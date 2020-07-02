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
# update with primary_term and seq_no
POST /users/_update/<index>?if_primary_term=1&if_seq_no=22
{
  "doc": {
    "age": 123
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
update by query:
```
POST /users/_update_by_query
{
  "conflicts": "proceed",
  "script": {
    "source": "ctx._source.age++" 
  },
  "query": {
    "match_all": {}
  }
}
```
delete by query:
```
POST /users/_delete_by_query
{
  "query": {
    "match_all": {}
  }
}
```
makes bulk request:
```
POST /_bulk
{ "index": { "_index": "users", "_id": 200 } }
{ "name": "Bob", "age": 19, "popular": false }
{ "create": { "_index": "users", "_id": 149 } }
{ "name": "Alice", "age": 19, "popular": true }

# with specifying index:
POST /users/_bulk
{ "update": { "_id": 200 } }
{ "doc": { "age": 20 } }
{ "delete": { "_id": 149 } }
```

detetes index:
```
DELETE <index_name>
```
optimistic concurrency control:
```
POST /books/_update/100/?if_primary_term=X&if_seq_no=X
{
  "doc": {
    "price": 30
  }
}
```
adds mapping:
```
PUT /reviews
{
  "mappings": {
    "properties": {
      "rating": { "type": "float" },
      "comment": { "type": "text" },
      "author": {
        "properties": {
          "first_name": { "type": "text" },
          "last_name": { "type": "text" },
          "email": { "type": "keyword" }
        }
      }
    }
  }
}
```
check mapping
```
PUT /reviews/_doc/1
{
  "rating": 5.0,
  "comment": "hilarious and touching. fun for the whole family.",
  "author": {
    "first_name": "Nick",
    "last_name": "Black",
    "email": "nick@black.com"
  }
}
```
```
GET /reviews/_mapping
```
check fields mapping:
```
GET /reviews/_mapping/field/rating
GET /reviews/_mapping/field/comment
GET /reviews/_mapping/field/author.email
```
mapping with dot notation:
```
PUT /reviews_with_dot_notation
{
  "mappings": {
    "properties": {
      "rating": { "type": "float" },
      "comment": { "type": "text" },
      "author.first_name": { "type": "text" },
      "author.last_name": { "type": "text" },
      "author.email": { "type": "keyword" }
    }
  }
}
```
