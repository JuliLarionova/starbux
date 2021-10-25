package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.DrinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface DrinkRepository extends JpaRepository<DrinkEntity, UUID> {
    Stream<DrinkEntity> findAllByNameIn(List<String> drinkNames);
}