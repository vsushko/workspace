

run container sh:

`docker exec -it <container-id> 'sh'`

display running containers:

`docker ps`

restart containers:

`docker restart <container-name>`

examine logs container (display the logs and then continue watching -f or --follow):

`docker logs <container-name>`

stop container:

`docker stop <container-name>`

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

```sh
VARIABLE1_CID=$(docker run -d <image>)
VARIABLE2_CID=$(docker create <image>)

VARIABLE3_CID=$(docker create --link $VARIABLE1_CID:<name1> --link $VARIABLE1_CID:<name2> <image>

docker start VARIABLE2_CID
docker start VARIABLE1_CID
```

