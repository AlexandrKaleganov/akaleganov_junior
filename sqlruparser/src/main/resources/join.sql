--create database icq;
create table if not exists users(
id serial primary key,
name varchar(200)
);
create table if not exists message(
id serial primary key,
message varchar(2000),
id_user int references users(id));
--insert into users(name) values('Vasia'),('Petia'),('Kolia'),('Sasha'),('Natasha');
select * from users;
--insert into message(message, id_user) values('message Vasia', 1),('Message Natasha', 5),('message  Natasha',5),('message Sasha', 4),('message Natasha', 5);
select * from message;
--многие ко многим
create table if not exists chat(
id serial primary key,
user_out int references users(id),
create_date Timestamp default now(),
user_input int references user(id));


--select u.name as "Клиент", u.create_date as "Дата регистрации в чате", c.message as "Сообщение" from users as u left outer join chat as c on c.id_user = u.id where c.id is not null;