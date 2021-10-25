--liquibase formatted sql

--changeset larionova:create-order-item-topping-table
create table order_item_topping
(
    id                       uuid  primary key not null,
    order_item_drink_id      uuid               references order_item_drink (id),
    item_id                  uuid              not null references topping (id),
    amount                   int               not null
);
