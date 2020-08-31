Downloads: https://hbase.apache.org/downloads.html

start hbase script - `start-hbase.sh`

start hbase script - `stop-hbase.sh`

starting hbase shell:
```
bin/hbase shell
```

#### HBase shell commands

list tables:
``` list ```

sample config:
```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href=”configuration.xsl"?>
<configuration>
    <property>
        <name>hbase.rootdir</name>
        <value>file:///tmp</value>
    </property>
    <property>
        <name>hbase.zookeeper.property.dataDir</name>
        <value>/tmp/zookeeper</value>
    </property>
    <property>
        <name>hbase.cluster.distributed</name>
        <value>true</value>
    </property>
    <property>
        <name>hbase.unsafe.stream.capability.enforce</name>
        <value>false</value>
    </property>
</configuration>
```

### SHELL

creates simple table with single column family:
```
create 'people', 'd'
```
describes table:
```
describe 'people'
```


## TROUBLE SHOOTING
ERROR: KeeperErrorCode = ConnectionLoss for /hbase/master
https://community.cloudera.com/t5/Support-Questions/KeeperErrorCode-ConnectionLoss-for-hbase-master/td-p/134130

