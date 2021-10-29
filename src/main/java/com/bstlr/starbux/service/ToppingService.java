package com.bstlr.starbux.service;

import com.bstlr.starbux.common.ClientException;
import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.ToppingEntity;
import com.bstlr.starbux.repository.ToppingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ToppingService {
    private static final String NO_TOPPING_WITH_ID = "Topping with id = %s hasn't been found";
    private static final String TOPPING_ALREADY_EXISTS = "Topping with name %s and currency %s already exists with id %s";
    private final ToppingRepository repository;

    public UUID createNewTopping(String name, BigDecimal price, Currency currency) {
        Optional<ToppingEntity> ToppingOptional = repository.findByNameAndCurrency(name, currency);
        if (ToppingOptional.isPresent()) {
            throw new ClientException(String.format(TOPPING_ALREADY_EXISTS,
                    name, currency.toString(), ToppingOptional.get().getId()));
        }
        return repository.save(ToppingEntity.builder()
                .name(name)
                .price(price)
                .currency(currency)
                .build()).getId();
    }

    public void removeToppingById(UUID id) {
        repository.deleteById(id);
    }

    public Map<UUID, BigDecimal> getToppingPricesByIds(Collection<UUID> ids) {
        return repository.findAllById(ids).stream()
                .collect(Collectors.toMap(ToppingEntity::getId, ToppingEntity::getPrice, (c1, c2) -> c1));
    }

    public void updateById(UUID id, String name, BigDecimal price, Currency currency) {
        ToppingEntity topping = getByIdOrThrow(id);
        topping.setName(name);
        topping.setPrice(price);
        topping.setCurrency(currency);
        repository.save(topping);
    }

    public ToppingEntity getByIdOrThrow(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientException(String.format(NO_TOPPING_WITH_ID, id)));
    }

    public List<ToppingEntity> getAll() {
        return repository.findAll();
    }
}
