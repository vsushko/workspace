install sbt:
```
wget https://github.com/sbt/sbt/releases/download/v1.0.0/sbt-1.0.0.tgz 
sudo tar xzvf sbt-1.0.0.tgz -C /usr/share/
sudo update-alternatives --install /usr/bin/scala scala /usr/share/scala/bin/scala 9999
```

## ~/.sbt/repositories:
### sbt.override.build.repos 
This setting is used to specify that all sbt project added resolvers should be ignored in favor of those configured in the repositories configuration. Using this with a properly configured ~/.sbt/repositories file leads to only your proxy repository used for builds.
It is specified like so:
```
-Dsbt.override.build.repos=true
```
The value defaults to false and must be explicitly enabled.
### sbt.repository.config 
If you are unable to create a ~/.sbt/repositories file, due to user permission errors or for convenience of developers, you can modify the sbt start script directly with the following:
```
-Dsbt.repository.config=<path-to-your-repo-file>
```
This is only necessary if users do not already have their own default repository file.

## credentials
file:
```
realm=My Nexus Repository Manager
host=my.artifact.repo.net
user=admin
password=admin123
```
The second way is to specify them inline:
```
credentials += Credentials("Some Nexus Repository Manager", "my.artifact.repo.net", "admin", "admin123")
```

java.util.concurrent.ExecutionException: java.lang.OutOfMemoryError: GC overhead limited exceeded:
```
sbt -mem 2048 test
```
Other options:
For Mac & Linux user:
if we need to execute this a lot. We can update the .bash_profile file and add below command:
```
export SBT_OPTS="-Xmx2G"
```
How run sbt assembly command without tests from command line?
```
sbt 'set test in assembly := {}' clean assembly
```
