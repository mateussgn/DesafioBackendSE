package com.bill.splitter.api.billing.link;

import java.math.BigDecimal;
import java.util.Map;

public interface BillingLink {

    public Map<String, String> getBillingLinks(Map<String, BigDecimal> clientsFinalBill);
}
