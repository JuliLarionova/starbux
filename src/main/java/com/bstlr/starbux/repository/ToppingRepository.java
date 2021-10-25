package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.ToppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface ToppingRepository extends JpaRepository<ToppingEntity, UUID> {
    Stream<ToppingEntity> findAllByNameIn(List<String> toppingNames);
}