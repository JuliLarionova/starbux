package com.bstlr.starbux.web.dto;

import com.bstlr.starbux.web.dto.DrinkWithToppingsDto.Topping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.fromString;

public class TestDataFactory {

    public static final BigDecimal CART_AMOUNT = BigDecimal.TEN;
    public static final BigDecimal DRINK_AMOUNT = BigDecimal.valueOf(5);
    public static final String CUSTOMER_NAME = "Bob";
    public static final String CUSTOMER_EMAIL = "email";
    public static final UUID CUSTOMER_ID = fromString("ee3e4567-e91b-12d3-1111-5566f2222222");
    public static final UUID TEA_ID = fromString("ee3e4567-e91b-12d3-1111-5566f2223333");
    public static final UUID MILK_ID = fromString("ee3e4567-e91b-12d3-1111-5566f2222111");

    public static CartDto getCartDto() {
        return CartDto.builder()
                .totalCartCost(BigDecimal.TEN)
                .drinksWithToppings(List.of(getDrinkWithToppings()))
                .build();
    }

    public static DrinkWithToppingsDto getDrinkWithToppings() {
        return DrinkWithToppingsDto.builder()
                .id(TEA_ID)
                .toppings(Set.of(getTopping()))
                .build();
    }

    public static Topping getTopping() {
        return Topping.builder()
                .id(MILK_ID)
                .amount(1)
                .build();
    }





}
