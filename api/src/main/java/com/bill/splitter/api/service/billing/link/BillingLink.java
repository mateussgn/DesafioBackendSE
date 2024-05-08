package com.bill.splitter.api.service.billing.link;

import java.math.BigDecimal;
import java.util.Map;

public interface BillingLink {

    Map<String, String> getBillingLinks(Map<String, BigDecimal> clientsFinalBill);
}
