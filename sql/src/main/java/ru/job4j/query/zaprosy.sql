--запросы по заданию
select * from product;
--1. Написать запрос получение всех продуктов с типом "СЫР"
select p.name from product as p where p.type_id = 2;
--2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
select p.name from product as p where p.name like 'Мороженное%';
--3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
select * from product;
--этот запрос написать не получилось ошибки выдаёт написал два варианта всёравно не работает

select * from product as p where p.expiried_date between '2018-10-01 00:00:00' and '2018-11-30 00:00:00';

--4. Написать запрос, который выводит самый дорогой продукт.
select * from product as p where p.price = (select MAX(p.price) from product as p);
--5. Написать запрос, который выводит количество всех продуктов определенного типа.
select count(id) from product as p where p.type_id =2;
--6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
select * from product as p where p.type_id in (2, 3);
--7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук. 
select p.type_id, count(id) from product as p
group by p.type_id
having count(id) < 10;
--8. Вывести все продукты и их тип.
select p.name, t.name from product as p inner join type as t on p.type_id = t.id;
--select * from product;
