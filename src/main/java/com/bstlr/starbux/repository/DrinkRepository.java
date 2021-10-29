package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.Currency;
import com.bstlr.starbux.entity.DrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkEntity, UUID> {
    Optional<DrinkEntity> findByNameAndCurrency(String name, Currency currency);
}