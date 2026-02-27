package com.smartorder.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.smartorder.api.dtos.orderItem.OrderItemResponseDTO;
import com.smartorder.api.models.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponseDTO toResponse(OrderItem orderItem);

}
