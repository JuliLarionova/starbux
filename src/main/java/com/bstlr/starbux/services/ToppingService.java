package com.bstlr.starbux.services;

import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.ToppingEntity;
import com.bstlr.starbux.repository.ToppingRepository;
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
public class ToppingService {
    private final ToppingRepository toppingRepository;

    public Map<String, BigDecimal> getDrinkPricesByNames(List<String> drinkNames) {
        return toppingRepository.findAllByNameIn(drinkNames)
                .collect(Collectors.toMap(ToppingEntity::getName, ToppingEntity::getPrice, (c1, c2) -> c1));
    }

    public void createNewDrink(String name, BigDecimal price, Currency currency) {
        toppingRepository.save(ToppingEntity.builder()
                .name(name)
                .price(price)
                .currency(currency)
                .build());
    }

    public void removeDrinkById(UUID id) {
        toppingRepository.deleteById(id);
    }

    public Map<UUID, BigDecimal> getToppingPricesByIds(Collection<UUID> ids) {
        return toppingRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(ToppingEntity::getId, ToppingEntity::getPrice, (c1, c2) -> c1));
}
}
