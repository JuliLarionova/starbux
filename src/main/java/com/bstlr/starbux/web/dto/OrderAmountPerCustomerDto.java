package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = OrderAmountPerCustomerDto.OrderAmountPerCustomerDtoBuilder.class)
public class OrderAmountPerCustomerDto {
    @JsonProperty("customer_name")
    String customerName;
    @JsonProperty("customer_id")
    UUID customerId;
    @JsonProperty("order_amount")
    Integer orderAmount;
}
