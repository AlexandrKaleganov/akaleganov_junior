--создание таблиц и бд
--create database "filtres";
create table type(
       id serial primary key,
       name character(40)
);
create table product(
       id serial primary key,
       name character(40),
       type_id integer references type(id),
       expiried_date timestamp not null,
       price numeric(30,2)
);
