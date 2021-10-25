package com.bstlr.starbux.services;

import com.bstlr.starbux.web.dto.DrinkWithToppings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DrinkService drinkService;

    public BigDecimal getTotalOrderAmount(List<DrinkWithToppings> drinksWithToppings) {
/*        Map<String, BigDecimal> drinkMap = drinkService.getDrinkPricesByNames(order.getDrinks());

        BigDecimal totalDrinkCost = BigDecimal.ZERO;
        for (String drink : order.getDrinks()) {
            BigDecimal price = drinkMap.get(drink);
            if (isNull(price)) {
                throw new ClientException(String.format("Drink %s doesn't exist", drink));
            }
            totalDrinkCost = totalDrinkCost.add(price);
        }
        return totalDrinkCost;*/
        return BigDecimal.TEN;
    }

}
