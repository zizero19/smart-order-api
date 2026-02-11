package com.smartorder.api.models;

import com.smartorder.api.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.CREATED;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

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

    private void ensureStatus(OrderStatus expected) {
        if (this.status != expected) {
            throw new IllegalStateException(
                    String.format("Expected order status to be %s but was %s", expected, this.status));
        }
    }
}
