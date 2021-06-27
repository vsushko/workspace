## ZooNavigator: UI to navigate Zookeeper Data
https://github.com/elkozmon/zoonavigator
https://zoonavigator.elkozmon.com/en/latest/features.html

docker-compose file:
```
version: '2.1'

services:
  web:
    image: elkozmon/zoonavigator-web:0.5.0
    container_name: zoonavigator-web
    ports:
     - "7070:7070"
    environment:
      WEB_HTTP_PORT: 7070
      API_HOST: "api"
      API_PORT: 9000
    depends_on:
     - api
  api:
    image: elkozmon/zoonavigator-api:0.5.0
    container_name: zoonavigator-api
    environment:
      API_HTTP_PORT: 9000
```
run command:
```
docker-compose -f docker-file-path.yaml up
```
http://localhost:9000/clusters/KafkaCluster/topics

## Kafka Manager: UI to explore Kafka Metadata and perform basic administration tasks
https://github.com/yahoo/CMAK

docker file:
https://github.com/provectus/kafka-ui
```
# /etc/docker/compose/kafka-manager/docker-compose.yml
version: '3.6'
services:
  kafka_manager:
    image: hlebalbau/kafka-manager:1.3.3.18
    ports:
      - "9000:9000"
    environment:
      ZK_HOSTS: "ip:2181"
      APPLICATION_SECRET: "random-secret"
    command: -Dpidfile.path=/dev/null
```

## Kafka Monitor: UI that runs a producer/consumer and ensures Kafka is available and working
https://github.com/linkedin/kafka-monitor
https://github.com/linkedin/kafka-monitor/pull/220


You can find this in the file 0-basic-admin-tools/cheat-sheet.md

### ZooNavigator
Automatically Launch at Boot Time:
```
sudo systemctl enable docker-compose@zoonavigator
```
Start
```
sudo systemctl start docker-compose@zoonavigator
```


### Kafka Manager
Automatically Launch at Boot Time:
```
sudo systemctl enable docker-compose@kafka-manager
```
Start
```
sudo systemctl start docker-compose@kafka-manager
```


### Kafka Monitor
Automatically Launch at Boot Time:
```
sudo systemctl enable kafka-monitor
```
Start
```
sudo systemctl start kafka-monitor
```
### Monitoring
#### JMX Exporter
https://github.com/prometheus/jmx_exporter

configs:
https://github.com/prometheus/jmx_exporter/tree/master/example_configs

https://grafana.com/grafana/dashboards/721


### Dashboard from Confluent in Grafana:
https://github.com/confluentinc/cp-helm-charts/tree/master/grafana-dashboard

### Jolokia
Jolokia (https://jolokia.org/) is remote JMX with JSON over HTTP.
It is fast, simple, polyglot and has unique features. It's JMX on Capsaicin.

### Yelp kafka utils
https://github.com/Yelp/kafka-utils

updating kafkaf configurations:
https://kafka.apache.org/documentation/#brokerconfigs

download kafka binaries:
https://archive.apache.org/dist/kafka/1.1.1/kafka_2.12-1.1.1.tgz

linkedin kafka tools:
https://github.com/linkedin/kafka-tools#kafka-tools

Kafka Assigner:
https://github.com/linkedin/kafka-tools/wiki/Kafka-Assigner

Module: Balance
https://github.com/linkedin/kafka-tools/wiki/module-balance

Module: Set Replication Factor
https://github.com/linkedin/kafka-tools/wiki/module-set-replication-factor

Module: Remove
https://github.com/linkedin/kafka-tools/wiki/module-remove

Kafka upgrade:
https://kafka.apache.org/documentation/#upgrade

Efficient data transfer through zero copy:
https://developer.ibm.com/articles/j-zerocopy/

KIP-283:
https://cwiki.apache.org/confluence/display/KAFKA/KIP-283%3A+Efficient+Memory+Usage+for+Down-Conversion
