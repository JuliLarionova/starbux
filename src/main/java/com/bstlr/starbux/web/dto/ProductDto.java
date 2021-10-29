package com.bstlr.starbux.web.dto;

import com.bstlr.starbux.entity.Currency;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonDeserialize(builder = ProductDto.ProductDtoBuilder.class)
public class ProductDto {
    UUID id;
    String name;
    BigDecimal price;
    Currency currency;
}
