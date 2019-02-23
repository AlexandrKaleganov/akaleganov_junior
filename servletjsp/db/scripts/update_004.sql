create table if not exists  users(
id serial primary key,
create_date Timestamp default now(),
name character(200),
login character(200),
pass character(200),
country character(200),
city character(200)
);