package com.bstlr.starbux.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "topping")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ToppingEntity {
    @Id
    @Builder.Default
    UUID id = UUID.randomUUID();
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
