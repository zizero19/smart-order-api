package com.smartorder.api.models;

import com.smartorder.api.models.enums.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEvent extends BaseEntity {

    @ManyToOne(optional = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    private OrderStatus previousStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus newStatus;

    private String changedBy;

    public OrderEvent(
            Order order,
            OrderStatus previousStatus,
            OrderStatus newStatus,
            String changedBy) {
        this.order = order;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.changedBy = changedBy;
    }
}