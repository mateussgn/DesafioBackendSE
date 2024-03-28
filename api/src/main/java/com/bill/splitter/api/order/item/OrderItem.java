package com.bill.splitter.api.order.item;

public record OrderItem (OrderItemType orderItemType, Float value, String client) {

    public OrderItem (OrderItemType orderItemType, Float value, String client) {
        this.orderItemType = orderItemType;
        this.value = value;
        this.client = client;
    }
}
