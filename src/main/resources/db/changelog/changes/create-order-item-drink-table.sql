--liquibase formatted sql

--changeset larionova:create-order-item-drink-table
create table order_item_drink
(
    id            uuid primary key not null,
    order_id      uuid             references order_ (id),
    item_id     uuid              not null references drink (id),
    total_cost    decimal           not null
);

