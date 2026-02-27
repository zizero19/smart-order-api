package com.smartorder.api.services.impl;

import com.smartorder.api.dtos.order.OrderRequestDTO;
import com.smartorder.api.dtos.order.OrderResponseDTO;
import com.smartorder.api.dtos.orderItem.OrderItemResponseDTO;
import com.smartorder.api.models.Client;
import com.smartorder.api.models.Order;
import com.smartorder.api.models.Product;
import com.smartorder.api.repositories.ClientRepository;
import com.smartorder.api.repositories.OrderRepository;
import com.smartorder.api.repositories.ProductRepository;
import com.smartorder.api.services.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            ClientRepository clientRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderResponseDTO create(OrderRequestDTO request) {

        Client client = clientRepository.findById(request.clientId())
                .orElseThrow(() -> new RuntimeException("Cliente nao encontrado"));

        Order order = new Order(client);

        if (request.productIds() != null && !request.productIds().isEmpty()) {
            List<Product> products = productRepository.findAllById(request.productIds());

            Set<Long> foundIds = products.stream()
                    .map(Product::getId)
                    .collect(Collectors.toSet());

            List<Long> missing = request.productIds().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            if (!missing.isEmpty()) {
                throw new RuntimeException("Produtos não encontrados: " + missing);
            }

            for (Product product : products) {
                order.addItem(product, 1);
            }
        }

        orderRepository.save(order);

        return mapToResponse(order);

    }

    @Override
    @Transactional
    public OrderResponseDTO findByOrderId(Long id) {
        Order order;

        order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order nao encontrada"));

        return mapToResponse(order);
    }

    @Override
    @Transactional
    public List<OrderResponseDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order nao encontrada");
        }

        orderRepository.deleteById(id);
    }

    private OrderResponseDTO mapToResponse(Order order) {
        List<OrderItemResponseDTO> productIds = order.getItems()
                .stream()
                .map(item -> new OrderItemResponseDTO(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()))
                .toList();

        return new OrderResponseDTO(
                order.getId(),
                order.getClient().getId(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                productIds);
    }
}
