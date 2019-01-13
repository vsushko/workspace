 ## Common
starts zookeeper:
```
zookeeper-server-start.sh config/zookeeper.properties 
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
deletes the topic (depends on delete.topic.enable, mark to delete when is no set to true), make sure that the flag `delete.topic.enable=true`, otherwise this will have no impact:
```
kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic second_topic --delete
```
### kafka-console-producer
creates the producer:
```
kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first_topic
```
creates the producer with specified acks:
```
kafka-console-producer.sh --broker-list 127.01:9092 --topic first_topic --producer-property acks=all
```
### kafka-console-consumer
```
kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic first_topic
```
will dump out messages to standard output:
```
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic
```
will dump out messages to standard output from the beginning:
```
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning
```
with specifying group:
```
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application
```
then ex run several:
```
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application
```
run and do input:
```
kafka-console-producer.sh --broker-list7.0.0.1:9092 --topic first_topic
```
and this command will dump out messages to standard output from the beginning but it will be another group:
```
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-second-application --from-beginning
```
### kafka-consumer-groups
will print out consuber gropus list:
```
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list
```
describes which app or which consumer is consuming onto your topic with specified consumer group:
```
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-second-application
```
resetting offsets:
```
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --to-earliest --execute --topic first_topic
```
after running the command:
```
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --group my-first-application
```
kafka will read all messages from first_topic, and after that:
```
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group my-first-application
```
we can check out offsets, then we can shift the offset:
```
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --group my-first-application --reset-offsets --shift-by -4 --execute --topic first_topic
```
producer with keys:
```
kafka-console-producer --broker-list 127.0.0.1:9092 --topic first_topic --property parse.key=true --property key.separator=,
```
consumer with keys:
```
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning --property print.key=true --property key.separator=,

```

## Kafka connect
resourse:
(https://www.confluent.io/hub)
## Kafka API
(https://medium.com/@stephane.maarek/the-kafka-api-battle-producer-vs-consumer-vs-kafka-connect-vs-kafka-streams-vs-ksql-ef584274c1e)
## Kafka topic naming conventions
(https://medium.com/@criccomini/how-to-paint-a-bike-shed-kafka-topic-naming-conventions-1b7259790073)


## Kafka Monitoring and Operations
Monitoring Kafka
https://docs.confluent.io/current/kafka/monitoring.html
Monitoring Kafka performance metrics
https://www.datadoghq.com/blog/monitoring-kafka-performance-metrics/
Documetation:
https://kafka.apache.org/documentation/#monitoring

## Kafka Cluster and Replication
Kafka mirroring (MirrorMaker):
https://cwiki.apache.org/confluence/pages/viewpage.action?pageId=27846330
MirrorMaker Performance Tuning:
https://engineering.salesforce.com/mirrormaker-performance-tuning-63afaed12c21
Tuning and Monitoring Replicator:
https://docs.confluent.io/current/multi-dc-replicator/replicator-tuning.html#improving-network-utilization-of-a-connect-task
Kafka Mirror Maker Best Practices:
https://community.hortonworks.com/articles/79891/kafka-mirror-maker-best-practices.html
Multi-Tenant, Multi-Cluster and Hierarchical Kafka Messaging Service
https://www.confluent.io/kafka-summit-sf17/multitenant-multicluster-and-hieracrchical-kafka-messaging-service
uReplicator: Uber Engineering’s Robust Apache Kafka Replicator:
https://eng.uber.com/ureplicator/
Multi-Cluster Deployment Options for Apache Kafka: Pros and Cons:
https://www.altoros.com/blog/multi-cluster-deployment-options-for-apache-kafka-pros-and-cons/

