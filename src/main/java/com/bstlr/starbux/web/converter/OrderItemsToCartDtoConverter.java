package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.entity.order.OrderItemDrinkEntity;
import com.bstlr.starbux.entity.order.OrderItemToppingEntity;
import com.bstlr.starbux.web.dto.CartDto;
import com.bstlr.starbux.web.dto.DrinkWithToppings;
import com.bstlr.starbux.web.dto.DrinkWithToppings.Topping;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderItemsToCartDtoConverter {

    public CartDto convert(List<OrderItemDrinkEntity> orderItems) {
        List<DrinkWithToppings> drinksWithToppings = orderItems.stream()
                .map(orderItem -> DrinkWithToppings.builder()
                        .id(orderItem.getItemId())
                        .toppings(getToppings(orderItem.getToppings()))
                        .build())
                .collect(Collectors.toList());
        return CartDto.builder()
                .totalCartCost(getTotalCost(orderItems))
                .drinksWithToppings(drinksWithToppings)
                .build();
    }

    private Set<Topping> getToppings(List<OrderItemToppingEntity> toppings) {
        return toppings.stream()
                .map(t -> Topping.builder()
                        .amount(t.getAmount())
                        .id(t.getItemId())
                        .build())
                .collect(Collectors.toSet());
    }

    private BigDecimal getTotalCost(List<OrderItemDrinkEntity> orderItems) {
        return orderItems.stream()
                .map(OrderItemDrinkEntity::getTotalCost)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
