create table  if not exists  users(
id serial primary key,
name varchar(200),
login varchar(200),
pass varchar(200),
create_date Timestamp default now());

