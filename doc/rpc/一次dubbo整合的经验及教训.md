#### 项目背景

因为总总历史原因，有两个项目中存在同样的业务逻辑

项目1：springboot1.5.8.4.3.3.RELEASE
项目2：springMVC 4.3.3.RELEASE<br>

导致同样的代码需要在两个项目同时开发和测试，带来很大的浪费。<br>
所以考虑引入dubbo项目1作为provider，项目2作为consumer

#### 项目1配置<br>
pom.xml
```xml
<dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo</artifactId>
      <version>2.7.0</version>
    </dependency>

    <dependency>
      <groupId>org.apache.curator</groupId>
      <artifactId>curator-framework</artifactId>
      <version>2.11.1</version>
    </dependency>

    <dependency>
      <groupId>org.apache.curator</groupId>
      <artifactId>curator-recipes</artifactId>
      <version>2.11.1</version>
    </dependency>
```
duubo的配置，使用api实现
```java

@Configuration
public class DubboConfig {


    @Autowired
    private XXXservice XXXservice;

    @Bean
    public ServiceConfig initService() {

        // 当前应用配置
        ApplicationConfig application = new ApplicationConfig();
        application.setName("xxx");

        // 连接注册中心配置
        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setName("zookeeper://127.0.0.1:2181");
        protocol.setPort(20880);
        protocol.setThreads(10);

        // 注意：ServiceConfig为重对象，内部封装了与注册中心的连接，以及开启服务端口

        // 服务提供者暴露服务配置
        ServiceConfig<XXXservice> service = new ServiceConfig<>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(application);
        service.setRegistry(registry); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(XXXservice.class);
        service.setRef(XXXservice);
        service.setVersion("1.0.0");

        // 暴露及注册服务
        service.export();
        return service;
    }
```


#### 项目二配置
pom.xml 这个地方需要和项目一致，否则可能会出现zk兼容性问题
```xml
<dependency>
      <groupId>org.apache.dubbo</groupId>
      <artifactId>dubbo</artifactId>
      <version>2.7.0</version>
    </dependency>

    <dependency>
      <groupId>org.apache.curator</groupId>
      <artifactId>curator-framework</artifactId>
      <version>2.11.1</version>
    </dependency>

    <dependency>
      <groupId>org.apache.curator</groupId>
      <artifactId>curator-recipes</artifactId>
      <version>2.11.1</version>
    </dependency>
```

dubbo配置 applicationContext-dubbo.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:dubbo="http://dubbo.apache.org/schema/dubbo"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd http://dubbo.apache.org/schema/dubbo http://dubbo.apache.org/schema/dubbo/dubbo.xsd">

    <dubbo:application name="dubbo-consumer" />
    <!-- 使用zookeeper注册中心暴露服务地址 -->
    <dubbo:registry protocol="zookeeper" address="127.0.0.1:2181" />
    <dubbo:protocol name="dubbo" port="20880" />

    <!-- 要引用的服务 -->
    <dubbo:reference interface="com.xxx.xxx.xxx.service" id="xxxservice"
      check="false" version="1.0.0" timeout="3000"/>

</beans>
```

按照上面步骤调用就可以了

#### 过程中遇到的问题
```
1、调用报错
一开始curator配置的版本是4.2.0

consumer调用时总是报错
Dubbo RemotingException: message can not send, because channel is closed

排查过程：
将注册中心换成redis后就正常调用

后面查看maven依赖树，发现项目一中curator还同时存在2.11.0
4.2.0依赖的zookeeper是 3.5.4
2.11.0依赖的是3.4.8
项目2不存在冲突问题，所以最后导致provider和consumer的zookeeper冲突

使用zkCli连接后 get ls /dubbo/com.xxx.xxx.xxx.service/providers发现注册成功
就是调用有问题，所以后面统一将curator的版本替换为2.11.0，再次测试就通过了

2、provider方法加断点后一次调用执行多次
结果：没有解决

```