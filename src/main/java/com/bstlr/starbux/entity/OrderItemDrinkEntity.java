package com.bstlr.starbux.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item_drink")
public class OrderItemDrinkEntity {

    @Id
    @Builder.Default
    UUID id = UUID.randomUUID();

    @NonNull
    @Column(name = "item_id")
    UUID itemId;

    @NonNull
    @Builder.Default
    Integer amount = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    OrderEntity order;

    @OneToMany(cascade = ALL, mappedBy = "drink", fetch = LAZY, orphanRemoval = true)
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    @Builder.Default
    List<OrderItemToppingEntity> toppings = new ArrayList<>();

    public void addToppings(List<OrderItemToppingEntity> toppingList){
        toppings.addAll(toppingList);
        toppingList.forEach(topping->topping.setDrink(this));
    }
}
