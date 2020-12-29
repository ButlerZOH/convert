drop database if exists convertingstation;
CREATE database if not EXISTS convertingstation;
use convertingstation;

drop table if exists user;
create table user
(
	  id        varchar(34),
    name  char(15),
    password  char(11)
);

insert into user values('1','admin','123456'),('2','asd','asd'),('3','xunyong','123456');
select * from user;

drop table if exists converter;
create table converter
(
		id       varchar(34),
		voltage  char(10),
		name     char(50)
);

insert into converter values("1","800Kv","换流站101");
select voltage,name from converter;
select * from converter where name="";
-- SELECT name from converter where;

drop table if exists transformer;
create table transformer
(
		id       varchar(34),
		voltage  char(10),
		name     char(50)
);

insert into transformer values(1,"110Kv","变电站101");
select * from transformer;

drop table if exists mix;
create table mix
(
		id    varchar(34) not null,
		name  varchar(50)not null PRIMARY key
);
insert into mix values('1','混合电站');

drop table if exists pathload;
create table pathload(
	id int not null,
	name  varchar(50) not null,
	path  varchar(80) not null
);
insert into pathload values(1,'混合电站','asd');







