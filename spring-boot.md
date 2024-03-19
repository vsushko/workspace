

build-packs:
```
https://buildpacks.io/
```
build image using buildpacks:
```xml
  <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
          <image>
              <name>eazybytes/${project.artifactId}:s4</name>
          </image>
      </configuration>
  </plugin>
```
```
mvn spring-boot:build-image 
```
