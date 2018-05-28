generate quickstart:
```
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.3
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
