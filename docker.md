Editing files in a docker container:
https://blog.softwaremill.com/editing-files-in-a-docker-container-f36d76b9613c

add user your to docker group:
```
sudo usermod -aG docker your-user
```
run container sh:
```
docker exec -it <container-id> 'sh'
```
display running containers:
```
docker ps
```
restart containers:
```
docker restart <container-name>
```
stop all containers:
```
docker stop $(docker ps -a -q)
```
remove all containers:
```
docker rm $(docker ps -a -q)
```
examine logs container (display the logs and then continue watching -f or --follow):
```
docker logs <container-name>
```
stop container:
```
docker stop <container-name>
```
download, install and start container running application:
```
docker run --detach --name <container-name> <image>
```
creating a PID namespace:
```
docker run -d --name namespaceA <image> /bin/sh -c "sleep 30000"
```
```
docker run -d --name namespaceB <image> /bin/sh -c "nc -l -p 0.0.0.0:80"
```
examine (docker exec command runs additional process in a running container):
```
docker exec namespaceA ps
```
```
docker exec namespaceB ps
```
shows list all processes running on the computer(optionaly create container without their own PID namespace - docker create):
```
docker run --pid host <image> ps
```
rename the container with the docker rename command:
```
docker rename <container-name> <new-container-name>
```
use container-id with stop or exec command:
```
docker exec <container-id> ps
```
```
docker stop <container-id> ps
```
create container without starting it (in a stopped state):
```
docker create <container-name>
```
assing container id to a shell variable and print it:
```
CID=$(docker create <image-name>) | echo $CID
```
write the ID of a new container to a known file:
```
docker create --cidfile /tmp/<file-name>.cid <image-name>
```
get the trancated ID of the last created container (--no-trunc for full container ID):
```
CID=$(docker ps --latest --quiet)
```
See all the containers(including those in the exited state):
```
docker ps -a
```
create new instance using variables(needs to be started in reverse order of their dependency chain):
```
VARIABLE1_CID=$(docker run -d <image>)
VARIABLE2_CID=$(docker run -d <image>)

VARIABLE3_CID=$(docker create --link $VARIABLE1_CID:<name1> --link $VARIABLE1_CID:<name2> <image>

docker start VARIABLE2_CID
docker start VARIABLE1_CID
```
link docker containres
```
docker run -it --link "container1:tag" container2 /bin/bash
```
start container in read-only state:
```
docker run -d --name <name> --read-only <image>
```
start container:
```
docker run --rm --name <container-name> -it <container-name:tag> bash
```
print true if the container named <name> is running and false otherwise:

`docker inspect --format "{{.State.Running}}" <name>`

execute the env command inside the container(the --env flag is used to inject any environment variable):

`docker run -env MY_ENVIRONMENT_VAR="this is a test" <repository>:<tag> env`

inject the value for a variable:

`docker create --env VARIABLE=<variable-value> repository:tag`

building a container that alwas restarts and simply prints the time:

`docker run -d --name backoff-detector --restart always <repository> date`

print processes which are running inside a container:

`docker top <container-name>`

get PIDs list from container's PID namespace:

`docker exec <container-name> ps`

kill program inside the container:

`docker exec <container-name> kill <PID>`

remove container (-f if state is running or use docker kill with docker ps <container-name>):
`docker rm <container-name>`

quick cleanup command:
`docker rm -vf $(docker ps -a -q)`

remove all exited containres:
`dokcer rm $(docker ps --filter status=exited -q)`

download/remove another image in alternative registry (for example quay.io/dockerinaction/ch3_hello_registry:latest):

`docker pull [REGISTRYHOST/][USERNAME/]NAME[:TAG]`
`docker rmi [REGISTRYHOST/][USERNAME/]NAME[:TAG]`

remove image:

`docker rmi <repository>`

save image to file (with preparation):

`docker pull <repository:tag>`
`docker save -o myfile.tar <repository:tag>`

save image to tar.gz:
```
docker save <container-name:tag> | gzip -c > <container-name:tag>.tar.gz
```

load image from a file:

`docker load -i myfile.tar`

build and install a Docker image using the Dockerfile (-t provide place to install image):

`docker build -t [REGISTRYHOST/][USERNAME/]NAME[:TAG] <docker-file-name>`
`docker build -t <container-name> .`

show a list which includes every installed intermediate image or layer:

`docker images -a`

start an Apache HTTP server where your new directory is bind mounted to the server's document root:

`docker run -d --name bmweb -v ~/example-docs:/usr/local/apache2/htdocs -p 80:80 httpd:latest`

create bind volume:

create managed volume:

create host-dependent sharing:

`mkdir ~/foler-name`

bind mount the location into a writing container:

`docker run --name <container-name> -d -v ~/folder-name:/data <repo>`

bind mount the same location into a container for reading (--rm is not required):

`docker run --rm -v ~/folder-name:/reader-data <repo> head /reader-data/file-name`

remove all stopped containers and their volumes:

`docker rm -v $(docker ps -ap)`

get a list of available users in a image with the following command:

`docker run --rm <repo> awk -F: '$0=$1' /etc/passwd`

display the history of images:

`docker history <repo>`

create image from DockerFile:

DockerFile:

```FROM ubuntu:latest
MAINTAINER "my@mail.com"
RUN apt-get update && apt-get install -y git
ENTRYPOINT ["git"]
```
`docker build --tag ubuntu-git:auto -f ./DockerFile .`

test:

`docker run --rm ubuntu-git:auto version`

create simple image (HelloWorld.df):

```FROM busybox:latest
CMD echo Hello World
```

build new image:

`docker build -t vsushko/hello-dockerfile -f HelloWorld.df .`

get authenticated:

`docker login`

push repository to the hosted registry:

`docker push vsushko/hello-dockerfile`

verify by searching for username and repository:

`docker search vsushko/hell-dockerfile`

work with github (with existence repository in github named hello-docker):

create HelloWorld.df: 

```FROM busybox:latest
CMD echo Hello World
```

create a local git repository, add the docker file, commit changes and push changed to github repository:

```git init
git config --global user.email "gmail.com"
git config --global user.name "Your Name"
git remote add origin username https://github.com/<your username>/hello-docker.git
```

bind repo at docker hub to github repo and then complete with these commands:

```git add Dockerfile
git commit -m "first commit"
git push -u origin master
```


check the work:

`docker search vsushko/hell-dockerfile`

verify image is discoverable with label filter (-f means that filter output based on conditions provided):

`docker images -f "label=dia_excercise=ch9_registry_bound"`
docker ps filering:
`docker ps --filter status=exited`
`docker ps --filter status=exited -q`

increase ram for docker machine:
```
docker-machine stop
VBoxManage modifyvm default --cpus 2
VBoxManage modifyvm default --memory 4096
docker-machine start
```
for more information: https://stackoverflow.com/questions/32834082/how-to-increase-docker-machine-memory-mac 

Get container ip:
```
-f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' <container-name-or-id>
```

## Troubleshooting
List directory /var/lib/apt/lists/partial is missing. - Acquire (13: Permission denied)
```
FROM websphere-liberty:webProfile7
ARG SSL_KEYSTORE_PASSWORD
USER root
RUN apt-get update && 
apt-get install -y 
curl
USER 1001
```
create a swarm (https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/):
```
docker swarm init --advertise-addr <MANAGER-IP>
```
leave docker swarm:
`docker swarm leave`
add more replicas to docker swarm container:
`docker service update --replicas 6 <container-name>`
add or updated port:
`docker service update --publish-add 80:80 <container-name>`
change node role:
`docker service update --constraint-add "node.role==manager" <node-name>`

show .NetworkSettings.Networks:
```
docker inspect mongo-container -f "{{ json .NetworkSettings.Networks }}"
```
run created docker container:
```
docker run -d -p port:port --name <service-name> --network <network-name> -e "SPRING_PROFILES_ACTIVE=docker" --restart always <container-name>
```

tag created container:
```
docker tag user-oauth2 <dockerhubusername>/<dockerhubrepo>:<container-name>
```

push image to docker hub:
```
docker push <dockerhubusername>/<dockerhubrepo>:<container-name>
```
remove all unused networks:
```
docker network prune
```
clean everything:
```
docker system prune -f
```
connect container to network:
```
docker network connect <network> <container>
```
inspect:
```
docker network inspect <network>
```
deploy stack:
```
docker stack deploy --compose-file docker-compose-stack.yml <stackname>
```
list services:
```
docker service ls
```
remove stack:
```
docker stack rm <stackname>
```
scale service via docker compose:
```
docker-compose up -d --scale <container-name>=2
```
restart all unhealthy containers:
```
docker restart $(docker ps -f health=unhealthy -q)
```
autoheal:
`https://hub.docker.com/r/willfarrell/autoheal`

Receive real time events from containers:
```
docker events

```
Docker sercrets & Configs:
Secrets and configs are mounted files inside a container of a swarm service atruntime
* location
* * secrets can only be mounted to /run/secrets/<secret-name>
* * configs default to /<config-name>
* encryption
* * secrets are encryypted, and then decrypted within the container.
`docker swarm ca`
`docker swarm ca | docker secret create swarm_ca -`
`docker swarm ca > swarm_ca.crt`
`docker config create swarm_ca ./swarm_ca.crt`
`docker secret ls`
`docker config ls`
`cat /run/secrets/swarm.crt`

in docker .yml file: 
```yml
configs:
 - source: swarm.crt
   target: /run/secret.crt
secrets:
 - source: swarm.crt
   target: /run/secret.crt
#...
configs:
  swarm.crt:
    file: ./swarm_ca.crt
secrets:
  swarm.crt:
    file: ./swarm_ca.crt
```
deploy:
`docker stack deploy -c docker-compose.yml conf_secret`

