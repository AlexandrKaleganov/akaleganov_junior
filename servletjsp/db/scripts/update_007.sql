create or replace view  userview  as select u.id, u.create_date, u.name, u.login, u.pass,  at.accesAttrib, co.country, ci.city from users as u inner join adreshelp as ad on ad.user_id = u.id
inner join country as co on co.id = ad.country_id
inner join city as ci on ci.id = ad.city_id
inner join accesAttribhelp as ath on ath.user_id = u.id
inner join accesAttrib as at on ath.accesAttrib_id = at.id;

