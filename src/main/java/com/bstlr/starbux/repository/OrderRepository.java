package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.OrderEntity;
import com.bstlr.starbux.entity.OrderEntity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findAllByCustomerIdAndStatus(UUID customerId, OrderStatus status);
}