set names utf8mb4;
set charset utf8mb4;

use keuken;

create table artikelgroepen (
  id int unsigned not null auto_increment primary key,
  naam varchar(50) not null,
  index naam(naam)
);

insert into artikelgroepen(naam) values
('fruit'),('groente'),('machines'),('potten en pannen');

alter table artikels add column artikelgroepId int unsigned not null;
update artikels set artikelgroepId = 1 where id in (1, 2);
update artikels set artikelgroepId = 2 where id in (3, 4);
update artikels set artikelgroepId = 3 where id = 5;
update artikels set artikelgroepId = 4 where id = 6;

alter table artikels
 add constraint FK_artikelgroepen foreign key FK_artikelgroepen (artikelgroepId)
    references artikelgroepen(id);

grant select,insert on artikelgroepen to cursist;