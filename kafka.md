starts zookeeper:
```
zookeeper-server-start.sh ../config/zookeeper.prerties 
```

cat config/zookeeper.properties
dataDir=/Users/myuser/apps/kafka_2.12-2.1.0/data/zookeeper
vim config/zookeeper.properties
mkdir data/zookeeper
ls data/zookeeper/
mkdir data/kafka
ls data/kafka/
log.dirs=/Users/myuser/apps/kafka_2.12-2.1.0/data/kafka
kafka-server-start.sh config/server.properties 
