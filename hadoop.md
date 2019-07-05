## Hadoop: Setting up a Single Node Cluster.

https://hadoop.apache.org/docs/r3.0.3/hadoop-project-dist/hadoop-common/SingleCluster.html

http://localhost:8088/cluster

get hadoop version:
```
$HADOOP_HOME/bin/hadoop version
```

## troubleshooting:
#### error:
```
sudo ./start-dfs.sh
Starting namenodes on [localhost]
pdsh@user: localhost: rcmd: socket: Permission denied
Starting datanodes
pdsh@user: localhost: rcmd: socket: Permission denied
Starting secondary namenodes [user]
pdsh@user: user: rcmd: socket: Permission denied
```
#### solution
```
pdsh -q -w localhost
```
```
Rcmd type		rsh
```
edit ~/.bashrc, add:
```
export PDSH_RCMD_TYPE=ssh
```
#### error
```
java.io.FileNotFoundException: /data/hadoop/namenode/current/VERSION (Permission denied)
```
#### solution
```
sudo chown -R user:user /data/hadoop/
ls -la /data/hadoop/namenode/
```
