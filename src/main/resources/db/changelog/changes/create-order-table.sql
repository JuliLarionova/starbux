--liquibase formatted sql

--changeset larionova:create-order-table
create table order_
(
    id              uuid primary key not null,
    customer_id     uuid              not null references customer (id),
    status          text              not null
);
