create table  if not exists  error(
id serial primary key,
err varchar(10000),
create_date Timestamp default now());