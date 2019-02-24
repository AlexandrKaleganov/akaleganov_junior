create table if not exists  users(
id serial primary key,
create_date Timestamp default now(),
name varchar(200),
login varchar(200),
pass varchar(200)
);
create table if not exists country(
id serial primary key,
country varchar(200)
);
create table if not exists  city(
id serial primary key,
city varchar(200)
);

create table if not exists accesAttrib(
id serial primary key,
accesAttrib varchar(200)
);
create table adreshelp(
user_id integer references users(id) primary key,
country_id integer references country(id),
city_id integer references city(id)
);
create table accesAtribhelp(
user_id integer references users(id) primary key,
accesAttrib_id integer references accesAttrib(id)
);
create or replace view  userview  as select u.id, u.create_date, u.name, u.login, u.pass,  at.accesAttrib, co.country, ci.city from users as u inner join adreshelp as ad on ad.user_id = u.id
inner join country as co on co.id = ad.country_id
inner join city as ci on ci.id = ad.city_id
inner join accesAtribhelp as ath on ath.user_id = u.id
inner join accesAttrib as at on ath.accesAttrib_id = at.id;

