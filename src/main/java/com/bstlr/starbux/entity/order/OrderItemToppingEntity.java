package com.bstlr.starbux.entity.order;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item_topping")
public class OrderItemToppingEntity {

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
    @JoinColumn(name = "order_item_drink_id", referencedColumnName = "id")
    OrderItemDrinkEntity drink;

    @NonNull
    @Column(name = "total_cost")
    BigDecimal totalCost;
}

