#### zookeeper生成自增主键

#### 1、zk节点介绍
zk有四种类型的节点
PERSISTENT
```
The znode will not be automatically deleted upon client's disconnect.
持久节点，不主动删除就一直存在
```
PERSISTENT_SEQUENTIAL
```
he znode will not be automatically deleted upon client's disconnect,
and its name will be appended with a monotonically increasing number.

持久顺序节点，不会自动删除，当再次调用create时会在名字后面追加单调自增的数字
例如调用
client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/seqtest")
```
EPHEMERAL
```
The znode will be deleted upon the client's disconnect.
临时节点，客户端断开连接则自动删除

```
EPHEMERAL_SEQUENTIAL
```
The znode will be deleted upon the client's disconnect, and its name
will be appended with a monotonically increasing number.
```

#### 2、启动zk
使用docker运行zookeeper
```shell
service docker start

docker pull zookeeper

docker run --name zookeeper -tld -p 2181:2181 zookeeper
```
