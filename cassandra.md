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
```sql
create keyspace killrvideo with replication = { 'class': 'SimpleStrategy', 'replication_factor': 1 };

use killrvideo ;

create table videos (video_id timeuuid, added_date timestamp, Title text, primary key (video_id)); 

insert into videos(video_id, added_date, title) values(1645ea59-14bd-11e5-a993-8138354b7e31, '2014-01-29', 'Cassandra History') ;
insert into videos(video_id, added_date, title) values(245e8024-14bd-11e5-9743-8238356b7e32, '2012-04-03', 'Cassandra & SSDs') ;
insert into videos(video_id, added_date, title) values(3452f7de-14bd-11e5-855e-8738355b7e3a, '2013-03-17', 'Cassandra Intro') ;
insert into videos(video_id, added_date, title) values(4845ed97-14bd-11e5-8a40-8338255b7e33, '2013-10-16', 'DataStax DevCenter') ;
insert into videos(video_id, added_date, title) values(5645f8bd-14bd-11e5-af1a-8638355b8e3a, '2013-04-16', 'What is DataStax Enterprise?') ;

select * from videos;

truncate videos;

docker cp ~/dse-resources/videos.csv 2bb3451ecf7e:/

copy videos(video_id, added_date, title) from '/videos.csv' with header=true;

select * from videos;

select count(*) from videos;

quit

describe table videos;

CREATE TABLE killrvideo.videos (
    video_id timeuuid PRIMARY KEY,
    added_date timestamp,
    title text
) WITH bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99PERCENTILE';

select token (video_id), video_id from videos;

 system.token(video_id) | video_id
------------------------+--------------------------------------
   -7805440677194688247 | 245e8024-14bd-11e5-9743-8238356b7e32
   -1963973032031712291 | 3452f7de-14bd-11e5-855e-8738355b7e3a
   -1613479371119279545 | 5645f8bd-14bd-11e5-af1a-8638355b8e3a
    3855558958565172223 | 1645ea59-14bd-11e5-a993-8138354b7e31
    7966101712501124149 | 4845ed97-14bd-11e5-8a40-8338255b7e33
    
    
Partitions group rows physically together on disk based on the partition key.

create table videos_by_tag (tag text, video_id timeuuid, added_date timestamp, title text, primary key ((tag), video_id)); 

COPY videos_by_tag(tag, video_id, added_date, title) FROM '/videos-by-tag.csv' WITH HEADER = TRUE;

select * from videos_by_tag;

select * from videos_by_tag WHERE tag = 'cassandra';

describe videos_by_tag;

CREATE TABLE killrvideo.videos_by_tag (
    tag text,
    video_id timeuuid,
    added_date timestamp,
    title text,
    PRIMARY KEY (tag, video_id)
) WITH CLUSTERING ORDER BY (video_id ASC)
    AND bloom_filter_fp_chance = 0.01
    AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
    AND comment = ''
    AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
    AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
    AND crc_check_chance = 1.0
    AND dclocal_read_repair_chance = 0.1
    AND default_time_to_live = 0
    AND gc_grace_seconds = 864000
    AND max_index_interval = 2048
    AND memtable_flush_period_in_ms = 0
    AND min_index_interval = 128
    AND read_repair_chance = 0.0
    AND speculative_retry = '99PERCENTILE';

```



