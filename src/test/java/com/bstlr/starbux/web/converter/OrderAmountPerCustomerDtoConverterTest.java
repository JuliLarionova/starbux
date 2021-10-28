package com.bstlr.starbux.web.converter;

import com.bstlr.starbux.web.dto.OrderAmountPerCustomerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.bstlr.starbux.TestDataFactory.CUSTOMER_ID;
import static com.bstlr.starbux.TestDataFactory.CUSTOMER_NAME;
import static com.bstlr.starbux.service.TestDataFactory.getOrdersPerCustomer;

class OrderAmountPerCustomerDtoConverterTest {

    OrderAmountPerCustomerDtoConverter INSTANCE = Mappers.getMapper(OrderAmountPerCustomerDtoConverter.class);

    @Test
    void convert() {
        //given
        var finishedProductWithAmountDto = getOrdersPerCustomer();
        var expectedDto = OrderAmountPerCustomerDto.builder()
                .customerId(CUSTOMER_ID)
                .customerName(CUSTOMER_NAME)
                .orderAmount(1)
                .build();
        //when
        OrderAmountPerCustomerDto dto = INSTANCE.convert(finishedProductWithAmountDto);
        //then
        Assertions.assertThat(dto).isEqualTo(expectedDto);
    }
}