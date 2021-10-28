package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.web.dto.MostUsedToppingDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.bstlr.starbux.service.TestDataFactory.*;

class MostUsedToppingDtoConverterTest {

    MostUsedToppingDtoConverter INSTANCE = Mappers.getMapper(MostUsedToppingDtoConverter.class);

    @Test
    void convert() {
        //given
        var mostUsedTopping = getMostUsedTopping();
        var expectedDto = MostUsedToppingDto.builder()
                .id(MILK_ID)
                .name(TOPPING_MILK)
                .amount(5L)
                .build();
        //when
        MostUsedToppingDto dto = INSTANCE.convert(mostUsedTopping);
        //then
        Assertions.assertThat(dto).isEqualTo(expectedDto);
    }
}