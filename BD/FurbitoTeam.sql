drop database FurbitoTeam;

create database if not exists FurbitoTeam;
use FurbitoTeam;

create table if not exists modelosCartas(
id int auto_increment not null primary key,
    imageLink longtext not null,
    tier int not null,
    media int not null,
    pac int not null,
    sho int not null,
    pas int not null,
    dri int not null,
    def int not null,
    phy int not null
);

insert into modelosCartas (imageLink, tier, media, pac, sho, pas, dri, def, phy) values 
("https://i.ibb.co/NmyTtdx/Byronv10.png", 3, 83, 90, 85, 81, 88, 74, 78),
("https://i.ibb.co/mH6KLXd/Byron-JVv10.png", 4, 86, 93, 88, 84, 91, 77, 81),
("https://i.ibb.co/VDVgFns/Danetiv10.png", 3, 76, 85, 79, 76, 84, 72, 60),
("https://i.ibb.co/W65VkhC/Elyayov10.png", 3, 76, 76, 64, 73, 69, 85, 89),
("https://i.ibb.co/qkfc7vC/Pukav10.png", 2, 69, 74, 79, 75, 64, 67, 52),
("https://i.ibb.co/4V2CJ3S/Pricev10.png", 1, 62, 65,53, 72, 54, 80, 48),
("https://i.ibb.co/0YFRvmv/Talleresv10.png", 3, 81, 85, 71, 76, 76, 86, 90);


create table if not exists usuarios(
id int auto_increment not null primary key,
    nombre_usuario varchar(30) not null unique,
    pass varchar(30) not null default "1234",
    tiradas int not null default 100
);

create table if not exists poolDeCartas(
id_unico int auto_increment not null primary key,
    id_modelo int not null,
    id_usuario int not null
);
alter table poolDeCartas
add constraint fk_pool_usuario foreign key (id_usuario) references usuarios(id),
add constraint fk_pool_modelo foreign key (id_modelo) references modelosCartas(id);

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

create table if not exists ranking(
    nombre_usuario varchar(30) not null,
    puntos int not null default 0,
    puntosIRL int not null default 0
);
alter table ranking add constraint fk_nombre_usuario foreign key (nombre_usuario) references usuarios(nombre_usuario);

insert into ranking (nombre_usuario) values 
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
select * from modelosCartas;
select * from poolDeCartas;
select * from inventario;
SELECT * FROM inventario AS i INNER JOIN cartas AS c on i.id_carta = c.id WHERE i.id_usuario = 4;
*/