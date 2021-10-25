package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.OrderItemDrinkEntity;
import com.bstlr.starbux.web.dto.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemDrinkRepository extends JpaRepository<OrderItemDrinkEntity, UUID> {
    Optional<OrderItemDrinkEntity> findByOrderIdAndItemId(
            UUID orderId, UUID itemId);

    List<OrderItemDrinkEntity> findAllByOrderId(UUID orderId);
}