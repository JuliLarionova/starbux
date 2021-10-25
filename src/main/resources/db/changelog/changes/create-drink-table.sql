--liquibase formatted sql

--changeset larionova:create-drink-table

create table drink
(
    id              uuid primary key not null,
    name            text             not null,
    price           int              not null,
    currency        text             not null
);

insert into drink (id, name, price, currency)
values (uuid_generate_v1(), 'Black Coffee', 4, 'euro'),
 (uuid_generate_v1(), 'Latte', 5, 'euro'),
 (uuid_generate_v1(), 'Mocha', 6, 'euro'),
 (uuid_generate_v1(), 'Tea', 3, 'euro');