create table if not exists adreshelp(
user_id integer references users(id) primary key,
country_id integer references country(id),
city_id integer references city(id)
);

