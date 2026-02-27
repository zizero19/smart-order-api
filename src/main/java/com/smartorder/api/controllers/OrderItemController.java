package com.smartorder.api.controllers;

import com.smartorder.api.dtos.orderItem.OrderItemRequestDTO;
import com.smartorder.api.dtos.orderItem.OrderItemResponseDTO;
import com.smartorder.api.services.impl.OrderItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/id/{orderId}/items")
public class OrderItemController {

    @Autowired
    private OrderItemServiceImpl orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemResponseDTO> addItem(
            @PathVariable Long orderId,
            @RequestBody OrderItemRequestDTO request) {
        return ResponseEntity.ok(orderItemService.addItem(orderId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderItemResponseDTO>> listItems(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.listByOrderId(orderId));
    }

    @PatchMapping("/{orderItemId}/quantity/{quantity}")
    public ResponseEntity<OrderItemResponseDTO> changeQuantity(
            @PathVariable Long orderId,
            @PathVariable Long orderItemId,
            @PathVariable Integer quantity) {
        return ResponseEntity.ok(orderItemService.changeQuantity(orderId, orderItemId, quantity));
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> removeItem(
            @PathVariable Long orderId,
            @PathVariable Long orderItemId) {
        orderItemService.removeItem(orderId, orderItemId);
        return ResponseEntity.noContent().build();
    }
}
