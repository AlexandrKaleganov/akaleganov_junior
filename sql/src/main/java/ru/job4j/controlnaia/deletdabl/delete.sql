--добавим объекты в нашу таблицу объекты с цифрами 2 и 3 дублироваться будут
--insert into items (name) values('1'), ('2'), ('3'),('1'), ('8'),('8'),('8');
--select * from items;
--вот так мы можем найти все дубли и удалим:
delete from items where id in (select i.id from items as i left outer join (select max(id), name from items group by name having count(*) > 1 ) as z on i.name = z.name where z.name is not null and i.id != z.max);
select * from items;