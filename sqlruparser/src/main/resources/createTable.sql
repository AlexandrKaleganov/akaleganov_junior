﻿--create database vacancy;
--title
create table  if not exists  title (
id serial PRIMARY KEY,
title varchar(200)  NOT NULL UNIQUE
);
--author
create table if not exists author (
id serial PRIMARY KEY,
author varchar(200)  NOT NULL UNIQUE
);
--description
create table if not exists description (
id serial PRIMARY KEY,
description varchar NOT NULL
);
--datatable
create table if not exists datatable(
id serial PRIMARY KEY,
url varchar(300)  NOT NULL UNIQUE,
title_id integer references title (id) not null,
author_id integer references author(id) not null,
description_id integer references description(id) not null,
date TimeStamp not null
);