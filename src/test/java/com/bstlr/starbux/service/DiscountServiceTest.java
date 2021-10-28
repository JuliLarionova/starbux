package com.bstlr.starbux.service;

import com.bstlr.starbux.BaseIT;
import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.entity.ToppingEntity;
import com.bstlr.starbux.entity.order.OrderEntity;
import com.bstlr.starbux.entity.order.OrderItemDrinkEntity;
import com.bstlr.starbux.repository.*;
import com.bstlr.starbux.web.dto.OrderCostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.bstlr.starbux.TestDataFactory.getCustomer;
import static com.bstlr.starbux.service.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscountServiceTest extends BaseIT {

    @Autowired
    private DiscountService discountService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private ToppingRepository toppingRepository;
    @Autowired
    private OrderItemDrinkRepository orderItemDrinkRepository;

    @BeforeEach
    public void runBefore() {
        DrinkEntity tea = getDrinkEntity(TEA_ID, DRINK_TEA, TEA_PRICE);
        DrinkEntity latte = getDrinkEntity(LATTE_ID, DRINK_LATTE, LATTE_PRICE);
        drinkRepository.saveAllAndFlush(List.of(tea, latte));
        ToppingEntity milk = getToppingEntity(MILK_ID, TOPPING_MILK, MILK_PRICE);
        ToppingEntity sauce = getToppingEntity(CHOCOLATE_SAUCE_ID, TOPPING_CHOCOLATE_SAUCE, CHOCOLATE_SAUCE_PRICE);
        toppingRepository.saveAllAndFlush(List.of(milk, sauce));
    }

    @Test
    public void getsZeroDiscount_twoDrinks_totalCostIsTen() {
        //given
        OrderEntity order = createNewOrder();
        saveTwoItemsInOrderWithTenCost();
        //when
        OrderCostDto orderCost = discountService.setDiscountAndGetTotalCost(order.getId());
        //then
        assertEquals(orderCost.getDiscount(), BigDecimal.ZERO);
        assertEquals(orderCost.getTotalCost(), BigDecimal.valueOf(10));
    }

    @Test
    public void getsRateDiscount_whenAmountOfDrinkIsTwo_totalCostIsSixteen() {
        //given
        OrderEntity order = createNewOrder();
        saveTwoItemInOrderWithTwentyCost();
        //when
        OrderCostDto orderCost = discountService.setDiscountAndGetTotalCost(order.getId());
        //then
        assertEquals(orderCost.getDiscount(), BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP));
        assertEquals(orderCost.getTotalCost(), BigDecimal.valueOf(16));
    }

    @Test
    public void getsDrinkDiscount_threeTheSameDrinksWithoutToppings_totalCostIsNine() {
        //given
        OrderEntity order = createNewOrder();
        saveThreeTheSameItemsInOrder();
        //when
        OrderCostDto orderCost = discountService.setDiscountAndGetTotalCost(order.getId());
        //then
        assertEquals(orderCost.getDiscount(), BigDecimal.valueOf(3));
        assertEquals(orderCost.getTotalCost(), BigDecimal.valueOf(9));
    }

    @Test
    public void getsDrinkDiscount_threeDifferentDrinksWithoutToppings_totalCostIsEleven() {
        //given
        OrderEntity order = createNewOrder();
        saveThreeDifferentItemsInOrderWithoutToppings();
        //when
        OrderCostDto orderCost = discountService.setDiscountAndGetTotalCost(order.getId());
        //then
        assertEquals(orderCost.getDiscount(), BigDecimal.valueOf(3));
        assertEquals(orderCost.getTotalCost(), BigDecimal.valueOf(11));
    }

    @Test
    public void getsDrinkDiscount_whenTwoDiscountAvailable() {
        //given
        OrderEntity order = createNewOrder();
        saveThreeDifferentItemsInOrderWithToppings();
        //when
        OrderCostDto orderCost = discountService.setDiscountAndGetTotalCost(order.getId());
        //then
        assertEquals(orderCost.getDiscount(), BigDecimal.valueOf(5));
        assertEquals(orderCost.getTotalCost(), BigDecimal.valueOf(25));
    }

    private OrderEntity createNewOrder() {
        CustomerEntity customer = getCustomer().build();
        customerRepository.saveAndFlush(customer);
        OrderEntity order = getOrder().build();
        orderRepository.saveAndFlush(order);
        return order;
    }

    private List<OrderItemDrinkEntity> saveTwoItemsInOrderWithTenCost() {
        OrderItemDrinkEntity teaItem1 = getOrderItemDrinkBuilder(TEA_ID)
                .totalCost(BigDecimal.valueOf(5))
                .build();
        OrderItemDrinkEntity teaItem2 = getOrderItemDrinkBuilder(TEA_ID)
                .id(ORDER_DRINK2_ID)
                .totalCost(BigDecimal.valueOf(5))
                .build();
        teaItem1.setToppings(List.of(getOrderItemToppingBuilder(MILK_ID).build()));
        teaItem2.setToppings(List.of(getOrderItemToppingBuilder(MILK_ID).build()));
        orderItemDrinkRepository.saveAllAndFlush(List.of(teaItem1, teaItem2));
        return List.of(teaItem1, teaItem2);
    }

    private List<OrderItemDrinkEntity> saveTwoItemInOrderWithTwentyCost() {
        OrderItemDrinkEntity drinkItem1 = getOrderItemDrinkBuilder(TEA_ID)
                .totalCost(BigDecimal.valueOf(8))
                .build();
        drinkItem1.setToppings(List.of(getOrderItemToppingBuilder(MILK_ID).build(),
                getOrderItemToppingBuilder(CHOCOLATE_SAUCE_ID)
                        .amount(1)
                        .totalCost(BigDecimal.valueOf(5))
                        .build()));
        OrderItemDrinkEntity drinkItem2 = getOrderItemDrinkBuilder(TEA_ID)
                .id(ORDER_DRINK2_ID)
                .totalCost(BigDecimal.valueOf(8))
                .build();
        drinkItem2.setToppings(List.of(getOrderItemToppingBuilder(MILK_ID).build(),
                getOrderItemToppingBuilder(CHOCOLATE_SAUCE_ID)
                        .amount(1)
                        .totalCost(BigDecimal.valueOf(5))
                        .build()));
        orderItemDrinkRepository.saveAllAndFlush(List.of(drinkItem1, drinkItem2));
        return List.of(drinkItem1, drinkItem2);
    }

    private List<OrderItemDrinkEntity> saveThreeTheSameItemsInOrder() {
        OrderItemDrinkEntity drinkItem1 = getOrderItemDrinkBuilder(TEA_ID)
                .totalCost(BigDecimal.valueOf(3))
                .build();
        OrderItemDrinkEntity drinkItem2 = getOrderItemDrinkBuilder(TEA_ID)
                .id(ORDER_DRINK2_ID)
                .totalCost(BigDecimal.valueOf(3))
                .build();
        OrderItemDrinkEntity drinkItem3 = getOrderItemDrinkBuilder(TEA_ID)
                .id(ORDER_DRINK3_ID)
                .totalCost(BigDecimal.valueOf(3))
                .build();
        orderItemDrinkRepository.saveAllAndFlush(List.of(drinkItem1, drinkItem2, drinkItem3));
        return List.of(drinkItem1, drinkItem2, drinkItem3);
    }

    private List<OrderItemDrinkEntity> saveThreeDifferentItemsInOrderWithoutToppings() {
        DrinkEntity tea = drinkRepository.getById(TEA_ID);
        DrinkEntity latte = drinkRepository.getById(LATTE_ID);
        OrderItemDrinkEntity teaItem1 = getOrderItemDrinkBuilder(tea.getId())
                .totalCost(BigDecimal.valueOf(3))
                .build();
        OrderItemDrinkEntity teaItem2 = getOrderItemDrinkBuilder(tea.getId())
                .id(ORDER_DRINK2_ID)
                .totalCost(BigDecimal.valueOf(3))
                .build();
        OrderItemDrinkEntity latteItem = getOrderItemDrinkBuilder(latte.getId())
                .id(ORDER_DRINK3_ID)
                .totalCost(BigDecimal.valueOf(5))
                .build();
        orderItemDrinkRepository.saveAllAndFlush(List.of(teaItem1, teaItem2, latteItem));
        return List.of(teaItem1, teaItem2, latteItem);
    }

    private List<OrderItemDrinkEntity> saveThreeDifferentItemsInOrderWithToppings() {
        OrderItemDrinkEntity teaItem = getOrderItemDrinkBuilder(TEA_ID)
                .totalCost(BigDecimal.valueOf(5))
                .build();
        teaItem.setToppings(List.of(getOrderItemToppingBuilder(MILK_ID).build()));

        OrderItemDrinkEntity latteItem1 = getOrderItemDrinkBuilder(LATTE_ID)
                .id(ORDER_DRINK2_ID)
                .totalCost(BigDecimal.valueOf(10))
                .build();
        OrderItemDrinkEntity latteItem2 = getOrderItemDrinkBuilder(LATTE_ID)
                .id(ORDER_DRINK3_ID)
                .totalCost(BigDecimal.valueOf(10))
                .build();
        latteItem1.setToppings(List.of(
                getOrderItemToppingBuilder(CHOCOLATE_SAUCE_ID).totalCost(BigDecimal.valueOf(5)).build()));
        latteItem2.setToppings(List.of(
                getOrderItemToppingBuilder(CHOCOLATE_SAUCE_ID).totalCost(BigDecimal.valueOf(5)).build()));
        orderItemDrinkRepository.saveAllAndFlush(List.of(teaItem, latteItem1, latteItem2));
        return List.of(teaItem, latteItem1, latteItem2);
    }
}
