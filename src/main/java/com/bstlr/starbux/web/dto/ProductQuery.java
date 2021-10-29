package com.bstlr.starbux.web.dto;

import com.bstlr.starbux.entity.Currency;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ProductQuery.ProductQueryBuilder.class)
public class ProductQuery {
    @NotNull
    String name;
    @NotNull
    BigDecimal price;
    @NotNull
    Currency currency;

    public enum ProductType {
        DRINK, TOPPING;
    }
}
