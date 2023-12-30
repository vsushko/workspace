Installation on macOS Platform:
```
https://www.graalvm.org/latest/docs/getting-started/macos/
```
Move the downloaded package to its proper location:
```
sudo mv graalvm-jdk-<version>_macos-<architecture> /Library/Java/JavaVirtualMachines
```
To verify if the move is successful and to get a list of all installed JDKs, run:
```
/usr/libexec/java_home -V
```
Set the JAVA_HOME environment variable to resolve to the GraalVM installation directory:
```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/<graalvm>/Contents/Home
```
Set the value of the PATH environment variable to the GraalVM bin directory:
```
 export PATH=/Library/Java/JavaVirtualMachines/<graalvm>/Contents/Home/bin:$PATH
```
To see the complete list of JVMs available to the java_home command:
```
/usr/libexec/java_home -v21 -V
```
“graalvm” is damaged and can’t be opened:
```
xattr -d com.apple.quarantine <PATH TO GRAALVM INSTALLATION>
```
native-image version:
```
native-image --version
```
to open hex editor:
```
xxd filename | less
```
Setting up Ghidra on macOS:
```
https://github.com/NationalSecurityAgency/ghidra/releases
https://medium.com/the-bkpt/setting-up-ghidra-on-macos-19b7b0becf7c
```
compile code with javac:
```
javac Main.java
```
```
javap -p -c Main.class
```
instructions:
```
https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-2.html#jvms-2.11.1
```
run native-image to compile code to native code:
```
native-image -O0 -g Main
```
run:
```
./main
```
Meaure:
```
time java Main
time ./main
```
run GraalVM Native-Image Maven Plugin:
```
mvn clean install -Pnative
```
GraalVM Native-Image Maven Plugin Options:
```
GraalVM Native-Image Maven Plugin supports the following options
- - mainClass
- - Searched automatically based on the following plugin’s configurations
- - - - maven-shade-plugin mainClass
- - - - maven-assembly-plugin mainClass
- - - - maven-jar-plugin mainClass
- - - Needs to be set manually if compilation fails with “no main manifest attribute, in target/<name>.jar” error
- - imageName - set a custom filename for the native image; when not specified, the artifact ID of the project
will be used by default
- - buildArgs - additional arguments to the native image builder
- - skipNativeBuild – whether to skip a generation of the native image
- - Agent – configuration of the native agent
- - Options can be configured via the “native-image.properties” file as well
```
to see which native-image.properties are applied:
```
native-image --verbose -jar build/myjar.java
```
Native Image Build Configuration Options:
- Args – specify custom command-line options to the native-image command
- JavaArgs - custom options to the JVM that runs the native image builder
- ImageName - specify a user-defined name for the image; if not specified will be automatically generated based on jar name, main class name, or artifactId (when used from Maven Plugin)
- -H:NumberOfThreads – by default, the number of threads will be set to the number of available processors; the default upper limit is 32 threads; this option can be used to specify the customer number of used threads
- - -H:NumberOfThreads = 4
- -J + <jvm option for memory> - Allows to configure non-default memory options for native image build
that runs on the Java HotSpot VM, for example:
- - Args=-J-Xms2G –J-Xmx4G
 
create native image with custom properties
```
native-image --verbose -jar target/myjar.jar
```
- GraalVM Native Image allows you to choose between build-time and run-time initialization
- You have a choice to decide when static initializers and static field initialization should be executed
- - Build Time:
  - - faster startup time
  - - all the static state information from initialized classes is stored in the image
  - Run Time:
  - - slower startup
  - - more compatible with HotSpot VM
- By default, your application code classes are initialized at run time; however, most JDK classes are initialized at image build time
- Behavior can be controlled using the following options to the “native-image” command:
- - --initialize-at-build-time=<comma-separated list of packages and classes>
- - --initialize-at-run-time=<comma-separated list of packages and classes>
- To track which classes were initialized and why use “-H:+PrintClassInitialization” option
```
mvn clean install -Pnative-initialize-at-build-time
```
Native Image Tracing Agent.
Native Image Compilation not handle:
- Java Native Interface (JNI)
- Java Reflection
- Dyynamic Proxy Objects (java.lang.reflect.Proxy)
- Classpath Resources (Class.getResource)

Tracing Agent is executed in the following way:
```
JAVA_HOME/bin/java -agentlib:native-image-agent=config-output-dir=/path/to/config-dir/ ...
```
In some cases, it might be required to run the tracing agent multiple times to trace all execution paths. In that case, it is possible to use the “config-merge-dir” option to append to the previously generated configuration:
```
JAVA_HOME/bin/java -agentlib:native-image-agent=config-merge-dir=/path/to/config-dir/ ...
```
By default, configuration files are written after the JVM process terminates. It is possible to use the following flags to write configuration files periodically:
- config-write-period-secs - executes write every number of seconds
- config-write-initial-delay-secs - number of seconds before the first write, enabled only if config-write-period-secs is greater
than zero.
```
JAVA_HOME/bin/java \ -agentlib:native-image-agent=config-output-dir=/path/to/config-dir/,config-write-period-secs=300,config-write-initial-delay-secs=5 \ ...
```
- Files generated by Tracing Agent can be supplied to “native-image” by placing them in “META-INF/native-image/”
- This directory and its subdirectories are searched for the following files:
- - jni-config.json
- - proxy-config.json
- - reflect-config.json
- - resource-config.json
- - serialization-config.json
Execute tracing agent to generate config JSON files for dynamic feature:
```
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar target/myjar.jar
```
```
https://www.graalvm.org/22.0/reference-manual/native-image/Agent/
```
