package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.OrderItemDrinkEntity;
import com.bstlr.starbux.entity.OrderItemToppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemToppingRepository extends JpaRepository<OrderItemToppingEntity, UUID> {
}