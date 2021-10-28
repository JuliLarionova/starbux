--liquibase formatted sql

--changeset larionova:create-topping-table
create table topping
(
    id              uuid primary key not null,
    name            text             not null,
    price           decimal              not null,
    currency        text             not null
);

insert into topping (id, name, price, currency)
values ('a7eac5b6-35b9-11ec-bd26-0fcfb3a57818', 'Milk', 2, 'euro'),
 ('a7eb139a-35b9-11ec-bd27-37356cbc8415', 'Hazelnut syrup', 3, 'euro'),
 ('a7eb139b-35b9-11ec-bd28-57d68ed4f398', 'Chocolate sauce', 5, 'euro'),
 ('a7eb139b-35b9-11ec-bd28-57d68ed4f399', 'Lemon', 2, 'euro');