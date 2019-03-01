create or replace view userview  as select u.id, u.name, u.mail, u.pass, co.country, ci.city from users as u
inner join adreshelp as ad on ad.user_id = u.id
inner join country as co on co.id = ad.country_id
inner join city as ci on ci.id = ad.city_id;