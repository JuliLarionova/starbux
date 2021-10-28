package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = OrderCostDto.OrderCostDtoBuilder.class)
public class OrderCostDto {

    BigDecimal discount;
    @JsonProperty("total_cost")
    BigDecimal totalCost;
}
