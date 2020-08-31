Downloads: https://hbase.apache.org/downloads.html

start hbase script - `start-hbase.sh`

start hbase script - `stop-hbase.sh`

starting hbase shell:
```
bin/hbase shell
```
url:
```
http://localhost:16010/master-status
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
put data into table:
```
put 'people', 'vsushko', 'd:hair', 'black'
```
retrieve data from table:
```
get 'people', 'vsushko'
```
add more data:
```
put 'people', 'enis', 'd:hair', 'brown'
put 'people', 'stack', 'd:hair', 'none'
put 'people', 'busbey', 'd:beard', 'scruffy'
put 'people', 'apurtell', 'd:beard', 'grey'
```
scan:
```
scan 'people'
```
more queries:
```
get 'people', 'stack'
scan 'people', {STARTROW => 'kevin'}
```
add more data
```
put 'people', 'jmhsieh', 'd:role-hbase', 'pmc'
put 'people', 'stack', 'd:role-hbase', 'none'
put 'people', 'busbey', 'd:role-phoenix', 'mentor'
put 'people', 'apurtell', 'd:role-hbase', 'chair'
```
delete row:
```
delete 'people', 'stack', 'd:hair'
```
update row:
```
put 'people', 'busbey', 'd:beard', 'trim'
```
delete all:
```
deleteall 'people', 'apurtell'
```
disable table:
```
disable 'people'
```
drop table:
```
 drop 'people'
```
run script:
```
bin/hbase shell -n people.rb
```


### INSTALLATION
https://commandstech.com/hbase-error-keeperrorcode-connectionloss-for-hbase-in-cluster/
https://www.engati.com/blog/install-hbase-on-mac-in-5-minutes

## TROUBLE SHOOTING
ERROR: KeeperErrorCode = ConnectionLoss for /hbase/master
https://community.cloudera.com/t5/Support-Questions/KeeperErrorCode-ConnectionLoss-for-hbase-master/td-p/134130

