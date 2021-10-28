package com.bstlr.starbux.repository;

import com.bstlr.starbux.entity.order.OrderEntity;
import com.bstlr.starbux.entity.order.OrderEntity.OrderStatus;
import com.bstlr.starbux.service.order.OrdersPerCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByCustomerIdAndStatus(UUID customerId, OrderStatus status);

    @Query(value = "select o.id from OrderEntity o " +
            "inner join CustomerEntity cs " +
            "on cs.id = o.customerId " +
            "where cs.emailAddress = :emailAddress " +
            "and o.status = :status")
    Optional<UUID> getByCustomerEmailAddress(String emailAddress, OrderStatus status);

    @Query(value = "select new com.bstlr.starbux.service.order.OrdersPerCustomer(count(o), o.customerId, c.name) " +
            "from OrderEntity as o " +
            "inner join CustomerEntity as c " +
            "on c.id = o.customerId " +
            "group by o.customerId, c.name")
    List<OrdersPerCustomer> findOrdersPerCustomer();
}