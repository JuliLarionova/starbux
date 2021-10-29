package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.ToppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToppingRepository extends JpaRepository<ToppingEntity, UUID> {
    Optional<ToppingEntity> findByNameAndCurrency(String name, Currency currency);
}