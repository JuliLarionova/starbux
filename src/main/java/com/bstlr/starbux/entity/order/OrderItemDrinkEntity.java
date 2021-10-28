package com.bstlr.starbux.entity.order;

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

    @Column(name = "order_id")
    UUID orderId;

    @OneToMany(cascade = ALL, mappedBy = "drink", fetch = LAZY, orphanRemoval = true)
    @Builder.Default
    List<OrderItemToppingEntity> toppings = new ArrayList<>();

    /**
     * Total cost of drink with toppings
     */
    @NonNull
    @Column(name = "total_cost")
    BigDecimal totalCost;

    public void addToppings(List<OrderItemToppingEntity> toppingList) {
        toppings.addAll(toppingList);
        toppingList.forEach(topping -> topping.setDrink(this));
    }
}
