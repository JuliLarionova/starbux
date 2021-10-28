package com.bstlr.starbux.web.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.bstlr.starbux.web.dto.TestDataFactory.getCartDto;

class CartDtoTest extends BaseDtoTest {
    private static final String OUTPUT_FILE_NAME = "cart-output.json";

    @Test
    public void testSerialization() throws IOException {
        //given
        var orderAmountPerCustomer = getCartDto();
        //when
        String result = new ObjectMapper().writeValueAsString(orderAmountPerCustomer);
        //then
        String expectedOutput = testReadFile(OUTPUT_FILE_NAME);;
        Assertions.assertEquals(result, expectedOutput);
    }
}