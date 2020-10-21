## ZooKeeper
indicates that the ZooKeeper server process did not start:
```
ps uxww | grep zookeeper
```

```
echo ruok | nc localhost 2181
```
## ZooKeeper CLI
```
./zkCli.sh
```

enter zk cli:
```
zookeeper-shell.sh localhost:2181
```

check kafka brokers:
```
ls /brokers/ids
get /brokers/ids/1002
get /brokers/ids/1001
```

sample output:
```json
{"listener_security_protocol_map":{"PLAINTEXT":"PLAINTEXT"},"endpoints":["PLAINTEXT://185.86.144.172:6667"],"jmx_port":-1,"host":"185.86.144.172","timestamp":"1603287079138","port":6667,"version":4}
cZxid = 0x20000004c
ctime = Wed Oct 21 13:31:19 UTC 2020
mZxid = 0x20000004c
mtime = Wed Oct 21 13:31:19 UTC 2020
pZxid = 0x20000004c
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x3754b5921840001
dataLength = 198
numChildren = 0
```
