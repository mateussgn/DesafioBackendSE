package com.bill.splitter.api.domain.order.item.dto;

import com.bill.splitter.api.domain.order.item.OrderItemType;

public record OrderItem (OrderItemType orderItemType, Float value, String client) {
}
