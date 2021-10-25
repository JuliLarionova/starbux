package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.entity.OrderItemDrinkEntity;
import com.bstlr.starbux.entity.OrderItemToppingEntity;
import com.bstlr.starbux.services.DrinkService;
import com.bstlr.starbux.services.ToppingService;
import com.bstlr.starbux.web.dto.CartDto;
import com.bstlr.starbux.web.dto.DrinkWithToppings;
import com.bstlr.starbux.web.dto.DrinkWithToppings.Topping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemsToCartDtoConverter implements Converter<CartDto, OrderItemDrinkEntity> {
    private final DrinkService drinkService;
    private final ToppingService toppingService;

    public CartDto convert(List<OrderItemDrinkEntity> orderItems) {
        List<DrinkWithToppings> drinksWithToppings = orderItems.stream()
                .map(orderItem -> DrinkWithToppings.builder()
                        .amount(orderItem.getAmount())
                        .id(orderItem.getItemId())
                        .toppings(getToppings(orderItem.getToppings()))
                        .build())
                .collect(Collectors.toList());
        return CartDto.builder()
                .totalCartAmount(getTotalAmount(drinksWithToppings))
                .drinkWithToppings(drinksWithToppings)
                .build();
    }

    private BigDecimal getTotalAmount(List<DrinkWithToppings> drinksWithToppings) {
        Set<UUID> drinkIds = drinksWithToppings.stream().map(DrinkWithToppings::getId)
                .collect(Collectors.toSet());
        Set<UUID> toppingIds = drinksWithToppings.stream()
                .map(DrinkWithToppings::getToppings)
                .flatMap(Collection::stream)
                .map(Topping::getId)
                .collect(Collectors.toSet());
        Map<UUID, BigDecimal> drinkWithPrices = drinkService.getDrinkPricesByIds(drinkIds);
        Map<UUID, BigDecimal> toppingsWithPrices = toppingService.getToppingPricesByIds(toppingIds);

        BigDecimal totalCost = BigDecimal.ZERO;
        for (DrinkWithToppings d : drinksWithToppings) {
            totalCost = calculate(totalCost, drinkWithPrices.get(d.getId()), d.getAmount());
            totalCost = calculateToppingsCost(totalCost, d.getToppings(), toppingsWithPrices);
        }
        return totalCost;
    }

    private BigDecimal calculateToppingsCost(BigDecimal totalCost, Set<Topping> toppings,
                                             Map<UUID, BigDecimal> toppingsWithPrices) {
        for (Topping t : toppings) {
            totalCost = calculate(totalCost, toppingsWithPrices.get(t.getId()), t.getAmount());
        }
        return totalCost;
    }

    private BigDecimal calculate(BigDecimal totalCost, BigDecimal price, Integer amount) {
        BigDecimal itemCost = price.multiply(BigDecimal.valueOf(amount));
        totalCost = totalCost.add(itemCost);
        return totalCost;
    }

    private Set<Topping> getToppings(List<OrderItemToppingEntity> toppings) {
        return toppings.stream()
                .map(t -> Topping.builder()
                        .amount(t.getAmount())
                        .id(t.getItemId())
                        .build())
                .collect(Collectors.toSet());
    }
}
