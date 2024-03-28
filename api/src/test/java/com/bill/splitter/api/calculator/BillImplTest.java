package com.bill.splitter.api.calculator;

import com.bill.splitter.api.order.item.OrderItem;
import com.bill.splitter.api.order.item.OrderItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bill.splitter.api.calculator.BillConstants.BILLING_LINK;

class BillImplTest {

    @BeforeEach
    void setUp() {
        orderItems = Arrays.asList(
                new OrderItem(OrderItemType.ADDITION_RATE, 0.10F, null),
                new OrderItem(OrderItemType.DELIVERY_FEE, 8.00F, null),
                new OrderItem(OrderItemType.DISCOUNT_COUPON, 5.00F, null),
                new OrderItem(OrderItemType.DISCOUNT_RATE, 0.10F, null),
                new OrderItem(OrderItemType.PRODUCT, 7.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 14.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 10.99F, "Beta"),
                new OrderItem(OrderItemType.PRODUCT, 20.99F, "Beta")
        );

        billingLinks = new HashMap<String, String>(){{
            put("Alpha", BILLING_LINK + "23.21");
            put("Beta", BILLING_LINK + "33.76");
        }};
    }

    @Test
    void calculate() {
        Bill bill = new BillImpl();
        bill.calculate(orderItems);

        Assertions.assertEquals(billingLinks, bill.calculate(orderItems));
    }

    private Map<String, String> billingLinks;
    private List<OrderItem> orderItems;
}