# Spring Boot

### Containerization
#### build-packs:
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
#### google jib:
```
https://github.com/GoogleContainerTools/jib
```
build image using jib:
```xml
  <plugin>
      <groupId>com.google.cloud.tools</groupId>
      <artifactId>jib-maven-plugin</artifactId>
      <version>3.4.1</version>
      <configuration>
          <to>
              <image>eazybytes/${project.artifactId}:s4</image>
          </to>
      </configuration>
  </plugin>
```
