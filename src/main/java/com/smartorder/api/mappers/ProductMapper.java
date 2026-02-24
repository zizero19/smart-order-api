package com.smartorder.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.smartorder.api.dtos.product.ProductRequestDTO;
import com.smartorder.api.dtos.product.ProductResponseDTO;
import com.smartorder.api.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toResponse(Product product);

    @Mapping(target = "active", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRequestDTO request);
}
