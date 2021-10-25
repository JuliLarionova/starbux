package com.bstlr.starbux.web.dto;

import com.bstlr.starbux.entity.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class NewItemQuery {
    @NotNull
    String name;
    @NotNull
    BigDecimal price;
    @NotNull
    Currency currency;
}
