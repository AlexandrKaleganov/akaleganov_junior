--create database users;
create table  if not exists  users(id serial primary key, name character(200), login character(200), create_date Timestamp default now());