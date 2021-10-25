--liquibase formatted sql

--changeset larionova:create-customer-table
create table customer
(
    id            uuid  primary key not null,
    name          text              not null,
    email_address text              not null
);

alter table customer add constraint customer_uk unique (email_address);