package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.web.dto.ProductDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static com.bstlr.starbux.service.TestDataFactory.*;

class ProductDtoConverterTest {

    ProductDtoConverter INSTANCE = Mappers.getMapper(ProductDtoConverter.class);

    @Test
    void convert() {
        //given
        var drinkEntity = getDrinkEntity(TEA_ID, DRINK_TEA, BigDecimal.ONE);
        var expectedDto = ProductDto.builder()
                .id(TEA_ID)
                .name(DRINK_TEA)
                .price(BigDecimal.ONE)
                .currency(Currency.euro)
                .build();
        //when
        ProductDto dto = INSTANCE.convert(drinkEntity);
        //then
        Assertions.assertThat(dto).isEqualTo(expectedDto);
    }
}