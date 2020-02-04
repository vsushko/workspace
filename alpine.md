
install sudo :))
```
apk add sudo
```
https://pastebin.com/r0iXp4Zy

run image:
```
docker run -i -t alpine /bin/bash
```
Best Practices for Non-root User
```
addgroup -g $HOST_USER_GID -s myuser
adduser -u $HOST_USER_UID -S myuser -G myuser
```
