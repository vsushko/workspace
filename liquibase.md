
Liquibase CLI:
```
https://www.liquibase.com/download#download-liquibase
```
Run the update script command:
```
liquibase --driver=com.mysql.jdbc.Driver --classpath="mysql-connector-j-8.3.0.jar" --changeLogFile=db-changelog.xml --url="jdbc:mysql://localhost:3306/db" --username=username --password=<somepassword> update
```
Rollback the specified number of changes made to the database:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> rollback-count 1
```
Mark the current database state with the specified tag:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> tag before_upgrading_to_version_1111 
```
Deploy the specified number of changes from the changelog file:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> update-count 2
```
Rollback changes made to the database based on the specific tag:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> rollback before_upgrading_to_version_1111 
```
Rollback changes made to the database based on the specific date:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> rollback-to-date 2024-02-04 15:44:58
```

Generate the SQL to deploy changes in the changelog which have not been deployed:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> --output-file=update.sql update-sql
```
Generate the raw SQL needed to rollback undeployed changes:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> --output-file=update.sql future-rollback-sql
```
Updates database, then rolls back changes before updating again. Useful for testing rollback support:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db/changelog/db.changelog-master.yaml --searchPath="<path>" --url="jdbc:mysql://localhost:3306/db" --username=username --password=<pwd> --output-file=update.sql update-testing-rollback
```
To unlock liquibase:
```
UPDATE DATABASECHANGELOGLOCK SET LOCKED=FALSE, LOCKGRANTED=null, LOCKEDBY=null where ID=1;
```
### Existing Projects Migration:
- Ignore the history
- Create historical changesets with <sql> tag
- Generate historical changesets

### Legacy Project
Initiate Liquibase:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db.changelog-master.yaml --url="jdbc:mysql://localhost:3306/employees" --username=<user> --password=<password> --output-file=rollback.sql update
```
Marks all changes as executed in the database:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db.changelog-master.yaml --url="jdbc:mysql://localhost:3306/employees" --username=<user> --password=<password> changelog-sync
```
Generate a changelog:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db.changelog-master.yaml --url="jdbc:mysql://localhost:3306/employees" --username=<user> --password=<password>  generate-changelog
```
Marks all changes as executed in the database:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db.changelog-master.yaml --url="jdbc:mysql://localhost:3306/employees" --username=<user> --password=<password>  changelog-sync
```
#### Liquibase Community changelog example does not include generating:
- package
- packagebody
- function
- stored procedure
- trigger
- view

#### Changest hash
Drop all database objects:
```
liquibase --driver=com.mysql.cj.jdbc.Driver --classpath="/Users/vsushko/workspace/lb/mysql-connector-j-8.3.0/mysql-connector-j-8.3.0.jar" --changeLogFile=db.changelog-master.yaml --url="jdbc:mysql://localhost:3306/employees" --username=<user> --password=<password>  drop-all
```
