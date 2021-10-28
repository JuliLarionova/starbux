package com.bstlr.starbux.service.order;

import com.bstlr.starbux.entity.order.OrderItemDrinkEntity;
import com.bstlr.starbux.repository.OrderItemDrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemDrinkService {
    private final OrderItemDrinkRepository repository;

    public List<OrderItemDrinkEntity> findOrderItemsByOrderId(UUID orderId) {
        return repository.findAllByOrderId(orderId);
    }
}
