package com.smartorder.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartorder.api.dtos.product.ProductRequestDTO;
import com.smartorder.api.dtos.product.ProductResponseDTO;
import com.smartorder.api.mappers.ProductMapper;
import com.smartorder.api.models.Product;
import com.smartorder.api.repositories.ProductRepository;
import com.smartorder.api.services.ProductService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO request) {
        boolean exists = productRepository.existsByName(request.name());

        if (exists) {
            throw new RuntimeException("Produto com o mesmo nome ja existe");
        }

        Product product = productMapper.toEntity(request);
        productRepository.save(product);
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllActiveProducts() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponseDTO findByProductId(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        return productMapper.toResponse(product);
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        product.setActive(false);
        productRepository.save(product);
    }

}
