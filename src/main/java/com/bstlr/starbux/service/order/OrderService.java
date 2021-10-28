package com.bstlr.starbux.service.order;

import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.entity.order.OrderEntity;
import com.bstlr.starbux.entity.order.OrderItemDrinkEntity;
import com.bstlr.starbux.entity.order.OrderItemToppingEntity;
import com.bstlr.starbux.repository.OrderItemDrinkRepository;
import com.bstlr.starbux.repository.OrderRepository;
import com.bstlr.starbux.service.DrinkService;
import com.bstlr.starbux.service.ToppingService;
import com.bstlr.starbux.web.dto.DrinkWithToppings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.bstlr.starbux.entity.order.OrderEntity.OrderStatus.NEW;
import static com.bstlr.starbux.entity.order.OrderEntity.OrderStatus.PLACED;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static String NO_ACTIVE_ORDER = "No active order has been found for customer with email address %s = ";
    private static String ORDER_ALREADY_PLACED = "Order with id = %s has been already placed";

    private final OrderRepository repository;
    private final OrderItemDrinkRepository orderItemDrinkRepository;
    private final DrinkService drinkService;
    private final ToppingService toppingService;

    private Map<UUID, BigDecimal> drinkWithPrices;
    private Map<UUID, BigDecimal> toppingsWithPrices;

    public OrderEntity getCurrentOrderOrCreate(UUID customerId) {
        Optional<OrderEntity> currentOrder = repository.findByCustomerIdAndStatus(customerId, NEW);
        if (currentOrder.isEmpty()) {
            return repository.save(OrderEntity.builder()
                    .customerId(customerId)
                    .build());
        }
        return currentOrder.get();
    }

    public UUID getActiveOrderByCustomerEmail(String emailAddress) {
        return repository.getByCustomerEmailAddress(emailAddress, NEW)
                .orElseThrow(() -> new ClientException(String.format(NO_ACTIVE_ORDER, emailAddress)));
    }

    public OrderEntity getOrderById(UUID orderId) {
        return repository.getById(orderId);
    }

    public UUID addNewOrderItems(UUID customerId, Set<DrinkWithToppings> drinks) {
        setItemWithPrices(drinks);
        UUID orderId = getCurrentOrderOrCreate(customerId).getId();

        List<OrderItemDrinkEntity> drinkEntities = drinks.stream().map(d -> {
            List<OrderItemToppingEntity> toppings = convertToppings(d.getToppings());
            BigDecimal totalToppingsCost = toppings.stream()
                    .map(OrderItemToppingEntity::getTotalCost)
                    .reduce(BigDecimal::add)
                    .orElse(BigDecimal.ZERO);
            BigDecimal totalDrinkWithToppingsCost = drinkWithPrices.get(d.getId()).add(totalToppingsCost);
            OrderItemDrinkEntity itemDrink = OrderItemDrinkEntity.builder()
                    .itemId(d.getId())
                    .orderId(orderId)
                    .totalCost(totalDrinkWithToppingsCost)
                    .build();
            itemDrink.addToppings(toppings);
            return itemDrink;
        }).collect(Collectors.toList());
        orderItemDrinkRepository.saveAll(drinkEntities);
        return orderId;
    }

    private void setItemWithPrices(Set<DrinkWithToppings> drinks) {
        Set<UUID> drinkIds = drinks.stream().map(DrinkWithToppings::getId)
                .collect(Collectors.toSet());
        Set<UUID> toppingIds = drinks.stream()
                .map(DrinkWithToppings::getToppings)
                .flatMap(Collection::stream)
                .map(DrinkWithToppings.Topping::getId)
                .collect(Collectors.toSet());
        drinkWithPrices = drinkService.getDrinkPricesByIds(drinkIds);
        toppingsWithPrices = toppingService.getToppingPricesByIds(toppingIds);
    }

    private List<OrderItemToppingEntity> convertToppings(Set<DrinkWithToppings.Topping> toppings) {
        return toppings.stream()
                .map(t -> OrderItemToppingEntity.builder()
                        .amount(t.getAmount())
                        .itemId(t.getId())
                        .totalCost(calculate(toppingsWithPrices.get(t.getId()), t.getAmount()))
                        .build())
                .collect(Collectors.toList());
    }

    private BigDecimal calculate(BigDecimal price, Integer amount) {
        return price.multiply(BigDecimal.valueOf(amount));
    }

    public void placeOrder(UUID orderId) {
        OrderEntity order = getOrderById(orderId);
        if (order.getStatus() != NEW) {
            throw new ClientException(String.format(ORDER_ALREADY_PLACED, orderId));
        }
        order.setStatus(PLACED);
        repository.save(order);
    }

    public List<OrdersPerCustomer> getOrderAmountPerCustomer() {
        return repository.findOrdersPerCustomer();
    }
}
