﻿--добавим объекты в нашу таблицу объекты с цифрами 2 и 3 дублироваться будут
--insert into items (name) values('object1'), ('object1'), ('object2'),('object3'),('object3');
--select * from items;
--вот так мы можем найти все дубли и удалим:
delete from items where name in(select name from items group by name having count(*) > 1);

select * from items;