### Commands
Check status:
```
service clickhouse-server status
```

### Queries
show tables:
```
clickhouse-client --port 9090 --query="show tables"
```

### UI
https://tabix.io/doc/Install/<br>

0. `vim /etc/clickhouse-server/config.xml`
1. Uncomment section `http_server_default_response`
2. Go to `http://localhost:8123`
3. default user login - `default`

how to remove data (https://stackoverflow.com/questions/52355143/is-it-possible-to-delete-old-records-from-clickhouse-table):
```
ALTER TABLE <table> DELETE WHERE 1=1
```
