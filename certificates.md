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
