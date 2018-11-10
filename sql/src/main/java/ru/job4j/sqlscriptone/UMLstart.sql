--create database "tracker";

create table role (
       id serial primary key,
       role_name character
);
create table users(
       id serial primary key,
       name character(200),
       role_id int references role(id)
);
create table rulles(
       id serial primary key,
       rulles_name character(200)
);
create table rulles_help(
       id serial primary key,
       rulles_id int references rulles(id),
       role_id int references role(id)
);
create table state(
       id serial primary key,
       stat_name character(200) unique
);
create table category(
       id serial primary key,
       category_name character(2000) unique
);   
create table priority(
       id serial primary key,
       name_priority character(200) unique
);
create table items(
       id serial primary key,
       user_id int references users(id) unique,
       name_items character(200),
       date timestamp not null default now(),
       category_id int references category(id),
       state_id int references state(id),
       priority_id int references priority(id)
);  
create table comment(
       id serial primary key,
       commeentary character(2000),
       item_id int references comment(id)
);
create table attachs(
       id serial primary key,
       attach bytea,   --не знал какой тип использоавать для хранения файлов
       item_id int references attachs(id)
);
       