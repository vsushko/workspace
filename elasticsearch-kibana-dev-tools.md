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
reindex API with script section and query:
```
POST /_reindex
{
  "source": {
    "index": "source_index",
    "query": {
      "match_all": {}
    },
    # for removing fields
    "_source": ["content", "created_at", "rating"]
  },
  "dest": {
    "index": "destination_index"
  },
  "script": {
    "source": """
      if (ctx._source.comment_id != null) {
        ctx._source.comment_id = ctx._source.comment_id.toString();
      }
      # rename "content" field to "comment"
      ctx._source.comment = ctx._source.remove("content");
      if (ctx._source.rating < 4.0) {
        # can also be set to "delete"
        ctx.op = "noop";
      }
    """
  }
}
```
aliases for renaming document field:
```
PUT /reviews/_mapping
{
  "properties": {
    "comment": {
      "type": "alias",
      "path": "content"
    }
  }
}

GET /reviews/_search
{
  "query": {
    "match": {
      "content": "outstanding"
    }
  }
}

GET /reviews/_search
{
  "query": {
    "match": {
      "comment": "outstanding"
    }
  }
}
```
index templates
```
PUT /_template/<name_of_index_template_to_create>
{
    "index_patterns": ["logs-*"],
    "settings": {
        "index.mapping.coerce": false
    },
    "mappings": {
        "properties": {
            "@timestamp": {
                "type": "date"
            }
        }
    }
}

PUT /logs-2020-01-01
GET /logs-2020-01-01
DELETE /_template/logs

```
custom analyzers:
```
PUT /analyzer_test
{
  "settings": {
    "analysis": {
      "analyzer": {
        "my_custom_analyzer": {
          "type": "custom",
          "char_filter": ["html_strip"],
          "tokenizer": "standard",
          "filter": [
            "lowercase",
            "stop",
            "asciifolding"
            ]
        }
      }
    }
  }
}

POST /_analyze
{
  "char_filter": ["html_strip"],
  "text": "I&apos;m in a <em>good</em> mood&nbsp;-&nbsp;and i <strong>love</strong> asdf!"
}

POST /analyzer_test/_analyze
{
  "analyzer": "my_custom_analyzer", 
  "text": "I&apos;m in a <em>good</em> mood&nbsp;-&nbsp;and i <strong>love</strong> asdf!"
}
```
Close/Open index:
```
POST /index/_close
POST /index/_open
```
Explains search query:
```
GET /books/_search
{
  "query": {
    "match": {
      "author": "Jane Austen"
    }
  },
  "explain": true
}
```
URI search:
```
GET /books/_search?q=author:Jane Austen
# q - query string
GET /books/_search?q=*
```
DSL search:
```
GET /books/_search
{
  "query": {
    "match_all": {
    }
  }
}
```
term level queries and full text queries:
```
GET /books/_search
{
  "query": {
    "term": {
      "author": "jane"
    }
  }
}

GET /books/_search
{
  "query": {
    "term": {
      "name": "Jane"
    }
  }
}

GET /books/_search
{
  "query": {
    "match": {
      "author": "Jane"
    }
  }
}
```
full text search queries:
```
# full text queries
GET /books/_mapping

# добавит документ с указанным id
POST /books/_doc/102
{
  "title": "Pride and Prejudice cut version",
  "author": "Jane Austen",
  "price": 5,
  "in_stock": 5
}

# match query
GET /books/_search
{
  "query": {
    "match": {
      "title": {
        "query": "Prejudice cut version",
        "operator": "or"
      }
    }
  }
}

# matching phrases
GET /books/_search
{
  "query": {
    "match_phrase": {
      "title": "Pride and"
    }
  }
}

# searching multiplee fields
GET /books/_search
{
  "query": {
    "multi_match": {
      "query": "Pride", 
      "fields": ["title", "author"]
    }
  }
}

# compound queries
GET /books/_search
{
  "query": {
    "bool": {
      "must": 
      [
        {
          "match": {
            "author": "Jane Austen"
          }
        },
        {
          "range": {
            "price": {
              "gte": 10
            }
          }
        }
      ]
    }
  }
}

# with filtering
GET /books/_search
{
  "query": {
    "bool": {
      "must": 
      [
        {
          "match": {
            "author": "Jane Austen"
          }
        }
      ],
      "filter": [
        {
          "range": {
            "price": {
              "gte": 10
            }
          }
        }
      ]
    }
  }
}

# must_not, should
GET /books/_search
{
  "query": {
    "bool": {
      "must": 
      [
        {
          "match": {
            "author": "Jane Austen"
          }
        }
      ],
      "must_not": [
        {
          "match": {
            "author": "Harper Lee"
          }
        }
      ],
      "should": [
        {
          "match": {
            "author": "Harper Lee"
          }
        }
      ], 
      "filter": [
        {
          "range": {
            "price": {
              "gte": 10
            }
          }
        }
      ]
    }
  }
}

# match 
GET /books/_search
{
  "query": {
    "match": {
      "title": "Pride and Prejudice"
    }
  }
}

# the same query
GET /books/_search
{
  "query": {
    "bool": {
      "should": 
      [
        {
          "term": {
            "title": {
              "value": "Pride"
            }
          }
        },
        {
          "term": {
            "title": {
              "value": "and"
            }
          }
        },
        {
          "term": {
            "title": {
              "value": "Prejudice"
            }
          }
        }
      ]
    }
  }
}
```
Nested inner hits
```
GET /department/_search
{
  "_source": false,
  "query": {
    "nested": {
      "path": "employees",
      "inner_hits": {},
      "query": {
        "bool": {
          "must": [
            {
              "match": {
                "employees.position": "intern"
              }
            },
            {
              "term": {
                "employees.gender.keyword": {
                  "value": "F"
                }
              }
            }
          ]
        }
      }
    }
  }
}
```
Querying by parent
```
GET /department/_search
{
  "query": {
    "parent_id": {
      "type": "employee",
      "id": 1
    }
  }
}
```

### Specifying the result format

Returning results as YAML:
```
GET /books/_search?format=yaml
{
    "query": {
      "match": { "author": "Jane" }
    }
}
```
Returning pretty JSON:
```
GET /books/_search?pretty
{
    "query": {
      "match": { "author": "Jane" }
    }
}
```
Excluding the _source field altogether
```
GET /books/_search
{
  "_source": false,
  "query": {
    "match": { "author": "Jane" }
  }
}
```
Only returning the price field
```
GET /books/_search
{
  "_source": "price",
  "query": {
    "match": { "author": "Jane" }
  }
}
```
### Specifying the result size
Using a query parameter
```
GET /books/_search?size=1
{
  "_source": false,
  "query": {
    "match": {
      "author": "Jane"
    }
  }
}
```
Using a parameter within the request body
```
GET /books/_search
{
  "_source": false,
  "size": 2,
  "query": {
    "match": {
      "author": "Jane"
    }
  }
}
```
Specifying an offset with the from parameter
```
GET /books/_search
{
  "_source": false,
  "size": 2,
  "from": 0,
  "query": {
    "match": {
      "author": "Jane"
    }
  }
}
```
