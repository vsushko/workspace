connect to hortonworks docker sandbox:
```
ssh maria_dev@127.0.0.1 -p 2222
```
list the files:
```
hadoop fs -ls
```
make dir:
```
hadoop fs -mkdir ml-100k
```
upload data into hdfs:
```
hadoop fs -copyFromLocal u.data ml-100k/u.data
```
check
```
hadoop fs -ls ml-100k
```
delete uploaded data:
```
hadoop fs -rm ml-100k/u.data
```
remove dir:
```
hadoop fs -rmdir ml-100k
```
list all available commands:
```
hadoop fs
```
