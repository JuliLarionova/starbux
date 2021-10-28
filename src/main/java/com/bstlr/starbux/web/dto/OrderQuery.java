package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = OrderQuery.OrderQueryBuilder.class)
public class OrderQuery {

    @NotNull
    CustomerInfoDto customerInfo;
    @Builder.Default
    List<DrinkWithToppingsDto> drinkWithToppings = new ArrayList<>();
}
