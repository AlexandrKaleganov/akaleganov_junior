create table if not exists  users(
id serial primary key,
create_date Timestamp default now(),
name varchar(200),
login varchar(200),
pass varchar(200)
);