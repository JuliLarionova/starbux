package com.bstlr.starbux.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class CartDto {

    @NonNull
    BigDecimal totalCartAmount;
    @Builder.Default
    List<DrinkWithToppings> drinkWithToppings = new ArrayList<>();
}
