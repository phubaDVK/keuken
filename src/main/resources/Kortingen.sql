set names utf8mb4;
set charset utf8mb4;
use keuken;
  create table kortingen (
  artikelId int unsigned not null,
  vanafAantal int unsigned not null,
  percentage decimal(10,2) not null,
  unique index kortingenvanaf(artikelId, vanafaantal),
  constraint fk_kortingen_artikels foreign key (artikelId) references artikels(id));

insert into kortingen(artikelId,vanafAantal,percentage)
select id,5,1+id mod 10 from artikels;

insert into kortingen(artikelId,vanafAantal,percentage)
select id,10,10+id mod 10 from artikels;

insert into kortingen(artikelId,vanafAantal,percentage)
select id,20,20+id mod 10 from artikels;

grant select,insert on kortingen to cursist;