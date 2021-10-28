package com.bstlr.starbux.service;

import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.entity.ToppingEntity;
import com.bstlr.starbux.entity.order.OrderEntity;
import com.bstlr.starbux.entity.order.OrderEntity.OrderEntityBuilder;
import com.bstlr.starbux.entity.order.OrderItemDrinkEntity;
import com.bstlr.starbux.entity.order.OrderItemDrinkEntity.OrderItemDrinkEntityBuilder;
import com.bstlr.starbux.entity.order.OrderItemToppingEntity;
import com.bstlr.starbux.entity.order.OrderItemToppingEntity.OrderItemToppingEntityBuilder;
import com.bstlr.starbux.service.order.OrdersPerCustomer;
import com.bstlr.starbux.web.dto.DrinkWithToppingsDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static com.bstlr.starbux.TestDataFactory.CUSTOMER_ID;
import static com.bstlr.starbux.TestDataFactory.CUSTOMER_NAME;
import static java.util.UUID.fromString;

public class TestDataFactory {
    public static final UUID TEA_ID = fromString("a7e87d74-35b9-11ec-bd25-c33959cdbf22");
    public static final String DRINK_TEA = "Tea";
    public static final BigDecimal TEA_PRICE = BigDecimal.valueOf(3);
    public static final UUID LATTE_ID = fromString("a7e85682-35b9-11ec-bd23-df2635677886");
    public static final String DRINK_LATTE = "Latte";
    public static final BigDecimal LATTE_PRICE = BigDecimal.valueOf(5);

    public static final UUID MILK_ID = fromString("a7eac5b6-35b9-11ec-bd26-0fcfb3a57818");
    public static final String TOPPING_MILK = "Milk";
    public static final BigDecimal MILK_PRICE = BigDecimal.valueOf(2);
    public static final UUID CHOCOLATE_SAUCE_ID = fromString("a7eb139b-35b9-11ec-bd28-57d68ed4f398");
    public static final String TOPPING_CHOCOLATE_SAUCE = "Chocolate sauce";
    public static final BigDecimal CHOCOLATE_SAUCE_PRICE = BigDecimal.valueOf(5);
    public static final UUID ORDER_ID = UUID.fromString("ee3e4567-e91b-12d3-1111-5566f2222222");
    public static final Integer ORDER_DRINK_AMOUNT = 2;
    public static final Integer ORDER_TOPPING_AMOUNT = 1;
    public static final UUID ORDER_DRINK1_ID = UUID.fromString("465b4794-f4e5-45da-891f-a6cbb0cb4000");
    public static final UUID ORDER_DRINK2_ID = UUID.fromString("465b4794-f4e5-45da-891f-a6cbb0cb4ae5");
    public static final UUID ORDER_DRINK3_ID = UUID.fromString("ee3e4567-e89b-12d3-1111-5566f2442288");

    public static OrderEntityBuilder getOrder() {
        return OrderEntity.builder()
                .id(ORDER_ID)
                .customerId(CUSTOMER_ID)
                .status(OrderEntity.OrderStatus.ACTIVE);
    }

    public static OrderItemDrinkEntityBuilder getOrderItemDrinkBuilder(UUID drinkId) {
        return OrderItemDrinkEntity.builder()
                .id(ORDER_DRINK1_ID)
                .orderId(ORDER_ID)
                .itemId(drinkId)
                .totalCost(BigDecimal.valueOf(3));
    }

    public static OrderItemToppingEntityBuilder getOrderItemToppingBuilder(UUID toppingId) {
        return OrderItemToppingEntity.builder()
                .amount(ORDER_TOPPING_AMOUNT)
                .itemId(toppingId)
                .totalCost(BigDecimal.valueOf(2));
    }

    public static OrdersPerCustomer getOrdersPerCustomer() {
        return new OrdersPerCustomer(1L, CUSTOMER_ID, CUSTOMER_NAME);
    }

    public static DrinkWithToppingsDto getDrinkWithToppings() {
        return DrinkWithToppingsDto.builder()
                .id(TEA_ID)
                .toppings(Set.of(getTopping()))
                .build();
    }

    public static DrinkWithToppingsDto.Topping getTopping() {
        return DrinkWithToppingsDto.Topping.builder()
                .id(MILK_ID)
                .amount(2)
                .build();
    }

    public static DrinkEntity getDrinkEntity(UUID id, String name, BigDecimal price) {
        return DrinkEntity.builder()
                .id(id)
                .name(name)
                .currency(Currency.euro)
                .price(price)
                .build();
    }

    public static ToppingEntity getToppingEntity(UUID id, String name, BigDecimal price) {
        return ToppingEntity.builder()
                .id(id)
                .name(name)
                .currency(Currency.euro)
                .price(price)
                .build();
    }


    public static MostUsedTopping getMostUsedTopping() {
        return new MostUsedTopping(5L, MILK_ID, TOPPING_MILK);
    }
}
