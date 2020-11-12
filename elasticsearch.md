#### Installation on macOS
via brew:
```
brew tap elastic/tap
```
```
brew install elastic/tap/elasticsearch-full
```
then check and run:
```
brew services list
brew services start elasticsearch-full
```
remove:
```
brew uninstall <service_name> --force
```
check Elasticsearch cluster health:
```
curl localhost:9200/_cat/health
```
or:
```
curl -XGET 'localhost:9200/_cluster/health?pretty'
```
start up elasticsearch, overwriting the following settings at the command line (not best practice):
```
bin/elasticsearch -Enode.name=node-3 -Epath.data=./node-3/data -Epath.logs=./node-3/logs
```
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
simple query:
```
curl -XGET "http://localhost:9200/.kibana/_search" -H 'Content-Type: application/json' -d '{"query": {"match_all":{}}}'
```
simple query with authorization:
```
curl -XGET -u elastic:<password> "http://localhost:9200/.kibana/_search" -H 'Content-Type: application/json' -d '{"query": {"match_all":{}}}'
```
import data from file:
```
curl -H "Content-Type: application/x-ndjson" -XPOST http://localhost:9200/products/_bulk --data-binary "@file-name.json"
```

### Troubleshooting
##### low disk watermark [85%] exceeded on replicas will not be assigned to this node
https://stackoverflow.com/questions/33369955/low-disk-watermark-exceeded-on
mac via brew:
```
curl -XPUT -H "Content-Type: application/json" http://localhost:9200/_cluster/settings -d '{ "transient": { "cluster.routing.allocation.disk.threshold_enabled": false } }'

curl -XPUT -H "Content-Type: application/json" http://localhost:9200/_all/_settings -d '{"index.blocks.read_only_allow_delete": null}'
```

update field with primary term and sequence number:
```
POST /myindex/_update/<docId>/?if_primary_term=1&if_seq_no=10
{
  "doc": {
    "field": "fieldValue"
  }
}
```
