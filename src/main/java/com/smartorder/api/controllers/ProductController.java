package com.smartorder.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartorder.api.dtos.product.ProductRequestDTO;
import com.smartorder.api.dtos.product.ProductResponseDTO;
import com.smartorder.api.services.impl.ProductServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping()
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO entity) {
        return ResponseEntity.ok(productService.create(entity));
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findByProductId(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
