--liquibase formatted sql

--changeset larionova:create-topping-table
create table topping
(
    id              uuid primary key not null,
    name            text             not null,
    price           int              not null,
    currency        text             not null
);

insert into topping (id, name, price, currency)
values (uuid_generate_v1(), 'Milk', 2, 'euro'),
 (uuid_generate_v1(), 'Hazelnut syrup', 3, 'euro'),
 (uuid_generate_v1(), 'Chocolate sauce', 5, 'euro'),
 (uuid_generate_v1(), 'Lemon', 2, 'euro');