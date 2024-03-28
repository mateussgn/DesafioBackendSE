package com.bill.splitter.api.calculator;

import com.bill.splitter.api.order.item.OrderItem;

import java.util.List;
import java.util.Map;

public interface Bill {
    public Map<String, String> calculate (List<OrderItem> orderItemsDTO);
}
