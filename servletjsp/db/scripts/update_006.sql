create table if not exists accesAtribhelp(
user_id integer references users(id) primary key,
accesAttrib_id integer references accesAttrib(id)
);

