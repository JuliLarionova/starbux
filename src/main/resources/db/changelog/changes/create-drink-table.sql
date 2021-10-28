--liquibase formatted sql

--changeset larionova:create-drink-table

create table drink
(
    id              uuid primary key not null,
    name            text             not null,
    price           decimal              not null,
    currency        text             not null
);

insert into drink (id, name, price, currency)
values ('a7e7ba88-35b9-11ec-bd22-bb29106ca7af', 'Black Coffee', 4, 'euro'),
 ('a7e85682-35b9-11ec-bd23-df2635677886', 'Latte', 5, 'euro'),
 ('a7e85683-35b9-11ec-bd24-ffed0eae523c', 'Mocha', 6, 'euro'),
 ('a7e87d74-35b9-11ec-bd25-c33959cdbf22', 'Tea', 3, 'euro');