1、使用docker运行zookeeper
```shell
service docker start

docker pull zookeeper

docker run --name zookeeper -tld -p 2181:2181 zookeeper
```