package com.bill.splitter.api.service.billing.link;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.bill.splitter.api.service.BillConstants.BILLING_LINK;

public class PicPayBillingLink implements BillingLink{

    @Override
    public Map<String, String> getBillingLinks(Map<String, BigDecimal> clientsFinalBill) {
        Map<String, String> billingLinks = new HashMap<>();

        for (String key : clientsFinalBill.keySet()) {
            billingLinks.put(key, BILLING_LINK + clientsFinalBill.get(key));
        }

        return billingLinks;
    }
}
