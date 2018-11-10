--заполнение таблицы права ролей
--insert into rulles(rulles_name) values('чтение');
--insert into rulles(rulles_name) values('добавление коментариев');
--insert into rulles(rulles_name) values('редактирование заявок');
--insert into rulles(rulles_name) values('добавление заявок');

--заполнение таблицы ролей пользователей
--insert into role(role_name) values('root');
--insert into role(role_name) values('user');

--заполнение вспомогательной таблицы (позже узнал что можно написать попроще)
--insert into rulles_help(rulles_id, role_id) values(1, 1);
--insert into rulles_help(rulles_id, role_id) values(2, 1);
--insert into rulles_help(rulles_id, role_id) values(3, 1);
--insert into rulles_help(rulles_id, role_id) values(4, 1);
--insert into rulles_help(rulles_id, role_id) values(1, 2);
--insert into rulles_help(rulles_id, role_id) values(2, 2);
--insert into rulles_help(rulles_id, role_id) values(4, 2);

--вывод соотношения ролей и прав
select r1.role_name, r2.rulles_name from role as r1
inner join rulles_help as rh on r1.id = rh.role_id
inner join rulles as  r2 on r2.id = rh.rulles_id; 

--заполнение полей пользователи
--insert into users(name, role_id) values('AlexanderKaleganov', 1);
--insert into users(name, role_id) values('PeterArsentev', 2);

--изменение размеров полей
--alter table role alter column role_name type character(10);
--alter table rulles alter column rulles_name type character(30);
--alter table priority alter column name_priority TYPE character(50);
--alter table state alter column stat_name type character(10);
--alter table category alter column category_name type character(20);
--alter table items alter column name_items type character(80);
--alter table items alter column state_id set default 1;

--вывод пользователей, ролей и прав которые им соответствуют
select u.name, r1.role_name, r2.rulles_name from users as u
inner join role as r1 on u.role_id = r1.id
inner join rulles_help as rh on r1.id = rh.role_id
inner join rulles as  r2 on r2.id = rh.rulles_id;

--вывод списка пользователей и их ролей
select u.name, r.role_name from users as u
inner join role as r on r.id = u.role_id;


--заполнение полей таблицы приоритеты
--insert into priority(name_priority) values('Высокий');
--insert into priority(name_priority) values('Средний');
--insert into priority(name_priority) values('Низкий');
 
--заполнение полей таблицы статус заявки
--insert into state(stat_name) values('Новая'),('В работе'),('Исполнена');
select * from category;
--insert into category(category_name) values ('Настройка'),('Очная консультация'),('Технический ремонт');
select * from items;
--добавление двух заявок
--insert into items(user_id, name_items, category_id, state_id, priority_id) values(1, 'настроить таблицу', 1, default, 1);
--insert into items(user_id, name_items, category_id, state_id, priority_id) values(2, 'Необходима консультация по настройки таблицы', 2, default, 2);

select * from comment;

--добвление комментариев
--insert into comment(commeentary, item_id) values ('заявка взята в работу', 1);
--insert into comment(commeentary, item_id) values ('заявка взята в работу', 2);

--обновление статуса заявки
--update items set state_id = 2;

--финальный вывод данных
select i.id, u.name, r.role_name, i.name_items, i.date, ca.category_name, s.stat_name, c.commeentary from items as i
inner join users as u on u.id = i.user_id
inner join comment as c on c.item_id = i.id
inner join role as r on r.id = u.role_id
inner join category as ca on ca.id = i.category_id
inner join state as s on s.id  = i.state_id;