package com.bill.splitter.api.controller;

import com.bill.splitter.api.service.calculator.Bill;
import com.bill.splitter.api.service.calculator.BillImpl;
import com.bill.splitter.api.domain.order.item.dto.OrderItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("orderRequest")
public class OrderRequestController {

    @GetMapping
    public ResponseEntity<Map<String, String>> addOrderRequest (@RequestBody List<OrderItem> orderItemsDTO) {

        Bill bill = new BillImpl();

        return ResponseEntity.ok(bill.calculate(orderItemsDTO));
    }
}