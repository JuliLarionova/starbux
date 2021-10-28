package com.bstlr.starbux.service.order;

import com.bstlr.starbux.BaseIT;
import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.entity.CustomerEntity;
import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.entity.ToppingEntity;
import com.bstlr.starbux.entity.order.OrderEntity;
import com.bstlr.starbux.entity.order.OrderEntity.OrderStatus;
import com.bstlr.starbux.entity.order.OrderItemDrinkEntity;
import com.bstlr.starbux.entity.order.OrderItemToppingEntity;
import com.bstlr.starbux.repository.*;
import com.bstlr.starbux.web.dto.DrinkWithToppings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.bstlr.starbux.TestDataFactory.*;
import static com.bstlr.starbux.entity.order.OrderEntity.OrderStatus.NEW;
import static com.bstlr.starbux.entity.order.OrderEntity.OrderStatus.PLACED;
import static com.bstlr.starbux.service.TestDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderServiceTest extends BaseIT {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderItemDrinkRepository orderItemDrinkRepository;
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
    void returnsActiveOrderIdByCustomerEmail() {
        //given
        createOrderWithStatus(NEW);
        //when
        UUID orderId = orderService.getActiveOrderByCustomerEmail(CUSTOMER_EMAIL);
        //then
        assertEquals(ORDER_ID, orderId);
    }

    @Test
    void throwsClientExceptionWhenNoActiveOrderFound() {
        //given
        createOrderWithStatus(PLACED);
        //then
        assertThrows(ClientException.class, () -> orderService.getActiveOrderByCustomerEmail(CUSTOMER_EMAIL));
    }

    @Test
    void placesOrderSuccessfully() {
        //given
        OrderEntity activeOrder = createOrderWithStatus(NEW);
        //when
        orderService.placeOrder(activeOrder.getId());
        //then
        OrderStatus status = orderRepository.getById(activeOrder.getId()).getStatus();
        assertEquals(status, PLACED);
    }

    @Test
    void throwsClientExceptionWhenOrderHasBeenAlreadyPlaced() {
        //given
        OrderEntity activeOrder = createOrderWithStatus(PLACED);
        //then
        assertThrows(ClientException.class, () -> orderService.placeOrder(activeOrder.getId()));
    }

    @Test
    void getOrderAmountPerCustomer() {
        //given
        createOrderWithStatus(NEW);
        //when
        List<OrdersPerCustomer> ordersPerCustomer = orderService.getOrderAmountPerCustomer();
        //then
        var expectedInfo = getOrdersPerCustomer();
        assertThat(ordersPerCustomer).containsExactlyInAnyOrder(expectedInfo);
    }

    @Test
    void addsNewOrderItems() {
        //given
        createOrderWithStatus(NEW);
        Set<DrinkWithToppings> dataToSave = Set.of(getDrinkWithToppings());
        //when
        UUID orderId = orderService.addNewOrderItems(CUSTOMER_ID, dataToSave);
        //then
        List<OrderItemDrinkEntity> drinks = orderItemDrinkRepository.findAllByOrderId(orderId);
        assertThat(drinks).hasSize(1);
        OrderItemDrinkEntity drink = drinks.get(0);
        assertEquals(drink.getTotalCost(), BigDecimal.valueOf(7));
        assertEquals(drink.getItemId(), TEA_ID);

        List<OrderItemToppingEntity> toppings = drink.getToppings();
        assertThat(toppings).hasSize(1);
        OrderItemToppingEntity topping = toppings.get(0);
        assertEquals(topping.getTotalCost(), MILK_PRICE.multiply(BigDecimal.valueOf(2)));
        assertEquals(topping.getItemId(), MILK_ID);
    }

    private OrderEntity createOrderWithStatus(OrderStatus orderStatus) {
        CustomerEntity customer = getCustomer().build();
        customerRepository.saveAndFlush(customer);
        OrderEntity order = getOrder().status(orderStatus).build();
        orderRepository.saveAndFlush(order);
        return order;
    }
}