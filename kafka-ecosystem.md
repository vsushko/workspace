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

