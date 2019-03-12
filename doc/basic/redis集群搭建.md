redis安装
```bash
wget http://download.redis.io/releases/redis-5.0.3.tar.gz
tar xzf redis-5.0.3.tar.gz
cd redis-5.0.3
make

报错
zmalloc.h:50:31: 致命错误 jemalloc/jemalloc.h 没有那个文件或目录

解决
make MALLOC=libc

```

集群最小配置
```
port 7000
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
appendonly yes

```

按照这个操作，配置了三个redis
```
6378
6379
6380
```
启动命令
```
redis/redis-3.2.11/src/redis-server redis-6378.conf &
redis/redis-3.2.11/src/redis-server redis-6379.conf &
redis/redis-3.2.11/src/redis-server redis-6380.conf &

redis/redis-3.2.11/src/redis-cli -c 
-c表示非常基本的集群开关

```
连接后执行
```
127.0.0.1:6379> set key 11
(error) CLUSTERDOWN Hash slot not served
127.0.0.1:6379>
```
执行meet命令后仍然报错
```
CLUSTER MEET 127.0.0.7 6378
CLUSTER MEET 127.0.0.7 6380

127.0.0.1:6379> cluster nodes
a1decde1964bbcb9cc039b7910821be29f1ee5f9 127.0.0.1:6378 master - 0 1551185398617 2 connected
b03059078b0635165dba20c5581af5f1e1d73bb0 127.0.0.1:6380 master - 0 1551185397615 0 connected
42b27977dceef97557989809b2c2c960003c6a34 127.0.0.7:6379 myself,master - 0 0 1 connected
```

重新创建集群
```
redis/redis-3.2.11/src/redis-cli --cluster create 127.0.0.1:6378 127.0.0.1:6379 127.0.0.1:6380 --cluster-replicas 1

redis/redis-3.2.11/src/redis-trib.rb  create --replicas  0  127.0.0.1:6378  127.0.0.1:6379  127.0.0.1:6380
提示没有安装ruby

--cluster-replicas 1意味着我们希望每个创建的主服务器都有一个从服 其他参数是我要用于创建新集群的实例的地址列表
此处我们不需要该项配置
yum install ruby
安装后报下面错误
/usr/share/rubygems/rubygems/core_ext/kernel_require.rb:55:in `require': cannot load such file -- redis (LoadError)
	from /usr/share/rubygems/rubygems/core_ext/kernel_require.rb:55:in `require'
	from ./redis-trib.rb:25:in `<main>'
	

需要安装 gem-redis

gem install redis

```

再次运行创建集群
```
[OK] All 16384 slots covered.
```