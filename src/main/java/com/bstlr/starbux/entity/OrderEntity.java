package com.bstlr.starbux.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_")
public class OrderEntity {

    @Id
    @Builder.Default
    UUID id = UUID.randomUUID();

    @NonNull
    @Column(name = "customer_id")
    UUID customerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Builder.Default
    OrderStatus status = OrderStatus.NEW;

    @OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "order", orphanRemoval = true)
    @JoinColumn(name = "order_id")
    @Builder.Default
    Set<OrderItemDrinkEntity> drinks = new HashSet<>();

    public enum OrderStatus {
        NEW, PLACED;
    }

    public void addDrinks(List<OrderItemDrinkEntity> drinkList){
        drinks.addAll(drinkList);
        drinkList.forEach(drink->drink.setOrder(this));
    }
}
