## Troubleshooting
```
Caused by: java.sql.SQLException: The server time zone value 'MSK' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the serverTimezone configuration property) to use a more specifc time zone value if you want to utilize time zone support.
```
solution:
```
spring.datasource.url = jdbc:mysql://localhost:3306/ppmtcourse?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
```

## Workbench
[download link](https://dev.mysql.com/downloads/workbench/)

## MySQL Community Server
[download link](https://dev.mysql.com/downloads/mysql/)
