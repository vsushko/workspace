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

python:
```
yum install python-pip
[Errno 14] PYCURL ERROR 22 - "The requested URL returned error: 403 Forbidden"
cd /etc/yum.repos.d
cp sandbox.repo /tmp
rm sandbox.repo
yum install python-pip
```
install mrjob
pip install mrjob==0.5.11
pip install google-api-python-client==1.6.4

wget http://media.sundog-soft.com/hadoop/ml-100k/u.data
http://media.sundog-soft.com/hadoop/RatingsBreakdown.py

python script.py -r hadoop --hadoop-streaming-jar /usr/hdp/current/hadoop-mapreduce-client/hadoop-streaming.jar hdfs://file-path

set ambari password:
```
su root
ambari-admin-password-reset
```
