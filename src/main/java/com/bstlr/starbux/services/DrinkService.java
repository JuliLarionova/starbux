package com.bstlr.starbux.services;

import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;

    public Map<String, BigDecimal> getDrinkPricesByNames(List<String> drinkNames) {
        return drinkRepository.findAllByNameIn(drinkNames)
                .collect(Collectors.toMap(DrinkEntity::getName, DrinkEntity::getPrice, (c1, c2) -> c1));
    }

    public Map<UUID, BigDecimal> getDrinkPricesByIds(Collection<UUID> ids) {
        return drinkRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(DrinkEntity::getId, DrinkEntity::getPrice, (c1, c2) -> c1));
    }

    public void createNewDrink(String name, BigDecimal price, Currency currency) {
        drinkRepository.save(DrinkEntity.builder()
                .name(name)
                .price(price)
                .currency(currency)
                .build());
    }

    public void removeDrinkById(UUID id) {
        drinkRepository.deleteById(id);
    }

}
