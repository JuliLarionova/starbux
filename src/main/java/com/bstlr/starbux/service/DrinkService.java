package com.bstlr.starbux.service;

import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;

    public Map<UUID, BigDecimal> getDrinkPricesByIds(Collection<UUID> ids) {
        return drinkRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(DrinkEntity::getId, DrinkEntity::getPrice, (c1, c2) -> c1));
    }

    public UUID createNewDrink(String name, BigDecimal price, Currency currency) {
        return drinkRepository.save(DrinkEntity.builder()
                .name(name)
                .price(price)
                .currency(currency)
                .build()).getId();
    }

    public void removeDrinkById(UUID id) {
        drinkRepository.deleteById(id);
    }

}
