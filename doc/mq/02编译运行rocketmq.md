1、编译
```
 > unzip rocketmq-all-4.4.0-source-release.zip
 > cd rocketmq-all-4.4.0/
 > mvn -Prelease-all -DskipTests clean install -U
 > cd distribution/target/apache-rocketmq
```

2、Start Name Server
```
  > nohup sh bin/mqnamesrv &
  > tail -f ~/logs/rocketmqlogs/namesrv.log
   main - The Name Server boot success. serializeType=JSON
```

3、Start Broker
```
> nohup sh bin/mqbroker -n localhost:9876 &
> tail -f ~/logs/rocketmqlogs/broker.log 
boot success. serializeType=JSON and name server is localhost:9876
```


tips：

尽量使用Java8编译和运行，否则他的一大堆jvm启动参数都不支持9及以上版本，比较麻烦

运行测试用例的使用遇到比较奇怪的问题

```
Caused by: io.netty.handler.codec.EncoderException: java.lang.NoSuchMethodError: java.nio.ByteBuffer.flip()Ljava/nio/ByteBuffer
```

产生的原因是因为我用java12build 然后用java8启动，导致了这个错误，后面统一版本后成功运行测试用例。