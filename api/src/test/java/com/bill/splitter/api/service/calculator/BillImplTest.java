package com.bill.splitter.api.service.calculator;

import com.bill.splitter.api.domain.order.item.dto.OrderItem;
import com.bill.splitter.api.domain.order.item.OrderItemType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.bill.splitter.api.service.calculator.BillConstants.BILLING_LINK;

class BillImplTest {

    @Test
    public void calculateAllDataSet() {
        orderItems = createOrderRequest(0.10F, 8.00F, 5.00F, 0.10F);
        orderItems.addAll(Arrays.asList(
                new OrderItem(OrderItemType.PRODUCT, 7.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 14.75F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 10.99F, "Beta"),
                new OrderItem(OrderItemType.PRODUCT, 20.37F, "Beta")
        ));

        billingLinks = new HashMap<>(){{
            put("Alpha", BILLING_LINK + "23.49");
            put("Beta", BILLING_LINK + "33.12");
        }};

        Assertions.assertEquals(billingLinks, bill.calculate(orderItems));
    }

    @Test
    public void calculateNoFees() {
        orderItems = createOrderRequest(null, null, null, null);
        orderItems.addAll(Arrays.asList(
                new OrderItem(OrderItemType.PRODUCT, 7.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 14.75F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 10.99F, "Beta"),
                new OrderItem(OrderItemType.PRODUCT, 20.37F, "Beta")
        ));

        billingLinks = new HashMap<>(){{
            put("Alpha", BILLING_LINK + "22.24");
            put("Beta", BILLING_LINK + "31.37");
        }};

        Assertions.assertEquals(billingLinks, bill.calculate(orderItems));
    }

    @Test
    public void calculateEmptyAdditionRate() {
        orderItems = createOrderRequest(null, 8.00F, 5.00F, 0.10F);
        orderItems.addAll(Arrays.asList(
                new OrderItem(OrderItemType.PRODUCT, 7.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 14.75F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 10.99F, "Beta"),
                new OrderItem(OrderItemType.PRODUCT, 20.37F, "Beta")
        ));

        billingLinks = new HashMap<>(){{
            put("Alpha", BILLING_LINK + "21.27");
            put("Beta", BILLING_LINK + "29.98");
        }};

        Assertions.assertEquals(billingLinks, bill.calculate(orderItems));
    }

    @Test
    public void calculateEmptyDeliveryFee() {
        orderItems = createOrderRequest(0.10F, null, 5.00F, 0.10F);
        orderItems.addAll(Arrays.asList(
                new OrderItem(OrderItemType.PRODUCT, 7.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 14.75F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 10.99F, "Beta"),
                new OrderItem(OrderItemType.PRODUCT, 20.37F, "Beta")
        ));

        billingLinks = new HashMap<>(){{
            put("Alpha", BILLING_LINK + "20.17");
            put("Beta", BILLING_LINK + "28.44");
        }};

        Assertions.assertEquals(billingLinks, bill.calculate(orderItems));
    }

    @Test
    public void calculateEmptyDiscountCoupon() {
        orderItems = createOrderRequest(0.10F, 8.00F, null, 0.10F);
        orderItems.addAll(Arrays.asList(
                new OrderItem(OrderItemType.PRODUCT, 7.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 14.75F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 10.99F, "Beta"),
                new OrderItem(OrderItemType.PRODUCT, 20.37F, "Beta")
        ));

        billingLinks = new HashMap<>(){{
            put("Alpha", BILLING_LINK + "25.56");
            put("Beta", BILLING_LINK + "36.05");
        }};

        Assertions.assertEquals(billingLinks, bill.calculate(orderItems));
    }

    @Test
    public void calculateEmptyDiscountRate() {
        orderItems = createOrderRequest(0.10F, 8.00F, 5.00F, null);
        orderItems.addAll(Arrays.asList(
                new OrderItem(OrderItemType.PRODUCT, 7.49F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 14.75F, "Alpha"),
                new OrderItem(OrderItemType.PRODUCT, 10.99F, "Beta"),
                new OrderItem(OrderItemType.PRODUCT, 20.37F, "Beta")
        ));

        billingLinks = new HashMap<>(){{
            put("Alpha", BILLING_LINK + "25.71");
            put("Beta", BILLING_LINK + "36.26");
        }};

        Assertions.assertEquals(billingLinks, bill.calculate(orderItems));
    }

    private List<OrderItem> createOrderRequest (
            Float additionRate, Float deliveryFee, Float discountCoupon, Float discountRate) {

        return new ArrayList<>(Arrays.asList(
                new OrderItem(OrderItemType.ADDITION_RATE, additionRate, null),
                new OrderItem(OrderItemType.DELIVERY_FEE, deliveryFee, null),
                new OrderItem(OrderItemType.DISCOUNT_COUPON, discountCoupon, null),
                new OrderItem(OrderItemType.DISCOUNT_RATE, discountRate, null)
        ));
    }

    private final Bill bill = new BillImpl();
    private Map<String, String> billingLinks;
    private List<OrderItem> orderItems;
}