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
#### VM Options
```
-Dspring.profiles.active=prod -Dbuild.version=1.1
```
#### Environment version
```
SPRING_PROFILES_ACTIVE=prod;BUILD_VERSION=1.0.0;
```
#### Actuator
```yml
management:
  endpoints:
    web:
      exposure:
        include: "*"
```
```
http://localhost:8080/actuator
```
Refresh (POST):
```
http://localhost:8080/actuator/refresh
```
```
[
  "config.client.version",
  "accounts.message"
]
```
#### Web hook
```
https://console.hookdeck.com/
```
```
brew install hookdeck/hookdeck/hookdeck
hookdeck login --cli-key 4epjt46jdobco7bay8a03jk4fzu555p1l6jngsiw8wll2ruxwa
hookdeck listen [port] Source
hookdeck logout
```
Spring cloud config:
```
https://docs.spring.io/spring-cloud-config/docs/current/reference/html/#_spring_cloud_config_client
```
