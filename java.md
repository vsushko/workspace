install java via webupd8team:
```
https://tecadmin.net/install-oracle-java-8-ubuntu-via-ppa/
```
set java home:
```
/etc/environment/ <- JAVA_HOME=/usr/java/jdk1.8.0_211
                     JRE_HOME=/usr/java/jdk1.8.0_211/jre
```
unix time format:
```
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
```

imports order(https://github.com/twitter/commons/blob/master/src/java/com/twitter/common/styleguide.md):
https://stackoverflow.com/questions/21787128/how-to-unit-test-jackson-jsonserializer-and-jsondeserializer
```
:::java
import java.*
import javax.*

import com.*

import net.*

import org.*

import all other

import static *
```
for bytecode and constant pool inspection of any class file:
```
 javap -c -v ClassName
```
memory analyzer mat:
```
https://www.eclipse.org/mat/
```
prints to the console when a gc takes place:
```
-verbose:gc
```
sets the maximum heap size:
```
-Xmx512m
```
sets the starting heap size:
```
-Xms150m
```
sets the size of the permgen (is used to keep informations for loaded classes and few other advanced features like String Pool):
```
-XX:MaxPermSize=256M
```
sets the size of the young generation:
```
-Xmn256m
```
creates a heap dump file when we get out of memory error ever:
```
-XX:HeapDumpOnOutOfMemory
```
type of collector
* serial - uses single thread
```
-XX:+UserSerialGC
```
* Parallel - when we have multiple threads app for instance
```
-XX:+UserParallelGC
```
* Mostly Concurrent
```
-XX:+UseConcMarkSweepGC or -XX:+UseG1GC
```
to find out which is your default
```
-XX:+PrintCommandLineFlags
```

## spring

https://start.spring.io/

https://www.baeldung.com/spring-profiles
