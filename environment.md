set vim as default system editor:
```
sudo update-alternatives –config editor
```
how to install and configure openldap:
```
https://www.linuxbabe.com/ubuntu/install-configure-openldap-server-ubuntu-16-04
```
update oracle jvm priority:
```
sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-8-oracle/jre/bin/java 1100
```
add apt proxy:
```
/etc/apt/apt.conf.d/proxy.conf <- Acquire::http::Proxy "http://192.168.0.1:2222";
```
