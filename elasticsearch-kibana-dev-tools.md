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
returns specified index by id:
```
GET /users/_doc/<_id>
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
updates document field:
```
POST /users/_update/<index>
{
  "doc": {
    "name": "Bob"
  }
}
```
adds new field to document:
```
POST /users/_update/<index>
{
  "doc": {
    "newFieldKey": "newFieldValue"
  }
}
```
update field using script:
```
POST /users/_update/<index>
{
  "script": {
    "source": "ctx._source.age++"
}
# assigning concrete value
POST /users/_update/<index>
{
  "script": {
    "source": "ctx._source.age = 50"
}
# using params
POST /users/_update/<index>
{
  "script": {
    "source": "ctx._source.age += params.quantity"
    "params": {
      "quantity": 4
    }
}
# with condition
POST /users/_update/<index>
{
  "script": {
    "source": """
      if (ctx._source.viewed > 10000) {
        ctx._source.viewed++;
        // ctx.op = 'noop';
      }
      ctx._source.popular = true;
    """
  }
}
POST /users/_update/<index>
{
  "script": {
    "source": """
      if (ctx._source.viewed > 1000000) {
        ctx.op = 'delete';
      }
    """
  }
}
```
upsert op:
```
POST /users/_update/<index>
{
  "script": {
    "source": "ctx._source.age++"
  },
  "upsert": {
    "name": "Blb",
    "age": 20
  }
}
```
detetes index:
```
DELETE <index_name>
```
