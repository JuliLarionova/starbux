package com.bstlr.starbux.web.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.bstlr.starbux.TestDataFactory.CUSTOMER_ID;
import static com.bstlr.starbux.TestDataFactory.CUSTOMER_NAME;

class OrdersPerCustomerDtoTest extends BaseDtoTest {
    private static final String OUTPUT_FILE_NAME = "order-amount-per-customer-output.json";

    @Test
    public void testSerialization() throws IOException {
        //given
        var orderAmountPerCustomer = OrderAmountPerCustomerDto.builder()
                .customerName(CUSTOMER_NAME)
                .customerId(CUSTOMER_ID)
                .orderAmount(2)
                .build();
        //when
        String result = new ObjectMapper().writeValueAsString(orderAmountPerCustomer);
        //then
        String expectedOutput = testReadFile(OUTPUT_FILE_NAME);;
        Assertions.assertEquals(result, expectedOutput);
    }
}