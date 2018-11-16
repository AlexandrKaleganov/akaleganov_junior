--create database vacancy;
--title
create table  if not exists  title (
id serial PRIMARY KEY,
title varchar(200)  NOT NULL UNIQUE
);
--author
create table if not exists author (
id serial PRIMARY KEY,
author varchar(200)  NOT NULL UNIQUE
);
--description
create table if not exists description (
id serial PRIMARY KEY,
description varchar NOT NULL
);
--datatable
create table if not exists datatable(
id serial PRIMARY KEY,
url varchar(300)  NOT NULL UNIQUE,
title_id integer references title (id) not null,
author_id integer references author(id) not null,
description_id integer references description(id) not null,
date TimeStamp not null
);
create view statistic as
select data.count as "Количество тем всего", aut.count as "Количество повторяющихся авторов", tit.count as "Количество повторяющихся наименований тем", descr.count as "Количество повторяющихся описаний вакансий" from 
(select count(id) from datatable) as data
inner join (select count(a.id) from author as a right outer join (select dat.author_id, count(id) from datatable as dat group by dat.author_id) as res on res.author_id = a.id where res.count > 1) as aut on aut.count!=data.count
inner join  (select count(t.id) from title as t right outer join (select dat.title_id, count(id) from datatable as dat group by dat.title_id) as res on res.title_id = t.id where res.count > 1) as tit on tit.count != data.count
inner join (select count(des.id) from description as des right outer join (select dat.description_id, count(id) from datatable as dat group by dat.description_id) as res on res.description_id = des.id where res.count > 1) as descr on descr.count != data.count;
