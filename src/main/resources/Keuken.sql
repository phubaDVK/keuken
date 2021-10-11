set names utf8mb4;
set charset utf8mb4;

drop database if exists keuken;
create database keuken charset utf8mb4;
use keuken;

create table artikels (
  id int unsigned not null auto_increment primary key,
  naam varchar(50) not null,
  aankoopprijs decimal(10,2) not null,
  verkoopprijs decimal(10,2) not null,
  index naam(naam)
);

insert into artikels(naam, aankoopprijs, verkoopprijs) values
('appel', 1.1, 1.9),
('peer', 1.2, 2.1),
('tomaat', 2.5, 3.8),
('wortel', 0.7, 1.2),
('mixer', 22, 30),
('pan', 45, 60);


create user if not exists cursist identified by 'cursist';
grant select,insert,update on artikels to cursist;