package com.smartorder.api.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.smartorder.api.models.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @ManyToOne
    private Client client;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEvent> events = new ArrayList<>();

    public Order(Client client) {
        this.client = client;
    }

    public void changeStatus(OrderStatus newStatus, String changedBy) {
        OrderEvent event = new OrderEvent(
                this,
                this.status,
                newStatus,
                changedBy);

        this.status = newStatus;
        this.events.add(event);
    }

}
