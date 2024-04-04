pull:
```
docker pull rabbitmq:3.12.4
```

run:
```
docker run --rm -it -p 5672:5672 rabbitmq:3.12.4
```

docs:
```
https://www.rabbitmq.com/docs/download
```

```
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management
```
