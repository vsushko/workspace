
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


To unlock liquibase:
```
UPDATE DATABASECHANGELOGLOCK SET LOCKED=FALSE, LOCKGRANTED=null, LOCKEDBY=null where ID=1;
```

