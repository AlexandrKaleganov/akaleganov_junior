select * from carbody;

--insert into transmission(name) values ('forSedan'), ('forJeep'),('forMicroautobus'),('forSpectehnika');
--insert into carbody(name) values('sedan'), ('jeep'), ('microautobus'), ('spectehnika');
--insert into engine(name) values('vitek'),  ('RN10'), ('QG18'), ('DizelDvigatel'), ('QG15');
--insert into car(name, id_transmission, id_carbody, id_engine) values ('Honda Civic', 1, 1, 1), ('nissan sunny', 1, 1, 3),  ('nissan Qasqai', 2, 2, 2),  ('nissan serena', 3, 3, 4);

select * from car;
--Вывести список всех машин и все привязанные к ним детали.
select c.name, t.name, cb.name, e.name from car as c inner join transmission as t on c.id_transmission = t.id inner join carbody as cb on c.id_carbody = cb.id inner join engine as e on c.id_engine = e.id;
--Вывести отдельно детали, которые не используются в машине, кузова, двигатели, коробки передач.
select t.name from transmission as t left outer join car as c on c.id_transmission = t.id where c.id is null;
select cb.name from carbody as cb left outer join car c on c.id_carbody = cb.id where c.id is null;
select e.name from car as c right outer join engine as e on e.id = c.id_engine where c.id is null;