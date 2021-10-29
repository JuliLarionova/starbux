package com.bstlr.starbux.service;

import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.DrinkEntity;
import com.bstlr.starbux.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DrinkService {
    private static final String NO_DRINK_WITH_ID = "Drink with id %s hasn't been found";
    private static final String DRINK_ALREADY_EXISTS = "Drink with name %s and currency %s already exists with id %s";
    private final DrinkRepository repository;

    public Map<UUID, BigDecimal> getDrinkPricesByIds(Collection<UUID> ids) {
        return repository.findAllById(ids).stream()
                .collect(Collectors.toMap(DrinkEntity::getId, DrinkEntity::getPrice, (c1, c2) -> c1));
    }

    public UUID createNewDrink(String name, BigDecimal price, Currency currency) {
        Optional<DrinkEntity> drinkOptional = repository.findByNameAndCurrency(name, currency);
        if (drinkOptional.isPresent()) {
            throw new ClientException(String.format(DRINK_ALREADY_EXISTS,
                    name, currency.toString(), drinkOptional.get().getId()));
        }
        return repository.save(DrinkEntity.builder()
                .name(name)
                .price(price)
                .currency(currency)
                .build()).getId();
    }

    public void removeDrinkById(UUID id) {
        repository.deleteById(id);
    }

    public void updateById(UUID id, String name, BigDecimal price, Currency currency) {
        DrinkEntity drink = getByIdOrThrow(id);
        drink.setName(name);
        drink.setPrice(price);
        drink.setCurrency(currency);
        repository.save(drink);
    }

    public DrinkEntity getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientException(String.format(NO_DRINK_WITH_ID, id)));
    }

    public List<DrinkEntity> getAll() {
        return repository.findAll();
    }
}
