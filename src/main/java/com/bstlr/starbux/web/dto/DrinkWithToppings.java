package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = DrinkWithToppings.DrinkWithToppingsBuilder.class)
public class DrinkWithToppings {
    @NotNull
    UUID id;
    @Builder.Default
    @Valid
    Set<Topping> toppings = new HashSet<>();

    @Getter
    @Builder(toBuilder = true)
    @JsonDeserialize(builder = DrinkWithToppings.Topping.ToppingBuilder.class)
    public static class Topping {
        @NotNull
        UUID id;
        @NotNull
        @Positive
        Integer amount;
    }
}
