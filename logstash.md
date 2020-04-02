Reloading the config file (https://www.elastic.co/guide/en/logstash/current/reloading-config.html#reloading-config):
```
bin/logstash -f apache.config --config.reload.automatic
```

### Commands
check logstash status:
```
systemctl status logstash
```
check logstash plugins:
```
/usr/share/logstash/bin/logstash-plugin list
```

### Info
logstash plain log:
```
less /var/log/logstash/logstash-plain.log
```
logstash pipeline config:
```
cat /etc/logstash/pipelines.yml
```
