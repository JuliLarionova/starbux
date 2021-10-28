package com.bstlr.starbux.service.order;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor
@Value
public class OrdersPerCustomer {
    Long orderAmount;
    UUID customerId;
    String customerName;
}
