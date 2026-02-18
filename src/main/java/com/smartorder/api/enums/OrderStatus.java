package com.smartorder.api.enums;

public enum OrderStatus {
    CREATED, // pedido criado, ainda não confirmado
    CONFIRMED, // confirmado pelo cliente
    PAID, // pagamento aprovado
    PREPARING, // em preparo
    SHIPPED, // enviado
    DELIVERED, // entregue
    CANCELED // cancelado
}
