package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.order.OrderItemToppingEntity;
import com.bstlr.starbux.service.MostUsedTopping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderItemToppingRepository extends JpaRepository<OrderItemToppingEntity, UUID> {

    @Query(value = "select new com.bstlr.starbux.service.MostUsedTopping(" +
            "sum(o.amount) as sum_, o.itemId, t.name) from OrderItemToppingEntity as o " +
            "join ToppingEntity as t " +
            "on o.itemId = t.id " +
            "group by o.itemId, t.name " +
            "order by sum_ desc")
    Page<MostUsedTopping> findTop1MostUsedTopping(Pageable pageable);
}