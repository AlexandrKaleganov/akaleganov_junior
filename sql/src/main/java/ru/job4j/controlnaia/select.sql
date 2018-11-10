--create database controlnaia;
--CREATE TABLE company
--(
--id integer NOT NULL,
--name character varying,
--CONSTRAINT company_pkey PRIMARY KEY (id)
--);

--CREATE TABLE person
--(
--id integer NOT NULL,
--name character varying,
--company_id integer,
--CONSTRAINT person_pkey PRIMARY KEY (id)
--);
--insert into company(id, name) values (1, 'АгроТех'), (2, 'Евротех'), (3, 'HappyPrint'), (4, 'Лаботон'), (5, 'Открытые технологии'), (6, 'job4j.ru');
--select * from company;
--insert into person(id, name, company_id) values (1, 'Peter Arsentev', 6), (2, 'Тарарыкин Иван', 1), (3, 'Самылкина-Борскина Евгения', 5), (4, 'Гоман Дмитрий', 3), (5, 'Andrei Hincu', 6), (6, 'Киртиш Денис', 6),(7, 'Вася', 1);

--1) Получить в одном запросе: - имена всех лиц, которые НЕ принадлежат компании с id = 5; - название компании для каждого человека
select p.name, c.name from person as p inner join company as c on p.company_id = c.id WHERE p.company_id != 5;
--2) Выберите название компании с максимальным количеством лиц + количество человек в этой компании
select c.name as "Название компаний", r.count as "Количество человек в фирме" from company as c right outer join (select p.company_id, count(id) from person as p group by p.company_id) as r on c.id = r.company_id where r.count = 
(select max(m.count)  from (select count(id) from person as p group by p.company_id) as m);