# Spring Boot

### Containerization
#### build-packs:
```
https://buildpacks.io/features
```
build image using buildpacks:
```xml
  <plugin>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
      <configuration>
          <image>
              <name>vsushkodev/${project.artifactId}:s4</name>
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
          <from>
              <image>openjdk:21-jdk</image>
          </from>
          <to>
              <image>vsushkodev/${project.artifactId}:s4</image>
          </to>
      </configuration>
  </plugin>
```
```
mvn compile jib:dockerBuild
```
#### Properties
- Properties present inside files like application properties
- OS Environmental variables
- Java System propertiees (System.getProperties())
- JNDI attributes from java:comp/env
- ServletContext init parameters
- ServletConfig init parameters
- Command line arguments

#### Program Arguments
```
--spring.profiles.active=dev --build.version=1.1.0
```
