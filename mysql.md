## Troubleshooting
```
Caused by: java.sql.SQLException: The server time zone value 'MSK' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
```
solution:
```
spring.datasource.url = jdbc:mysql://localhost:3306/ppmtcourse?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
```
MySQL server startup error 'The server quit without updating PID file':
```
https://stackoverflow.com/questions/4963171/mysql-server-startup-error-the-server-quit-without-updating-pid-file
```
```
brew services

# Remove each instance by running
brew uninstall <instance_name>

# Delete the MySQL directory in /usr/local/var/mysql:
rm -rf /usr/local/var/mysql

# Reinstall MySQL using Homebrew:
brew install mysql
brew install mysql@<version> #Optional

# Rerun mysql.server start:
mysql.server start
```
## Workbench
[download link](https://dev.mysql.com/downloads/workbench/)

## MySQL Community Server
[download link](https://dev.mysql.com/downloads/mysql/)

## CLI 
connect to db:
```
mysql -h localhost:3306 -u user -p
```

## Tutorials
#### Accessing data with MySQL
https://spring.io/guides/gs/accessing-data-mysql/
