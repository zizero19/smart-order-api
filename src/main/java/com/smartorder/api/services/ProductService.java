package com.smartorder.api.services;

import java.util.List;

import com.smartorder.api.dtos.product.ProductRequestDTO;
import com.smartorder.api.dtos.product.ProductResponseDTO;

public interface ProductService {

    ProductResponseDTO create(ProductRequestDTO request);

    List<ProductResponseDTO> getAllActiveProducts();

    ProductResponseDTO findByProductId(Long id);

    void delete(Long id);
}
