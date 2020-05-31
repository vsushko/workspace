shows indecies:
```
GET /_cat/indices?v
```
shows shards:
```
GET /_cat/shards?v
```
cluster health:
```
GET /_cluster/health
```
shows basic information about single node:
```
GET /_cat/nodes?v
```
creates an index:
```
PUT /pages
```
adds document:
```
POST /users/_doc
{
  "name": "John",
  "surnamee": Doe,
  "age": 21
}
```
detetes index:
```
DELETE <index_name>
```
