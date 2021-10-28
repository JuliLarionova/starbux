package com.bstlr.starbux.service.order;

import com.bstlr.starbux.BaseIT;
import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.entity.ToppingEntity;
import com.bstlr.starbux.entity.order.OrderEntity;
import com.bstlr.starbux.repository.*;
import com.bstlr.starbux.service.MostUsedTopping;
import com.bstlr.starbux.web.dto.DrinkWithToppings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static com.bstlr.starbux.TestDataFactory.CUSTOMER_ID;
import static com.bstlr.starbux.TestDataFactory.getCustomer;
import static com.bstlr.starbux.entity.order.OrderEntity.OrderStatus.NEW;
import static com.bstlr.starbux.service.TestDataFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderItemToppingServiceTest extends BaseIT {

    @Autowired
    private OrderItemToppingService orderItemToppingService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderItemDrinkRepository orderItemDrinkRepository;
    @Autowired
    private OrderItemToppingRepository orderItemToppingRepository;
    @Autowired
    private DrinkRepository drinkRepository;
    @Autowired
    private ToppingRepository toppingRepository;

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
    public void getsMostUsedTopping() {
        //given
        saveOrderItemToppings();
        //when
        MostUsedTopping mostUsedTopping = orderItemToppingService.getMostUsedTopping();
        //then
        var expectedTopping = new MostUsedTopping(3L, CHOCOLATE_SAUCE_ID, TOPPING_CHOCOLATE_SAUCE);
        assertEquals(mostUsedTopping, expectedTopping);
    }

    @Test
    public void returns() {
        //when
        MostUsedTopping mostUsedTopping = orderItemToppingService.getMostUsedTopping();
        //then
        assertEquals(mostUsedTopping, null);
    }

    private void saveOrderItemToppings() {
        createOrderWithStatus(NEW);
        DrinkWithToppings teaWith2Milk = getDrinkWithToppings();

        DrinkWithToppings.Topping topping = getTopping().toBuilder()
                .id(CHOCOLATE_SAUCE_ID)
                .amount(3)
                .build();
        DrinkWithToppings latteWith3ChSauce = getDrinkWithToppings().toBuilder()
                .id(LATTE_ID)
                .toppings(Set.of(topping))
                .build();
        orderService.addNewOrderItems(CUSTOMER_ID, Set.of(latteWith3ChSauce, teaWith2Milk));
    }

    private OrderEntity createOrderWithStatus(OrderEntity.OrderStatus orderStatus) {
        CustomerEntity customer = getCustomer().build();
        customerRepository.saveAndFlush(customer);
        OrderEntity order = getOrder().status(orderStatus).build();
        orderRepository.saveAndFlush(order);
        return order;
    }
}