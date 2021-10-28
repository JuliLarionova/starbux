package com.bstlr.starbux.entity.order;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

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

    public enum OrderStatus {
        NEW, PLACED;
    }
}
