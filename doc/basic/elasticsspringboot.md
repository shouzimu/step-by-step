####ElasticsSearch5.2.2和SpringBoot2.x整合遇到的坑
项目一开始是elasticsSearch5.2.2+springboot1.5.8,稳定运行了半年
最近准备升级到springboot2.1

原始配置
pom依赖
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>1.5.8.RELEASE</version>
    <relativePath/>
</parent>
<dependency>
    <groupId>org.elasticsearch</groupId>
    <artifactId>elasticsearch</artifactId>
    <version>5.2.2</version>
</dependency>
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>transport</artifactId>
    <version>5.2.2</version>
</dependency>
<dependency>
<groupId>org.elasticsearch.client</groupId>
    <artifactId>x-pack-transport</artifactId>
    <version>5.2.2</version>
</dependency>
```
client配置
```java

@Component("transportClient")
@Configuration
@ComponentScan("com.dh.service")
@ConfigurationProperties(prefix="elasticsearch.es")
@Data
public class ElasticSearchService implements InitializingBean ,FactoryBean<TransportClient> {
    private String clusterName;
    private boolean clientTransportSniff;
    private String esHost;
    private int esPort;
    private TransportClient client;
    @Override
    public void afterPropertiesSet() throws Exception {
        Settings settings=Settings.builder().put("cluster.name",clusterName).put("xpack.security.user","elastic:Zanchina2016").put("client.transport.sniff", false).build();
        client=new PreBuiltXPackTransportClient(settings);
        TransportAddress transportAddress=new InetSocketTransportAddress(InetAddress.getByName(esHost),esPort);
        client.addTransportAddress(transportAddress);
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

```

直接升级到2.0报如下错误
xml
```
<parent>
    <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
    <relativePath/>
</parent>
```
```
Caused by: java.lang.IllegalArgumentException: Cannot register setting [http.netty.max_composite_buffer_components] twice

```

执行mvn依赖命令查看
```
mvn dependency:tree
```
发现依赖中有这么一句
```
[INFO] |  +- org.elasticsearch.client:transport:jar:5.2.2:compile
[INFO] |  |  +- org.elasticsearch.plugin:transport-netty3-client:jar:5.2.2:compile
[INFO] |  |  |  \- io.netty:netty:jar:3.10.6.Final:compile
[INFO] |  |  +- org.elasticsearch.plugin:transport-netty4-client:jar:6.4.3:compile


其余的elasticsearch相关的版本都是5.2.2，但是transport-netty4-client:jar:6.4.3这行依赖6.4.3
所以问题出在这儿

但是为什么仅升级了springboot就冲突了呢
```
参考资料[记一次springboot maven依赖问题的解决之路](https://my.oschina.net/u/3779759/blog/2254667)

```
1、maven包依赖不正确，肯定是别的地方覆盖了，依赖冲突时，相对于本pom深度较浅的优先级高
2、相同深度下，父级依赖<dependencyManagement>优先级比传递依赖优先级高
3、springboot默认有许多依赖在spring-boot-dependencies中，如果仅想覆盖版本号，则在<properties>中覆盖对应属性即可。
```
所以在pom.xml中增加一行配置解决问题
```
 <elasticsearch.version>5.2.2</elasticsearch.version>
```
之前的配置实际也是有问题的，
x-pack-transport依赖了clinet和es，没必要重复配置，
依赖是可以传递的，进一步修改如下
```
<dependency>
    <groupId>org.elasticsearch.client</groupId>
    <artifactId>x-pack-transport</artifactId>
    <version>${elasticsearch.version}</version>
</dependency>
```
再次启动成功

总结
```
对maven依赖传递不熟悉，盲目的在搜索引擎上搜索
对于springboot不熟悉，不知道默认依赖文件的存在

后续遇到类似升级问题，优先查看mvn依赖，排查版本是否有问题
```