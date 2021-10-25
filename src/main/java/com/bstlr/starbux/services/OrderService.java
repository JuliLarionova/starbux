package com.bstlr.starbux.services;

import com.bstlr.starbux.entity.OrderEntity;
import com.bstlr.starbux.entity.OrderItemDrinkEntity;
import com.bstlr.starbux.entity.OrderItemToppingEntity;
import com.bstlr.starbux.repository.OrderItemDrinkRepository;
import com.bstlr.starbux.repository.OrderItemToppingRepository;
import com.bstlr.starbux.repository.OrderRepository;
import com.bstlr.starbux.web.dto.DrinkWithToppings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bstlr.starbux.entity.OrderEntity.OrderStatus.NEW;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderItemDrinkRepository orderItemDrinkRepository;
    private final OrderItemToppingRepository orderItemToppingRepository;

    public OrderEntity getCurrentOrderOrCreate(UUID customerId) {
        Optional<OrderEntity> currentOrder = repository.findAllByCustomerIdAndStatus(customerId, NEW);
        if (currentOrder.isEmpty()) {
            return repository.save(OrderEntity.builder()
                    .customerId(customerId)
                    .build());
        }
        return currentOrder.get();
    }

    private OrderItemDrinkEntity addNewDrinkOrUpdateAmount(OrderEntity order, UUID drinkId) {
        Optional<OrderItemDrinkEntity> itemOptional = orderItemDrinkRepository
                .findByOrderIdAndItemId(order.getId(), drinkId);
        if (itemOptional.isPresent()) {
            OrderItemDrinkEntity item = itemOptional.get();
            item.setAmount(item.getAmount() + 1);
            orderItemDrinkRepository.save(item);
            return item;
        }

        return orderItemDrinkRepository.save(OrderItemDrinkEntity.builder()
                .order(order)
                .itemId(drinkId)
                .build());
    }

    public List<OrderItemDrinkEntity> findOrderInfoById(UUID orderId) {
        return orderItemDrinkRepository.findAllByOrderId(orderId);
    }

    public UUID addNewOrderItems(UUID customerId, Set<DrinkWithToppings> drinks) {
        OrderEntity order = getCurrentOrderOrCreate(customerId);


/*       не работает вложенное сохранение
 List<OrderItemDrinkEntity> orderItems = drinks.stream()
                .map(c -> {
                   return OrderItemDrinkEntity.builder()
                            .amount(c.getAmount())
                            .itemId(c.getId())
                            .toppings(convertToppings(c.getToppings()))
                            .order(order)
                            .build();
                })
                .collect(Collectors.toList());

       orderItemDrinkRepository.saveAll(orderItems);*/
     /*   order.getDrinks().addAll(orderItems);
        repository.save(order);*/

        List<OrderItemDrinkEntity> drinkEntities = drinks.stream().map(c -> {
            OrderItemDrinkEntity drink = OrderItemDrinkEntity.builder()
                    .amount(c.getAmount())
                    .itemId(c.getId())
                    .build();
            List<OrderItemToppingEntity> toppings = convertToppings(c.getToppings(), drink);
            drink.addToppings(toppings);
            return drink;
        }).collect(Collectors.toList());
      //  orderItemDrinkRepository.saveAll(drinkEntities);
       order.addDrinks(drinkEntities);
        repository.save(order);
        return order.getId();
    }

    private List<OrderItemToppingEntity> convertToppings(Set<DrinkWithToppings.Topping> toppings, OrderItemDrinkEntity drink) {
        return toppings.stream()
                .map(c -> OrderItemToppingEntity.builder()
                        .amount(c.getAmount())
                        .itemId(c.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private List<OrderItemToppingEntity> convertToppings(Set<DrinkWithToppings.Topping> toppings) {
        return toppings.stream()
                .map(c -> OrderItemToppingEntity.builder()
                        .amount(c.getAmount())
                        .itemId(c.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
