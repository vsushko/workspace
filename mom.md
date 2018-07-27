run kafka server:
```
sudo  /opt/Kafka/kafka_2.10-0.10.0.1/bin/kafka-server-start.sh /opt/Kafka/kafka_2.10-0.10.0.1/config/server.properties
```
run server as a background process:
```
sudo nohup /opt/Kafka/kafka_2.10-0.10.0.1/bin/kafka-server-start.sh /opt/Kafka/kafka_2.10-0.10.0.1/config/server.properties /tmp/kafka.log 2>&1 &
```
