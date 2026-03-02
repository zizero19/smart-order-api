package com.smartorder.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.smartorder.api.dtos.order.OrderRequestDTO;
import com.smartorder.api.dtos.order.OrderResponseDTO;
import com.smartorder.api.models.Order;

@Mapper(componentModel = "spring", uses = OrderItemMapper.class)
public interface OrderMapper {
    
    @Mapping(target = "customerId", source = "client.id")
    OrderResponseDTO toResponse(Order order);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "items", ignore = true)
    Order toEntity(OrderRequestDTO request);
}
