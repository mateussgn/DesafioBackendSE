package com.bill.splitter.api.service.calculator;

import com.bill.splitter.api.domain.order.item.dto.OrderItem;

import java.util.List;
import java.util.Map;

public interface Bill {
    public Map<String, String> calculate (List<OrderItem> orderItemsDTO);
}
