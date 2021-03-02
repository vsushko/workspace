./bin/spark-sql

list databases:
```
show databases;
```
create database vsdb;

use vsdb;

create table persons(id integer, name string);

select * from persons;

insert into persons values (1, "Martin Odersky"), (2, "Matei Zaharia");

describe extended persons;

drop table persons;

create table flights(origin string, destination string) using csv options(header true, path "/home/myuser/data/flights");

insert into flights values ("Los Angeles", "New York"), ("London", "Prague");

select * from flights;

describe extended flights;
