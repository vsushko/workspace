

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

examine:

`docker exec namespaceA ps`

`docker exec namespaceB ps`

