drop database FurbitoTeam;

create database if not exists FurbitoTeam;
use FurbitoTeam;

create table if not exists cartas(
	id int auto_increment not null primary key,
    imageLink longtext not null,
    tier int not null,
    alt varchar(20) not null
);

insert into cartas (imageLink, tier, alt) values 
("https://i.ibb.co/B6v4gjL/Byronv10.png", 3, "Carta de Pablopri"),
("https://i.ibb.co/swSHVQX/Byron-JVv10.png", 4, "Carta de Pablopri JV"),
("https://i.ibb.co/VDVgFns/Danetiv10.png", 3, "Carta de Daneti"),
("https://i.ibb.co/W65VkhC/Elyayov10.png", 3, "Carta de Yayo"),
("https://i.ibb.co/qkfc7vC/Pukav10.png", 2, "Carta de Puka"),
("https://i.ibb.co/4V2CJ3S/Pricev10.png", 1, "Carta de Price"),
("https://i.ibb.co/0YFRvmv/Talleresv10.png", 3, "Carta de Talleres");


create table if not exists usuarios(
	id int auto_increment not null primary key,
    nombre_usuario varchar(30) not null unique,
    pass varchar(30) not null default "1234",
    tiradas int not null default 100
);

create table if not exists poolDeCartas(
	id_unico int auto_increment not null primary key,
    imageLink longtext not null,
    tier int not null,
    alt varchar(20) not null
);

create table if not exists inventario(
	id_usuario int not null,
    id_carta int not null
);
alter table inventario 
add constraint fk_inventario_usuario foreign key (id_usuario) references usuarios(id),
add constraint fk_inventario_carta foreign key (id_carta) references poolDeCartas(id_unico);

insert into usuarios (nombre_usuario) values
("Byronhate"),
("Danetibm"),
("Talleres"),
("Pukaso"),
("Pricelol"),
("Elyayo");

create table if not exists equipo(
	id_usuario int not null,
    id_carta int,
    pos varchar(5) not null
);
alter table equipo
add constraint fk_equipo_usuario foreign key (id_usuario) references usuarios(id),
add constraint fk_equipo_carta foreign key (id_carta) references poolDeCartas(id_unico);

insert into equipo values 
(1,null,"por"),(1,null,"defd"),(1,null,"defi"),(1,null,"deld"),(1,null,"deli"),
(2,null,"por"),(2,null,"defd"),(2,null,"defi"),(2,null,"deld"),(2,null,"deli"),
(3,null,"por"),(3,null,"defd"),(3,null,"defi"),(3,null,"deld"),(3,null,"deli"),
(4,null,"por"),(4,null,"defd"),(4,null,"defi"),(4,null,"deld"),(4,null,"deli"),
(5,null,"por"),(5,null,"defd"),(5,null,"defi"),(5,null,"deld"),(5,null,"deli"),
(6,null,"por"),(6,null,"defd"),(6,null,"defi"),(6,null,"deld"),(6,null,"deli");

create table if not exists rankingVersion(
    nombre_usuario varchar(30) not null,
    puntos int not null default 0
);
alter table rankingVersion add constraint fk_nombre_usuario foreign key (nombre_usuario) references usuarios(nombre_usuario);

insert into rankingVersion (nombre_usuario) values 
("Byronhate"),
("Danetibm"),
("Talleres"),
("Pukaso"),
("Pricelol"),
("Elyayo");


/*
select * from usuarios;
select * from rankingVersion;
select * from equipo;
select * from equipo;
select * from cartas;
select * from poolDeCartas;
select * from inventario;
SELECT * FROM inventario AS i INNER JOIN cartas AS c on i.id_carta = c.id WHERE i.id_usuario = 4;
*/