create table expense (
id serial not null primary key,
name varchar(50),
created_at DATE,
category int constraint exp_cat_fk references category(id) on delete set null,
amount decimal
);


create table category (
id serial not null primary key,
name varchar(50)
);


