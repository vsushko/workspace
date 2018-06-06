how to add existing in .jks certificate to java trusted store:

print existing certificates:
```
keytool -list -keystore my_cert.jks
```
export existing certificate from .jks:
```
keytool -exportcert -alias cert-to-der -file cert-to-der.der -keystore my_cert.jks
```
to check successful export:
```
ls
```
more details with keytool:
```
keytool -v -printcert -file cert-to-der.der
```
import to keystore:
```
keytool -importcert -alias cert-to-der -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -file cert-to-der.der
```
check for existing in java keystore
```
keytool -keystore "$JAVA_HOME/jre/lib/security/cacerts" -storepass changeit -list | grep cert-to-der
```
delete an existing certificate:
```
keytool -delete -alias cert-to-der -keystore $JAVA_HOME/jre/lib/security/cacerts -storepass changeit -file cas-loc.der
```
check ldap status:
```
systemctl status slapd
```
