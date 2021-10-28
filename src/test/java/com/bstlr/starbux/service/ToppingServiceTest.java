package com.bstlr.starbux.service;

import com.bstlr.starbux.BaseIT;
import com.bstlr.starbux.entity.Currency;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bstlr.starbux.service.TestDataFactory.*;
import static org.hamcrest.MatcherAssert.assertThat;

class ToppingServiceTest extends BaseIT {

    @Autowired
    private ToppingService toppingService;

    @Test
    public void getsToppingPricesByIds() {
        //given
        UUID milkId = toppingService.createNewTopping(TOPPING_MILK, MILK_PRICE, Currency.euro);
        UUID sauceId = toppingService.createNewTopping(TOPPING_CHOCOLATE_SAUCE, CHOCOLATE_SAUCE_PRICE, Currency.euro);
        //when
        Map<UUID, BigDecimal> result = toppingService.getToppingPricesByIds(List.of(milkId, sauceId));
        //then
        Assertions.assertEquals(result.size(), 2);
        assertThat(result, IsMapContaining.hasEntry(milkId, MILK_PRICE));
        assertThat(result, IsMapContaining.hasEntry(sauceId, CHOCOLATE_SAUCE_PRICE));
    }

    @Test
    public void returnsEmptyMapWhenNoToppingsExist() {
        //when
        Map<UUID, BigDecimal> result = toppingService.getToppingPricesByIds(Collections.emptyList());
        //then
        Assertions.assertEquals(result.size(), 0);
    }
}