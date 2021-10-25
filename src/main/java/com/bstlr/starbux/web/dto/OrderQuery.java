package com.bstlr.starbux.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class OrderQuery {

    @NotNull
    CustomerInfo customerInfo;
    @Builder.Default
    List<DrinkWithToppings> drinkWithToppings = new ArrayList<>();
}
