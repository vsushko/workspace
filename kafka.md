 ## Common
starts zookeeper:
```
sh bin/zookeeper-server-start.sh config/zookeeper.properties 
```
zookeeper shell:
```
./zookeeper-shell.sh localhost:2181
```
starts kafka and zookeeper as a daemon:
```
sh bin/zookeeper-server-start.sh -daemon config/zookeeper.properties 
sh bin/kafka-server-start.sh -daemon config/server.properties 
```
or
```
~/kafka/bin/zookeeper-server-start.sh -daemon ~/kafka/config/zookeeper.properties 
~/kafka/bin/kafka-server-start.sh -daemon ~/kafka/config/server.properties
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
export KAFKA_HEAP_OPTS='-Xmx256M -Xms128M"

systemd file for kafka:
```
[Unit]
Description=Apache Kafka server (broker)
Documentation=http://kafka.apache.org/documentation.html
Requires=zookeeper.service

[Service]
Type=simple
ExecStart=/home/ubuntu/kafka/bin/kafka-server-start.sh /home/ubuntu/kafka/config/server.properties
ExecStop=/home/ubuntu/kafka/bin/kafka-server-stop.sh

[Install]
WantedBy=multi-user.target
```
enable service:
```
sudo systemctl enable kafka
```
check:
```
sudo systemctl status kafka
```
start:
```
sudo systemctl start kafka
```
check journalctl:
```
sudo journalctl -u kafka
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
will print out consumer gropus list:
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

## Kafka configs
list the configs:

```
kafka-configs.sh --zookeeper 127.0.0.1:2181 --entity-type topics --entity-name configured-topic --describe
```
add min.insync.replicas=2 to config:
```
kafka-configs.sh --zookeeper 127.0.0.1:2181 --entity-type topics --entity-name configured-topic --add-config min.insync.replicas=2 --alter
```
delete min.insync.replicas from config:
```
kafka-configs.sh --zookeeper 127.0.0.1:2181 --entity-type topics --entity-name configured-topic --delete-config min.insync.replicas --alter
```
log.retention.minutes to config(deprecated, use kafka-configs instead):
```
kafka-topics.sh --zookeeper localhost:2181 --alter --topic topic --config retention.ms=300000
```
list of configs:
```
 Key Value pairs of configs to add.     
                                         Square brackets can be used to group 
                                         values which contain commas: 'k1=v1, 
                                         k2=[v1,v2,v2],k3=v3'. The following  
                                         is a list of valid configurations:   
                                         For entity-type 'topics':            
                                       	cleanup.policy                        
                                       	compression.type                      
                                       	delete.retention.ms                   
                                       	file.delete.delay.ms                  
                                       	flush.messages                        
                                       	flush.ms                              
                                       	follower.replication.throttled.       
                                         replicas                             
                                       	index.interval.bytes                  
                                       	leader.replication.throttled.replicas 
                                       	max.message.bytes                     
                                       	message.downconversion.enable         
                                       	message.format.version                
                                       	message.timestamp.difference.max.ms   
                                       	message.timestamp.type                
                                       	min.cleanable.dirty.ratio             
                                       	min.compaction.lag.ms                 
                                       	min.insync.replicas                   
                                       	preallocate                           
                                       	retention.bytes                       
                                       	retention.ms                          
                                       	segment.bytes                         
                                       	segment.index.bytes                   
                                       	segment.jitter.ms                     
                                       	segment.ms                            
                                       	unclean.leader.election.enable        
                                       For entity-type 'brokers':             
                                       	log.message.timestamp.type            
                                       	ssl.client.auth                       
                                       	log.retention.ms                      
                                       	sasl.login.refresh.window.jitter      
                                       	sasl.kerberos.ticket.renew.window.    
                                         factor                               
                                       	log.preallocate                       
                                       	log.index.size.max.bytes              
                                       	sasl.login.refresh.window.factor      
                                       	ssl.truststore.type                   
                                       	ssl.keymanager.algorithm              
                                       	log.cleaner.io.buffer.load.factor     
                                       	sasl.login.refresh.min.period.seconds 
                                       	ssl.key.password                      
                                       	background.threads                    
                                       	log.retention.bytes                   
                                       	ssl.trustmanager.algorithm            
                                       	log.segment.bytes                     
                                       	max.connections.per.ip.overrides      
                                       	log.cleaner.delete.retention.ms       
                                       	log.segment.delete.delay.ms           
                                       	min.insync.replicas                   
                                       	ssl.keystore.location                 
                                       	ssl.cipher.suites                     
                                       	log.roll.jitter.ms                    
                                       	log.cleaner.backoff.ms                
                                       	sasl.jaas.config                      
                                       	principal.builder.class               
                                       	log.flush.interval.ms                 
                                       	log.cleaner.dedupe.buffer.size        
                                       	log.flush.interval.messages           
                                       	advertised.listeners                  
                                       	num.io.threads                        
                                       	listener.security.protocol.map        
                                       	log.message.downconversion.enable     
                                       	sasl.enabled.mechanisms               
                                       	sasl.login.refresh.buffer.seconds     
                                       	ssl.truststore.password               
                                       	listeners                             
                                       	metric.reporters                      
                                       	ssl.protocol                          
                                       	sasl.kerberos.ticket.renew.jitter     
                                       	ssl.keystore.password                 
                                       	sasl.mechanism.inter.broker.protocol  
                                       	log.cleanup.policy                    
                                       	sasl.kerberos.principal.to.local.rules
                                       	sasl.kerberos.min.time.before.relogin 
                                       	num.recovery.threads.per.data.dir     
                                       	log.cleaner.io.max.bytes.per.second   
                                       	log.roll.ms                           
                                       	ssl.endpoint.identification.algorithm 
                                       	unclean.leader.election.enable        
                                       	message.max.bytes                     
                                       	log.cleaner.threads                   
                                       	log.cleaner.io.buffer.size            
                                       	max.connections.per.ip                
                                       	sasl.kerberos.service.name            
                                       	ssl.provider                          
                                       	follower.replication.throttled.rate   
                                       	log.index.interval.bytes              
                                       	log.cleaner.min.compaction.lag.ms     
                                       	log.message.timestamp.difference.max. 
                                         ms                                   
                                       	ssl.enabled.protocols                 
                                       	log.cleaner.min.cleanable.ratio       
                                       	replica.alter.log.dirs.io.max.bytes.  
                                         per.second                           
                                       	ssl.keystore.type                     
                                       	ssl.secure.random.implementation      
                                       	ssl.truststore.location               
                                       	sasl.kerberos.kinit.cmd               
                                       	leader.replication.throttled.rate     
                                       	num.network.threads                   
                                       	compression.type                      
                                       	num.replica.fetchers                  
                                       For entity-type 'users':               
                                       	request_percentage                    
                                       	producer_byte_rate                    
                                       	SCRAM-SHA-256                         
                                       	SCRAM-SHA-512                         
                                       	consumer_byte_rate                    
                                       For entity-type 'clients':             
                                       	request_percentage                    
                                       	producer_byte_rate                    
                                       	consumer_byte_rate                    
                                       Entity types 'users' and 'clients' may 
                                         be specified together to update      
                                         config for clients of a specific     
                                         user. 
```                                         
## Kafka connect
resourse:
https://www.confluent.io/hub
## Kafka API
https://medium.com/@stephane.maarek/the-kafka-api-battle-producer-vs-consumer-vs-kafka-connect-vs-kafka-streams-vs-ksql-ef584274c1e
## Kafka topic naming conventions
https://medium.com/@criccomini/how-to-paint-a-bike-shed-kafka-topic-naming-conventions-1b7259790073
Mirror tool for kafka connect:
https://github.com/Comcast/MirrorTool-for-Kafka-Connect

## Kafka Monitoring and Operations
Monitoring Kafka
https://docs.confluent.io/current/kafka/monitoring.html
Monitoring Kafka performance metrics
https://www.datadoghq.com/blog/monitoring-kafka-performance-metrics/
Documetation:
https://kafka.apache.org/documentation/#monitoring

## Kafka Cluster

create properteis in config folder:
cp server.properties server0.properties
cp server.properties server1.properties
cp server.properties server2.properties

in these properties change:
broker.id=0
broker.id=1
broker.id=2

uncomment line (in server1.properties, server2.properties)
listeners=PLAINTEXT://:9093
listeners=PLAINTEXT://:9094

log.dirs=/Users/vsushko/apps/kafka_2.12-2.1.0/data/kafka1
log.dirs=/Users/vsushko/apps/kafka_2.12-2.1.0/data/kafka2

start zookeeper:
zookeeper-server-start.sh config/zookeeper.properties

start kafka:
kafka-server-start.sh config/server0.properties
kafka-server-start.sh config/server1.properties
kafka-server-start.sh config/server2.properties

kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic many-reps --create --partitions 6 --replication-factor 3

kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic manyeps --describe

the output will be:
```
Topic:many-reps	PartitionCount:6	ReplicationFactor:3	Configs:
Topic: many-reps	Partition: 0	Leader: 1	Replicas: 1,2,0	Isr: 1,2,0
Topic: many-reps	Partition: 1	Leader: 2	Replicas: 2,0,1	Isr: 2,0,1
Topic: many-reps	Partition: 2	Leader: 0	Replicas: 0,1,2	Isr: 0,1,2
Topic: many-reps	Partition: 3	Leader: 1	Replicas: 1,0,2	Isr: 1,0,2
Topic: many-reps	Partition: 4	Leader: 2	Replicas: 2,1,0	Isr: 2,1,0
Topic: many-reps	Partition: 5	Leader: 0	Replicas: 0,2,1	Isr: 0,2,1
```
 
write something to all brokers:
kafka-console-producer.sh --broker-list 127.0.0.1:9092,127.0.0.1:9093,127.0.0.1:9094 --topic many-reps
write something to one broker:
kafka-console-producer.sh --broker-list 127.0.0.1:9094 --topic many-reps

all messages is here

ls data/kafka1
ls data/kafka2
ls data/kafka3

kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9093 --topic many-reps --from-beginning
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

## Confluent CLI
Start all services or a specific service along with its dependencies (bin/confluent for more information):
```
bin/confluent start
```

## Kafka and Docker

export DOCKER_HOST=192.168.99.100

run (from https://github.com/simplesteph/kafka-stack-docker-compose) the command:
```
docker-compose -f zk-single-kafka-single.yml up
```

```
kafka-topics.sh --zookeeper 127.0.0.1:2181 --create --topic test --partitions 3 --replication-factor 1
```

## Kafka connect Archetype
[link](https://github.com/jcustenborder/kafka-connect-archtype)
```
mvn archetype:generate \
    -DarchetypeGroupId=com.github.jcustenborder.kafka.connect \
    -DarchetypeArtifactId=kafka-connect-quickstart \
    -DarchetypeVersion=2.0.0-cp1
```
```
mvn archetype:generate \
    -DarchetypeGroupId=com.github.jcustenborder.kafka.connect \
    -DarchetypeArtifactId=kafka-connect-quickstart \
    -DarchetypeVersion=2.0.0-cp1 \
    -Dpackage=com.github.jcustenborder.kafka.connect.test \
    -DgroupId=com.github.jcustenborder.kafka.connect \
    -DartifactId=testconnect \
    -DpackageName=com.github.jcustenborder.kafka.connect.test \
    -Dversion=1.0-SNAPSHOT
```

## Kafka security:
```
https://kafka.apache.org/documentation/#security_authz
https://docs.confluent.io/4.0.0/kafka/authorization.html
https://github.com/simplesteph/kafka-security-manager

https://github.com/steveloughran/kerberos_and_hadoop/blob/master/sections/errors.md
```
