package com.smartorder.api.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartorder.api.dtos.orderItem.OrderItemRequestDTO;
import com.smartorder.api.dtos.orderItem.OrderItemResponseDTO;
import com.smartorder.api.mappers.OrderItemMapper;
import com.smartorder.api.models.Order;
import com.smartorder.api.models.Product;
import com.smartorder.api.repositories.OrderRepository;
import com.smartorder.api.repositories.ProductRepository;
import com.smartorder.api.services.OrderItemService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderItemServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderItemResponseDTO addItem(Long orderId, OrderItemRequestDTO request) {

        if (request == null) {
            throw new IllegalArgumentException("Request é obrigatório.");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order nao encontrada"));

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        order.addItem(product, request.quantity());

        orderRepository.save(order);

        return orderItemMapper.toResponse(order.getItems().get(order.getItems().size() - 1));
    }

    @Override
    public List<OrderItemResponseDTO> listByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order nao encontrada"));

        return order.getItems()
                .stream()
                .map(orderItemMapper::toResponse)
                .toList();
    }

    @Override
    public OrderItemResponseDTO changeQuantity(Long orderId, Long orderItemId, Integer quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order nao encontrada"));

        order.changeItemQuantity(orderItemId, quantity);

        orderRepository.save(order);

        return order.getItems()
                .stream()
                .filter(i -> orderItemId.equals(i.getId()))
                .findFirst()
                .map(orderItemMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado"));
    }

    @Override
    public void removeItem(Long orderId, Long orderItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order nao encontrada"));

        order.removeItem(orderItemId);

        orderRepository.save(order);
    }

}
