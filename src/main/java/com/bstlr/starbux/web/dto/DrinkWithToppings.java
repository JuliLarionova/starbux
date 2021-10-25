package com.bstlr.starbux.web.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class DrinkWithToppings {
    @NotNull
    UUID id;
    @Builder.Default
    @Valid
    Set<Topping> toppings = new HashSet<>();
    @NotNull
    @Positive
    Integer amount;

    @Getter
    @Builder(toBuilder = true)
    public static class Topping {
        @NotNull
        UUID id;
        @NotNull
        @Positive
        Integer amount;
    }
}
