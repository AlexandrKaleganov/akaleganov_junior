create table if not exists  users(
id serial primary key,
name varchar(200),
mail varchar(200),
pass varchar(200)
);
create table if not exists country(
id serial primary key,
country varchar(200)
);
create table if not exists  city(
id serial primary key,
country_id integer references country(id),
city varchar(200)
);
create table if not exists adreshelp(
user_id integer references users(id) primary key,
city_id integer references city(id),
country_id integer references country(id)
);
