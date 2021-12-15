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
### Security:
1. edit elasticsearch.yml
```
xpack.security.enabled: true
```
2.
```
curl http://localhost:9200
```
```
{"error":{"root_cause":[{"type":"security_exception","reason":"missing authentication credentials for REST request [/]","header":{"WWW-Authenticate":"Basic realm=\"security\" charset=\"UTF-8\""}}],"type":"security_exception","reason":"missing authentication credentials for REST request [/]","header":{"WWW-Authenticate":"Basic realm=\"security\" charset=\"UTF-8\""}},"status":401}
```
3.
```
bin/elasticsearch-setup-passwords auto
```

```
Changed password for user apm_system
PASSWORD apm_system = *

Changed password for user kibana_system
PASSWORD kibana_system = *

Changed password for user kibana
PASSWORD kibana = *

Changed password for user logstash_system
PASSWORD logstash_system = *

Changed password for user beats_system
PASSWORD beats_system = *

Changed password for user remote_monitoring_user
PASSWORD remote_monitoring_user = *

Changed password for user elastic
PASSWORD elastic = *
```
4.
```
cd /usr/local/var/homebrew/linked/kibana-full
```
5.
```
elasticsearch.username: "kibana_system"
elasticsearch.password: "***"
```
go to kibana 
```
elastic:password
```

### Spaces
Sharing saved-objects in multiple spaces
```
https://github.com/elastic/kibana/issues/27004
```

Importing data into local cluster:
```
curl -H "Content-Type: application/x-ndjson" -XPOST http://localhost:9200/_bulk --data-binary "@nginx-access-logs-2020-01.bulk.ndjson"
```
