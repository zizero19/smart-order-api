package com.smartorder.api.models;

import com.smartorder.api.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    public Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public OrderStatus status = OrderStatus.CREATED;

    public BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderItem> items = new ArrayList<>();

    public Order(Client client) {
        this.client = client;
    }

    public void changeStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    public void confirm() {
        ensureStatus(OrderStatus.CREATED);
        changeStatus(OrderStatus.CONFIRMED);
    }

    public void pay() {
        ensureStatus(OrderStatus.CONFIRMED);
        changeStatus(OrderStatus.PAID);
    }

    public void cancel() {
        if (this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Delivered order cannot be canceled");
        }

        changeStatus(OrderStatus.CANCELED);

    }

    public void addItem(Product product, Integer quantity) {

        OrderItem item = new OrderItem(this, product, quantity, product.getPrice());

        this.items.add(item);

        recalculateTotalPrice();
    }

    public void removeItem(Long orderItemId) {
        if (orderItemId == null) {
            throw new IllegalArgumentException("Id do item é obrigatório.");
        }

        boolean removed = this.items.removeIf(i -> orderItemId.equals(i.getId()));
        if (!removed) {
            throw new RuntimeException("Item do pedido não encontrado");
        }

        recalculateTotalPrice();
    }

    public void changeItemQuantity(Long orderItemId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantidade de itens deve ser maior que zero.");
        }
        if (orderItemId == null) {
            throw new IllegalArgumentException("Id do item é obrigatório.");
        }

        OrderItem item = this.items.stream()
                .filter(i -> orderItemId.equals(i.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item do pedido não encontrado"));

        item.setQuantity(quantity);
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void ensureStatus(OrderStatus expected) {
        if (this.status != expected) {
            throw new IllegalStateException(
                    String.format("Expected order status to be %s but was %s", expected, this.status));
        }
    }
}
