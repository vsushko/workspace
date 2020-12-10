Generates a RSA private key:
```
openssl req -new -newkey rsa:4096 -days 365 -x509 -subj "/CN=Kafka-Security-CA" -keyout ca-key -out ca-cert -nodes
```
`ca-key` - private key
`ca-cert` - public certificate 

create certificate for kafka broker:
```
keytool -genkey -keystore kafka.server.keystore.jks -validity 365 -storepass $SRVPASS -keypass $SRVPASS -dname "CN=amazonaws.com" -storetype pkcs12 -keyalg RSA
```
check the content:
```
keytool -list -v -keystore kafka.server.keystore.jks
```
create a certification request file, to be signed by the CA:
```
keytool -keystore kafka.server.keystore.jks -certreq -file cert-file -storepass $SRVPASS -keypass $SRVPASS
```
sign the server certificate => output: file "cert-signed":
```
openssl x509 -req -CA ca-cert -CAkey ca-key -in cert-file -out cert-signed -days 365 -CAcreateserial -passin pass:$SRVPASS
```
check certificates:
```
keytool -printcert -v -file cert-signed
keytool -list -v -keystore kafka.server.keystore.jks
```
create a trust store
Trust the CA by creating a truststore and importing the ca-cert:
```
keytool -keystore kafka.server.truststore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt -keyalg RSA
```
Import CA and the signed server certificate into the keystore:
```
keytool -keystore kafka.server.keystore.jks -alias CARoot -import -file ca-cert -storepass $SRVPASS -keypass $SRVPASS -noprompt
keytool -keystore kafka.server.keystore.jks -import -file cert-signed -storepass $SRVPASS -keypass $SRVPASS -noprompt
```
files:<br>
`ca-cert` - server sertificate authority<br>
`ca-key`  - server sertificate authority<br>
`ca-cert.srl` - related to signing certificate - can be deleted<br>
`cert-file`   - signing certificate of our kafka broker certificate - can be deleted<br>
`cert-signed` - signed broker certificate needs to be imported later on kafka server keystore<br>
`kafka.server.keystore.jks` - here we should import cert-signed<br>
`kafka.server.truststore.jks` - this is needed because various componeents of kafka broker itself needs to communicate with each other and therefore kafka serever needs to trust to ca certificate as well<br>

shouldn't be distributed `ca-key` and `kafka.server.keystore.jks` files<br>
`ca-cert` and `cert-signed` could be publically distributed to all clients to be able establissh successful ssl communication

on the client side:
grab CA certificate from remote server and add it to local CLIENT truststore:
```
scp -i ~/kafka-security.pem ubuntu@amazonaws.com:/home/ubuntu/ssl/ca-cert .
keytool -keystore kafka.client.truststore.jks -alias CARoot -import -file ca-cert  -storepass $CLIPASS -keypass $CLIPASS -noprompt

keytool -list -v -keystore kafka.client.truststore.jks
```
create client.properties and configure SSL parameters:
```
security.protocol=SSL
ssl.truststore.location=location.jks
ssl.truststore.password=pwd
```
producer:
```
~/kafka/bin/kafka-console-producer.sh --broker-list amazonaws.com:9093 --topic kafka-security-topic --producer.config ~/ssl/client.properties
~/kafka/bin/kafka-console-producer.sh --broker-list amazonaws.com:9093 --topic kafka-security-topic

```
consumer:
```
~/kafka/bin/kafka-console-consumer.sh --bootstrap-server amazonaws.com:9093 --topic kafka-security-topic --consumer.config ~/ssl/client.properties
```
create a CLIENT certificate:
```
keytool -genkey -keystore kafka.client.keystore.jks -validity 365 -storepass $CLIPASS -keypass $CLIPASS  -dname "CN=mylaptop" -alias my-local-pc -storetype pkcs12
```
check:
```
keytool -list -v -keystore kafka.client.keystore.jks
```
create a certification request file:
```
keytool -keystore kafka.client.keystore.jks -certreq -file client-cert-sign-request -alias my-local-pc -storepass $CLIPASS -keypass $CLIPASS
```
sign the server certificate => output: file "cert-signed":
```
openssl x509 -req -CA ca-cert -CAkey ca-key -in /tmp/client-cert-sign-request -out /tmp/client-cert-signed -days 365 -CAcreateserial -passin pass:$SRVPASS
```
import to keystore on local computer:
```
keytool -keystore kafka.client.keystore.jks -alias CARoot -import -file ca-cert -storepass $CLIPASS -keypass $CLIPASS -noprompt
keytool -keystore kafka.client.keystore.jks -import -file client-cert-signed -alias my-local-pc -storepass $CLIPASS -keypass $CLIPASS -noprompt
```

add changes to enable ssl auth in server.properties
```
ssl.client.auth=required
```
create client-ssl-auth.properties file and configure kafka:
```
security.protocol=SSL
ssl.truststore.location=/home/gerd/ssl/kafka.client.truststore.jks
ssl.truststore.password=clientpass
ssl.keystore.location=/home/gerd/ssl/kafka.client.keystore.jks
ssl.keystore.password=clientpass
ssl.key.password=clientpass
```
