 ## Common
starts zookeeper:
```
zookeeper-server-start.sh ../config/zookeeper.prerties 
```
starts kafka:
```
kafka-server-start.sh config/server.properties 
```
changing zookeeper dataDir:
```
mkdir data/zookeeper
ls data/zookeeper/
cat config/zookeeper.properties
vim config/zookeeper.properties
dataDir=/Users/myuser/apps/kafka_2.12-2.1.0/data/zookeeper
```
changing kafka log.dir:
```
mkdir data/kafka
ls data/kafka/
vim config/server.properties
log.dirs=/Users/myuser/apps/kafka_2.12-2.1.0/data/kafka
```
 ## Kafka cli
### kafka-topics
creates kafka topic with name "first_topic" (one running broker and zookeeper is required):
```
kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first_topic --create --partitions 3 --replication-factor 1
```
shows the list of topics:
```
kafka-topics.sh --zookeeper 127.0.0.1:2181 --list
```
gives extended information about "first_topic":
```
kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic first_topic --describe
```
deletes the topic (depends on delete.topic.enable, mark to delete when is no set to true):
```
kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second_topic --delete
```
### kafka-console-producer
creates the producer
```
kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first_topic
```
