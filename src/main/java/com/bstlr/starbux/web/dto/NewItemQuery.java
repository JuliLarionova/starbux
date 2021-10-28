package com.bstlr.starbux.web.dto;

import com.bstlr.starbux.entity.Currency;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = NewItemQuery.NewItemQueryBuilder.class)
public class NewItemQuery {
    @NotNull
    String name;
    @NotNull
    BigDecimal price;
    @NotNull
    Currency currency;

    public enum ItemType {
        DRINK, TOPPING;
    }
}
