package com.bill.splitter.api.order.item;

public record OrderItem (OrderItemType orderItemType, Float value, String client) {
}
