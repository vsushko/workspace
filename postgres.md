# PostgreSQL

#### Download page:
```
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
```
find postgresql by port:
```
netstat -vanp tcp | grep 5432
```
#### Starting a server (macOS)
start postgresql:
```
sudo -u postgres /Library/PostgreSQL/16/bin/pg_ctl start -D /Library/PostgreSQL/16/data
```
#### Stopping a running server (macOS)
installation directory:
```
cd /Library/PostgreSQL/<version>/bin
```
stop the Postgresql server:
```
pg_ctl stop
```
if above fails:
```
pg_ctl stop -D /Library/PostgreSQL/<version>/data
```
alternative approach:
```
sudo lsof -i:5432
```

### Commands
list all users:
```
\du
```
change password of the porstgres user:
```
\password postgres
```
### Troubleshooting
role "postgres" does not exist
```
https://stackoverflow.com/questions/15301826/psql-fatal-role-postgres-does-not-exist
```
connecting to the docker container from outside:
```
https://stackoverflow.com/questions/37694987/connecting-to-postgresql-in-a-docker-container-from-outside
```
SQL Vacuum:
```
https://www.postgresql.org/docs/9.1/sql-vacuum.html
```
list databases:
```
SELECT datname FROM pg_database;
```
list tables:
```
\dt
```
postgress location:
```
sudo ls -l /usr/lib/postgresql/12
```

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
