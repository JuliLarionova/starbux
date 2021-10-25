package com.bstlr.starbux.web.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class OrderAmountPerCustomerDto {
    String customerName;
    Integer orderAmount;
}
