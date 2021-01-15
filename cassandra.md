### Client

https://hector-client.github.io/hector/build/html/index.html


https://hub.docker.com/_/cassandra


## Troubleshooting:

```
org.apache.cassandra.exceptions.ConfigurationException: commitlog directory '/var/lib/cassandra/commitlog' or, if it does not already exist, an existing parent directory of it, is not readable and writable for the DSE. Check file system and configuration
```
solution:
```
vim /etc/cassandra/conf/cassandra.yaml

```

create keyspace killrvideo with replication = { 'class': 'SimpleStrategy', 'replication_factor': 1 };

use killrvideo ;

create table videos (video_id timeuuid, added_date timestamp, Title text, primary key (video_id)); 

insert into videos(video_id, added_date, title) values(1645ea59-14bd-11e5-a993-8138354b7e31, '2014-01-29', 'Cassandra History') ;
insert into videos(video_id, added_date, title) values(245e8024-14bd-11e5-9743-8238356b7e32, '2012-04-03', 'Cassandra & SSDs') ;
insert into videos(video_id, added_date, title) values(3452f7de-14bd-11e5-855e-8738355b7e3a, '2013-03-17', 'Cassandra Intro') ;
insert into videos(video_id, added_date, title) values(4845ed97-14bd-11e5-8a40-8338255b7e33, '2013-10-16', 'DataStax DevCenter') ;
insert into videos(video_id, added_date, title) values(5645f8bd-14bd-11e5-af1a-8638355b8e3a, '2013-04-16', 'What is DataStax Enterprise?') ;

