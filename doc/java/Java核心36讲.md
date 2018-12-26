- 1、第1讲 |谈谈你对Java平台的理解？
- 2、第2讲 |Exception和Error有什么区别？
- 3、第3讲 |谈谈final、finally、 finalize有什么不同？
- 4、第4讲 |强引用、软引用、弱引用、幻象引用有什么区别？
- 5、第5讲 |String、StringBuffer、StringBuilder有什么区别？
- 6、第6讲 |动态代理是基于什么原理？
- 7、第7讲 |int和Integer有什么区别？
- 8、第8讲 | 对比Vector、ArrayList、LinkedList有何区别？
- 9、第9讲 | 对比Hashtable、HashMap、TreeMap有什么不同？
- 10、第10讲 | 如何保证集合是线程安全的? ConcurrentHashMap如何实现高效地线程安全？
- 11、第11讲 | Java提供了哪些IO方式？ NIO如何实现多路复用？
- 12、第12讲 | Java有几种文件拷贝方式？哪一种最高效？
- 13、第13讲 | 谈谈接口和抽象类有什么区别？第12讲 | 第13讲 | 谈谈接口和抽象类有什么区别？
- 14、第14讲 | 谈谈你知道的设计模式？
- 15、第15讲 | synchronized和ReentrantLock有什么区别呢？
- 16、第16讲 | synchronized底层如何实现？什么是锁的升级、降级？
- 17、第17讲 | 一个线程两次调用start()方法会出现什么情况？
- 18、第18讲 | 什么情况下Java程序会产生死锁？如何定位、修复？
- 19、第19讲 | Java并发包提供了哪些并发工具类？
- 20、第20讲 | 并发包中的ConcurrentLinkedQueue和LinkedBlockingQueue有什么区别？
- 21、第21讲 | Java并发类库提供的线程池有哪几种？ 分别有什么特点？
- 22、第22讲 | AtomicInteger底层实现原理是什么？如何在自己的产品代码中应用CAS操作？
- 23、第23讲 | 请介绍类加载过程，什么是双亲委派模型？
- 24、第24讲 | 有哪些方法可以在运行时动态生成一个Java类？
- 25、第25讲 | 谈谈JVM内存区域的划分，哪些区域可能发生OutOfMemoryError?
- 26、第26讲 | 如何监控和诊断JVM堆内和堆外内存使用？
- 27、第27讲 | Java常见的垃圾收集器有哪些？
- 28、第28讲 | 谈谈你的GC调优思路?
- 29、第29讲 | Java内存模型中的happen-before是什么？
- 30、第30讲 | Java程序运行在Docker等容器环境有哪些新问题？
- 31、第31讲 | 你了解Java应用开发中的注入攻击吗？
- 32、第32讲 | 如何写出安全的Java代码？
- 33、第33讲 | 后台服务出现明显“变慢”，谈谈你的诊断思路？
- 34、第34讲 | 有人说“Lambda能让Java程序慢30倍”，你怎么看？
- 35、第35讲 | JVM优化Java代码时都做了什么？
- 36、第36讲 | 谈谈MySQL支持的事务隔离级别，以及悲观锁和乐观锁的原理和应用场景？
- 37、第37讲 | 谈谈Spring Bean的生命周期和作用域？
- 38、第38讲 | 对比Java标准NIO类库，你知道Netty是如何实现更高性能的吗？
- 39、第39讲 | 谈谈常用的分布式ID的设计方案？Snowflake是否受冬令时切换影响？










<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>


```markdown

springBoot介绍：
1、遵循“习惯优于配置”的原则，使用Spring Boot只需要很少的配置，大部分的时候我们直接使用默认的配置即可；
2、提供starter简化Manen配置
3、支持运行期内嵌容器，如 Tomcat、Jetty

多线程、异步编程
接口优化中用到了CompletableFuture.supplyAsync方法，然后使用future.get获取返回值


dubbo服务中使用zk作为其注册中心。
zk使用ZAB协议来实现分布式数据的一致性


dubbo:
分为服务提供者、消费者、注册中心、
服务接口尽可能大粒度，每个服务方法应代表一个功能，而不是某功能的一个步骤，
否则将面临分布式事务问题，Dubbo 暂未提供分布式事务支持。

mybatis:


nginx:


tomcat:


synchronized和ReentrantLock有什么区别呢？
原子性



2、兴趣是什么，优势是什么
3、jvm、jre、jdk三者之间的关系
4、Dubbo的底层原理、Zookeeper是神么
5、ConcurrentHashMap机制、TreeMap、Volatile关键字
6、快速排序、广度优先搜索
7、缓存的雪崩以及穿透的理解
8、synchronized和lock的区别
9、开发一个大型网站你会考虑哪些问题

