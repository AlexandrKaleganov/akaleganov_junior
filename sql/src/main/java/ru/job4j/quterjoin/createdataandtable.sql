--скрипт создания базы данных и таблиц
--create database "auto";
create table "transmission"(
      id serial primary key,
      name varchar(200)
);
create table "carbody"(
       id serial primary key,
       name varchar(200)
);
create table "engine" (
       id serial primary key,
       name varchar(200)
);
create table "car"(
      id serial primary key,
      name varchar(200),
      id_transmission integer references transmission(id),
      id_carbody integer references carbody(id),
      id_engine integer references engine(id));       