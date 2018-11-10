--items
create table if not exists  items(
       id serial primary key,
       name varchar(100),
       descc varchar(500),
       created timestamp not null default now()
       );
--comments
create table if not exists comments(
       id serial primary key,
       comment varchar(500),
       id_items integer references items(id));