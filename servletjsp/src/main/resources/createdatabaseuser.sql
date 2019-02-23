--create database usersdata;
create table  if not exists  users(id serial primary key,
create_date Timestamp default now()
name varchar(200), login varchar(200), password varchar(200), country varchar(200),city varchar(200));