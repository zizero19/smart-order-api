package com.smartorder.api.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Column(name = "product_name", nullable = false, length = 150)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(length = 50)
    private String category;

    public Product(String name, BigDecimal price, Integer stock, String category) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço nao pode ser negativo.");
        }
        if (stock == null || stock < 0) {
            throw new IllegalArgumentException("O estoque do produto deve ser um valor não negativo.");
        }

        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public boolean hasStock(int quantity) {
        return this.stock >= quantity;
    }

    public void decreaseStock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("A quantidade a ser diminuida deve ser um valor maior que zero.");
        }
        if (!hasStock(quantity)) {
            throw new IllegalArgumentException("Estoque insuficiente para o produto: " + this.name);
        }
        this.stock -= quantity;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

}
