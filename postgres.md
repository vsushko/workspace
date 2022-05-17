\du
\password postgres

### Troubleshooting
role "postgres" does not exist
```
https://stackoverflow.com/questions/15301826/psql-fatal-role-postgres-does-not-exist
```
connecting to the docker container from outside:
```
https://stackoverflow.com/questions/37694987/connecting-to-postgresql-in-a-docker-container-from-outside
```
https://www.postgresql.org/docs/9.1/sql-vacuum.html

list tables:
```
\dt
```
postgress location:
`sudo ls -l /usr/lib/postgresql/12`

db cluster (PGDATA): 
`ls -l /var/lib/postgresql/12/main`

stop server:
`pg_ctlcluster 12 main stop`

start server: 
`pg_ctlcluster 12 main start`

restart serever: 
`pg_ctlcluster 12 main restart`

reload configuration:
`pg_ctlcluster 12 main reload`

server log:
`ls -l /var/log/postgresql/postgresql-12-main.log`

#### configuration parameters
`ls -l /etc/postgreesql/12/main`
main file postgresql.conf
ALTER SYSTEEM - postgresqul.auto.conf
for current session:
```
SET/RESET
set_config()
```
get current value:
```
SHOW
current_setting()
pg_settings

show work_mem:
`SHOW wwork_mem;`
