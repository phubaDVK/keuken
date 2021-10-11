set names utf8mb4;
set charset utf8mb4;
use keuken;
alter table artikels add column soort enum('F','NF') not null,
 add column garantie int unsigned,
 add column houdbaarheid int unsigned;
update artikels set soort='F', houdbaarheid=7 where id=1;
update artikels set soort='F', houdbaarheid=5 where id=2;
update artikels set soort='F', houdbaarheid=6 where id=3;
update artikels set soort='F', houdbaarheid=14 where id=4;
update artikels set soort='NF', garantie=36 where id=5;
update artikels set soort='NF', garantie=48 where id=6;