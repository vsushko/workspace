Postgress:<br>
(https://askubuntu.com/questions/50621/cannot-connect-to-postgresql-on-port-5432)
(https://stackoverflow.com/questions/10861260/how-to-create-user-for-a-db-in-postgresql)

how to run postgres:
```
sudo su - postgres
```
create database (https://www.postgresql.org/docs/9.0/static/sql-createdatabase.html):
```
create database sampledb owner username
```
check if a database exists:
```
SELECT 1 from pg_database WHERE datname='abc';
```
FireBird:<br>
install FireBird:
```
sudo apt-get install firebird2.5-superclassic
```
sets permissions for specific users, without changing the ownership of the directory:
```
setfacl -m u:username:rwx myfolder
```
sets permissions for specific users and to apply them recursively to all the subdirectories:
```
setfacl -R -m u:username:rwx myfolder
```
shows the output of top, restricted by grep to show only lines containing the characters fb:
```
top -b -n1 | grep fb
```
run firebird command line:
```
isql-fb
```
