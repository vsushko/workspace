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
