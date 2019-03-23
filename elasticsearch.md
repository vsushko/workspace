java rest client getting started:
```
https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-getting-started-maven.html
```
dejavu - UI for Elasticsearch:
```
https://github.com/appbaseio/dejavu
```
for the managing elasticsearch in the cloud: 
```
https://app.bonsai.io/clusters
```
maven configuration:
```
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>elasticsearch-rest-high-level-client</artifactId>
    <version>6.5.4</version>
</dependency>
```
running as a daemon:
```
./bin/elasticsearch -d -p pid
```

add index:
```
curl -X PUT "localhost:9200/twitter"
```
search twitter index:
```
curl -X GET "localhost:9200/twitter/_search?q=user:kimchy"
```
beautify json responses:
```
apk update && apk add jq
```
