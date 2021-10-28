package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@JsonDeserialize(builder = CustomerInfoDto.CustomerInfoDtoBuilder.class)
public class CartDto {

    @NonNull
    @JsonProperty("total_cart_cost")
    BigDecimal totalCartCost;
    @Builder.Default
    @JsonProperty("drinks_with_toppings")
    List<DrinkWithToppings> drinksWithToppings = new ArrayList<>();
}
