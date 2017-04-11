

run container sh:

`docker exec -it <container-id> 'sh'`

display running containers:

`docker ps`

restart containers:

`docker restart <container-name>`

stop all containers:

`docker stop $(docker ps -a -q)`

remove all containers:

`docker rm $(docker ps -a -q)`

examine logs container (display the logs and then continue watching -f or --follow):

`docker logs <container-name>`

stop container:

`docker stop <container-name>`

download, install and start container running application:

`docker run --detach --name <container-name> <image>`

creating a PID namespace:

`docker run -d --name namespaceA <image> /bin/sh -c "sleep 30000"`

`docker run -d --name namespaceB <image> /bin/sh -c "nc -l -p 0.0.0.0:80"`

examine (docker exec command runs additional process in a running container):

`docker exec namespaceA ps`

`docker exec namespaceB ps`

shows list all processes running on the computer(optionaly create container without their own PID namespace - docker create):

`docker run --pid host <image> ps`

rename the container with the docker rename command:

`docker rename <container-name> <new-container-name>`

use container-id with stop or exec command:

`docker exec <container-id> ps`

`docker stop <container-id> ps`

create container without starting it (in a stopped state):

`docker create <container-name>`

assing container id to a shell variable and print it:

`CID=$(docker create <image-name>) | echo $CID`

write the ID of a new container to a known file:

`docker create --cidfile /tmp/<file-name>.cid <image-name>`

get the trancated ID of the last created container (--no-trunc for full container ID):

`CID=$(docker ps --latest --quiet)`

See all the containers(including those in the exited state):

`docker ps -a`

create new instance using variables(needs to be started in reverse order of their dependency chain):

```
VARIABLE1_CID=$(docker run -d <image>)
VARIABLE2_CID=$(docker run -d <image>)

VARIABLE3_CID=$(docker create --link $VARIABLE1_CID:<name1> --link $VARIABLE1_CID:<name2> <image>

docker start VARIABLE2_CID
docker start VARIABLE1_CID
```
start container in read-only state:

`docker run -d --name <name> --read-only <image>`

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

download/remove another image in alternative registry (for example quay.io/dockerinaction/ch3_hello_registry:latest):

`docker pull [REGISTRYHOST/][USERNAME/]NAME[:TAG]`
`docker rmi [REGISTRYHOST/][USERNAME/]NAME[:TAG]`

remove image:

`docker rmi <repository>`

save image to file (with preparation):

`docker pull <repository:tag>`
`docker save -o myfile.tar <repository:tag>`

load image from a file:

`docker load -i myfile.tar`

build and install a Docker image using the Dockerfile (-t provide place to install image):

`docker build -t [REGISTRYHOST/][USERNAME/]NAME[:TAG] <docker-file-name>`

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


