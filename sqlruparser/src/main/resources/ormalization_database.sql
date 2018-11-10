
--доказательство смысла нормализации базы по описанию вакансий 703 описания вакансий повторяются
select count(des.id) from description as des right outer join (select dat.description_id, count(id) from datatable as dat group by dat.description_id) as res on res.description_id = des.id where res.count > 1;
--доказательство смысла нормализации базы по автарам
select count(a.id) from author as a right outer join (select dat.author_id, count(id) from datatable as dat group by dat.author_id) as res on res.author_id = a.id where res.count > 1;
--доказательство смысла нормализации базы по наименованию тем
select count(t.id) from title as t right outer join (select dat.title_id, count(id) from datatable as dat group by dat.title_id) as res on res.title_id = t.id where res.count > 1;

--ТЕПЕРЬ ОБЪЕДЕНИМ ДАННЫЙ В ОДНОМ ЗАПРОСЕ И ВЫВЕДЕМ ИНФОРМАЦИЮ НАГЛЯДНО
select data.count as "Количество тем всего", aut.count as "Количество повторяющихся авторов", tit.count as "Количество повторяющихся наименований тем", descr.count as "Количество повторяющихся описаний вакансий" from 
(select count(id) from datatable) as data
inner join (select count(a.id) from author as a right outer join (select dat.author_id, count(id) from datatable as dat group by dat.author_id) as res on res.author_id = a.id where res.count > 1) as aut on aut.count!=data.count
inner join  (select count(t.id) from title as t right outer join (select dat.title_id, count(id) from datatable as dat group by dat.title_id) as res on res.title_id = t.id where res.count > 1) as tit on tit.count != data.count
inner join (select count(des.id) from description as des right outer join (select dat.description_id, count(id) from datatable as dat group by dat.description_id) as res on res.description_id = des.id where res.count > 1) as descr on descr.count != data.count;
