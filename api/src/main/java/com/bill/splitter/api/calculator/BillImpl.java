package com.bill.splitter.api.calculator;

import com.bill.splitter.api.billing.link.BillingLink;
import com.bill.splitter.api.billing.link.PicPayBillingLink;
import com.bill.splitter.api.order.item.OrderItem;
import com.bill.splitter.api.order.item.OrderItemType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bill.splitter.api.calculator.BillConstants.*;

public class BillImpl implements Bill{

    @Override
    public Map<String, String> calculate (List<OrderItem> orderItems){
        Map<String, Object> bill = sectionOrderRequest(orderItems);

        Map<String, Float> individualPartialBill = (Map<String, Float>) bill.get(CLIENTS_BILL);

        Float clientsBill = clientsBill(individualPartialBill);

        Map<String, Float> individualPercentages = individualPercentages(individualPartialBill, clientsBill);

        Float fullBill = fullBill(bill, clientsBill);

        return clientsBillingLinks(clientsFinalBill(individualPercentages, fullBill));
    }

    private Float clientsBill (Map<String, Float> individualPartialBill) {
        Float clientsBill = 0F;

        for (String key : individualPartialBill.keySet() ) {
            clientsBill += individualPartialBill.get(key);
        }

        return clientsBill;
    }

    private Float fullBill (Map<String, Object> bill, Float clientsBill) {
        Float additionRateValue = ((1 + (Float) bill.get(ADDITION_RATE)) * clientsBill) - clientsBill;
        Float discountRateValue = (Float) bill.get(DISCOUNT_RATE) * clientsBill;

        return clientsBill + additionRateValue + (Float) bill.get(DELIVERY_FEE) -
                (Float) bill.get(DISCOUNT_COUPON) - discountRateValue;
    }

    private Map<String, BigDecimal> clientsFinalBill (Map<String, Float> individualPercentages, Float fullBill) {
        Map<String, BigDecimal> clientsFinalBill = new HashMap<>();
        BigDecimal bigDecimal;

        for (String key : individualPercentages.keySet()) {
            bigDecimal = BigDecimal.valueOf(individualPercentages.get(key) * fullBill)
                    .setScale(2, RoundingMode.CEILING);

            clientsFinalBill.put(key, bigDecimal);
        }

        return clientsFinalBill;
    }

    private Map<String, String> clientsBillingLinks(Map<String, BigDecimal> clientsFinalBill) {
        BillingLink billingLinks = new PicPayBillingLink();
        return billingLinks.getBillingLinks(clientsFinalBill);
    }

    private Map<String, Float> individualPercentages (Map<String, Float> individualPartialBill, Float fullBill) {
        Map<String, Float> individualPercentages = new HashMap<>();

        for (String key : individualPartialBill.keySet() ) {
            individualPercentages.put(key, individualPartialBill.get(key) / fullBill);
        }

        return individualPercentages;
    }

    private Map<String, Object> sectionOrderRequest (List<OrderItem> orderItems) {
        Map<String, Object> bill = getDefaultOrderRequest();

        Map<String, Float> clientsBill = new HashMap<String, Float>();

        for (OrderItem orderItem : orderItems) {

            OrderItemType orderItemType = orderItem.orderItemType();

            // TODO: Would be a good approach create an interface and then its implementations to avoid this if statements sequence.

            if (orderItemType.equals(OrderItemType.ADDITION_RATE)) {
                if (orderItem.value() != null) {
                    bill.put(ADDITION_RATE, orderItem.value());
                }
            }

            if (orderItemType.equals(OrderItemType.DELIVERY_FEE)) {
                if (orderItem.value() != null) {
                    bill.put(DELIVERY_FEE, orderItem.value());
                }
            }

            if (orderItemType.equals(OrderItemType.DISCOUNT_COUPON)) {
                if (orderItem.value() != null) {
                    bill.put(DISCOUNT_COUPON, orderItem.value());
                }
            }

            if (orderItemType.equals(OrderItemType.DISCOUNT_RATE)) {
                if (orderItem.value() != null) {
                    bill.put(DISCOUNT_RATE, orderItem.value());
                }
            }

            if (orderItemType.equals(OrderItemType.PRODUCT)) {

                if (clientsBill.containsKey(orderItem.client())) {
                    Float individualBill = clientsBill.get(orderItem.client()) + orderItem.value();
                    clientsBill.put(orderItem.client(), individualBill);
                    bill.put(CLIENTS_BILL, clientsBill);

                } else {
                    clientsBill.put(orderItem.client(), orderItem.value());
                    bill.put(CLIENTS_BILL, clientsBill);

                }
            }
        }

        return bill;
    }

    private static Map<String, Object> getDefaultOrderRequest() {
        Map<String, Object> bill = new HashMap<String, Object>();
        bill.put(ADDITION_RATE, 0F);
        bill.put(DELIVERY_FEE, 0F);
        bill.put(DISCOUNT_COUPON, 0F);
        bill.put(DISCOUNT_RATE, 0F);
        return bill;
    }
}
