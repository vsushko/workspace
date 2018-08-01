generate quickstart:
```
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.3
```
calls clean install with force update of snapshot dependencies flag:
```
mvn clean install -U
```
add dependency to local repo:
```
mvn dependency:get -U -DrepoUrl=https://comany.repo.com/artifactory/libs-snapshot -Dartifact=ru.comany:some-library:LATEST:jar
```
Plugins:<br>
ProGuard Reference Card Usage:
```
http://www.dre.vanderbilt.edu/~schmidt/android/android-4.0/external/proguard/docs/manual/refcard.html
```
tests only particularMethod:
```
mvn clean test -Dtest=your.package.TestClassName#particularMethod
```
with specifying file, groupId, artifactId, version and packaging:
```
mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>
```
via pom:
```
mvn install:install-file -Dfile=<path-to-file> -DpomFile=<path-to-pomfile>
```
using jar only:
```
mvn install:install-file -Dfile=<path-to-file>
```
