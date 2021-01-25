### Client

https://hector-client.github.io/hector/build/html/index.html

create network:
```
docker network create cassandra-network
```
Start cassandra:
```
docker run --name vsushko-cassandra --network cassandra-network -d cassandra:latest
```

start another node:
```
docker run --name vsushko-cassandra2 -d --network cassandra-network -e CASSANDRA_SEEDS=vsushko-cassandra cassandra:latest
```
```
docker exec -it vsushko-cassandra bash
```
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
DROP KEYSPACE IF EXISTS killrvideo;
drop table videos;

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

create table videos_by_tag (tag text, video_id timeuuid, added_date timestamp, title text, primary key ((tag), added_date, video_id)) with clustering order by (added_date DESC); 

COPY videos_by_tag(tag, video_id, added_date, title) FROM '/videos-by-tag.csv' with header = true;
INSERT INTO killrvideo.videos_by_tag (tag, added_date, video_id, title) VALUES ('cassandra', '2016-2-8', uuid(), 'Me Lava Cassandra');

select * from videos_by_tag;

describe videos_by_tag;

select * from videos_by_tag where tag = 'cassandra' order by added_date ASC;

select * from videos_by_tag where tag = 'cassandra' and added_date >= '2013-1-1' order by added_date ASC;

dsetool status
nodetool info
nodetool describecluster
nodetool getlogginglevels
nodetool setlogginglevel org.apache.cassandra TRACE
nodetool settraceprobability 0.1
nodetool drain
nodetool stopdaemon
dse cassandra

We will now stress the node using a simple tool called Apache Cassandra(TM) Stress. Once
your node has restarted, navigate to the
/home/ubuntu/node/resources/cassandra/tools/bin directory in the terminal. Run
cassandra-stress to populate the cluster with 50,000 partitions using 1 client thread and
without any warmup using:

cassandra-stress write n=50000 no-warmup -rate threads=1

nodetool flush
nodetool status
DESCRIBE KEYSPACES;
USE keyspace1;
DESCRIBE TABLES;
SELECT * FROM standard1 LIMIT 5;

# determinte which nodes own which partitions in the videos_by_tag table
SELECT token(tag), tag FROM videos_by_tag;

# refresh your memory as to which nodes own which token ranges:
nodetool ring

# returns the ip addresses of the nodes(s) whichi store the partitions with the respective partition key yvaluee
./nodetool getendpoints killrvideo videos_by_tag 'cassandra'
./nodetool getendpoints killrvideo videos_by_tag 'datastax'

vnodes:
Edit cassandra.yaml. Uncomment num_tokens if necessary and set it to 128.
Comment out initial_token. Do this for both node1 and node2.
Restart /home/ubuntu/node1/bin/dse cassandra
./nodetool status

Datacenter: Cassandra
=====================
Status=Up/Down
|/ State=Normal/Leaving/Joining/Moving
-- Address Load Tokens Owns Host ID Rack
UN 127.0.0.1 138.6 KiB 128 ? 30cdb721-74e2-4335-af61-74d8b9fb445f rack1
UN 127.0.0.2 111.38 KiB 128 ? 574dbde2-62db-48b8-9abb-d3e4bd2e1c0f rack1
./nodetool ring


./nodetool gossipinfo

# system.peers which stores some gossip data about a node's peers:
SELECT peer, data_center, host_id, preferred_ip, rack, release_version, rpc_address, schema_version FROM system.peers;

peer       | data_center | host_id                              | preferred_ip | rack  | release_version | rpc_address | schema_version
------------+-------------+--------------------------------------+--------------+-------+-----------------+-------------+--------------------------------------
172.25.0.3 | datacenter1 | db464a77-ce04-4148-ad69-38ebb958c89f |         null | rack1 |          3.11.9 |  172.25.0.3 | e84b6a60-24cf-30ca-9b58-452d92911703

A snitch determines which datacenters and racks nodes belong to. They inform Cassandra about the network topology so that requests are routed efficiently and allows Cassandra to distribute replicas by grouping machines into datacenters and racks.

cassandra.yaml
endpoint_snitch: com.datastax.bdp.snitch.DseSimpleSnitch

# Replication

CREATE KEYSPACE killrvideo
WITH replication = {
 'class': 'NetworkTopologyStrategy',
 'east-side': 1,
 'west-side': 1
};


# consistency 
CONSISTENCY;
Current consistency level is ONE.

only one node must acknowledge a write on a
write request, and only one node must return a result set to satisfy a read request.

#update:
CONSISTENCY TWO; 

TWO is the same as ALL because our current replication settings store
one replica per data center, and we have two data centers.

# commit log

watch -n 1 -d "ls -lh /home/ubuntu/node/data/commit-log"

/opt/cassandra/tools/bin# ./cassandra-stress write no-warmup n=250000 -port native=9041 -rate threads=1

# `cfstats` gives you column family stats
nodetool cfstats keyspace1.standard1
nodetool cfstats 

# Execute the following nodetool command which will flush the memtable contents to disk.
# /home/ubuntu/node/resources/cassandra/bin/nodetool flush

# Execute the following command to view the current bloom filter settings:

DESCRIBE keyspace keyspace1;
cqlsh> DESCRIBE keyspace keyspace1;
CREATE KEYSPACE keyspace1 WITH replication = {'class':
'SimpleStrategy', 'replication_factor': '1'} AND durable_writes
= true;
CREATE TABLE keyspace1.counter1 (
 key blob,
 column1 text,
 "C0" counter static,
 "C1" counter static,
 "C2" counter static,
 "C3" counter static,
 "C4" counter static,
 value counter,
 PRIMARY KEY (key, column1)
) WITH COMPACT STORAGE
 AND CLUSTERING ORDER BY (column1 ASC)
 AND bloom_filter_fp_chance = 0.01
 ...
 AND speculative_retry = '99PERCENTILE';
CREATE TABLE keyspace1.standard1 (
 key blob PRIMARY KEY,
 "C0" blob,
 "C1" blob,
 "C2" blob,
 "C3" blob,
 "C4" blob
) WITH COMPACT STORAGE
 AND bloom_filter_fp_chance = 0.01
 ...
 AND speculative_retry = '99PERCENTILE';

# Note the bloom_filter_fp_chance is 0.01, meaning a 1% chance of a false positive.
# That's pretty good, but if we want an even smaller chance, we can trade space for it.

ALTER TABLE keyspace1.standard1 WITH bloom_filter_fp_chance = 0.0001;
# Now that we have changed the bloom_filter_fp_chance, we must update our
# SSTables and associated bloom filter files

/home/ubuntu/node/resources/cassandra/bin/nodetool upgradesstables --include-all-sstables

# nodetool upgradesstables rebuilds SSTables for a specified keyspace and table.
# We are doing this here to rebuild the Bloom Filters also. Normally this command will
# only upgrade sstables if the sstables are not at the most recent SSTable version; the --
# include-all-sstables flag forces the rebuild to occur. Normally you would need to
# run nodetool upgradesstables on each node. For the purposes of this exercise, we
# only have one node.

# Now examine the new size of your bloom filter files.
ubuntu@ds201-node1:/home/ubuntu/node/data/data/keyspace1/standard1-
9b3cd4711f7111e79d8a7b0e72657334$ ls -lh *Filter.db
-rw-rw-r-- 1 ubuntu ubuntu 1.4K Apr 16 23:43 aa-16-bti-Filter.db
-rw-rw-r-- 1 ubuntu ubuntu 487K Apr 16 23:43 aa-17-bti-Filter.db
-rw-rw-r-- 1 ubuntu ubuntu 616K Apr 16 23:43 aa-18-bti-Filter.db

# Notice the size of these filesis larger. We traded space for a smaller chance of a false positive.


```



