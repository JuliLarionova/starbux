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

class DrinkServiceTest extends BaseIT {

    @Autowired
    private DrinkService drinkService;

    @Test
    public void getsDrinkPricesByIds() {
        //given
        UUID teaId = drinkService.createNewDrink(DRINK_TEA, TEA_PRICE, Currency.euro);
        UUID latteId = drinkService.createNewDrink(DRINK_LATTE, LATTE_PRICE, Currency.euro);
        //when
        Map<UUID, BigDecimal> result = drinkService.getDrinkPricesByIds(List.of(teaId, latteId));
        //then
        Assertions.assertEquals(result.size(), 2);
        assertThat(result, IsMapContaining.hasEntry(teaId, TEA_PRICE));
        assertThat(result, IsMapContaining.hasEntry(latteId, LATTE_PRICE));
    }

    @Test
    public void returnsEmptyMapWhenNoDrinksExist() {
        //when
        Map<UUID, BigDecimal> result = drinkService.getDrinkPricesByIds(Collections.emptyList());
        //then
        Assertions.assertEquals(result.size(), 0);
    }
}