## idea远程调试

此处以jdbcT4-2.2.0作为测试例子

#### 一、原理

远程调试的原理就是两个VM之间通过debug协议进行通信，两者之间通过socket进行通信。

#### 二、实现

在启动JVM时加上参数

如：

```
-agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=n
```

参数说明:

```
- transport :调试程序和VM之间的通信方式， dt_socket 表示用套接字传输
- server=y : 表示是监听其他debugclient端的请求
- suspend : 表示是否在调试客户端建立连接之后启动 VM。
+ 如果为y，那么当前的VM就是suspend直到有debug client连接进来才开始执行程序
+ 如果为n，那么当前的VM就会直接执行，不会等待debug client连接进来
- address=8001 : 表示端口是5005
```



#### 三、运行环境

环境如下：

| 软件       | 位置           | 版本                                 |
| ---------- | -------------- | ------------------------------------ |
| 虚拟机ip   | 192.168.31.130 |                                      |
| 操作系统   | 虚拟机         | CentOS Linux release 7.7.1908 (Core) |
| trafodion  | 虚拟机k8s中    | 2.7.0                                |
| 服务端java | 虚拟机         | openjdk version "1.8.0_242"          |
| idea       | 主机windows    | 2020.1                               |
| java       | 主机windows    | java version "1.8.0_251"             |
| hbase      | 虚拟机         | 2.2.5                                |
| hadoop     | 虚拟机         | 2.10.0                               |



#### 四、服务端准备工作

测试sql:

```sql
create table test001(id int ,primary key(id));
insert into test001(id) values(1),(2),(3),(4),(5),(6),(7);
```

远程运行的测试代码如下 `Test.java`

```java
import java.sql.*;

public class Test {
    protected static final String url = "jdbc:t4jdbc://10.103.0.125:23400/:";
    protected static final String driverClass = "org.trafodion.jdbc.t4.T4Driver";
    protected static final String userName = "DB__ROOT";
    protected static final String pwd = "traf123";
    protected static Connection conn;

    public static void main(String[] args) throws Exception {
        System.out.println("enter word continue..");
        System.in.read();//阻塞远程程序，client端有时间连接
        System.out.println("exe..");
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, pwd);
        String sql = "select * from test001 order by id desc";
        String countSql = "select count(*) from test001 order by id desc";
        Statement ps = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        Statement psCount = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery(sql);
        ResultSet countRs = psCount.executeQuery(countSql);
        if(countRs.next()){
            System.out.println("count:" + countRs.getInt(1));
        }else{
            System.out.println("count null");
        }
        while (rs.next()) {
            System.out.println(rs.getInt(1));
        }
        ps.close();
        conn.close();
    }
}
```



程序运行脚本如下：

```shell
//将驱动包添加到classpath中
export CLASSPATH=/root/test/jdbcT4-2.2.0.jar:.
//编译
javac Test.java
//运行
java -agentlib:jdwp=transport=dt_socket,address=8001,server=y,suspend=n Test

会看到如下输出
Listening for transport dt_socket at address: 8001
enter word continue..
```

运行Test后，会等待输入才会继续运行，下面处理idea中相关的工作



#### 五、idea中的操作

##### 1、项目准备

此处以maven项目为例，只是为了运行的时候代码跟远程的能关联上

idea中新建一个maven项目为此处为`remote-debugger`

`pom.xml`

```
<dependency>
    <groupId>org.apache.trafodion.jdbc.t4</groupId>
    <artifactId>jdbcT4</artifactId>
    <version>2.2.0</version>
</dependency>
```

选择2.2.0的原因是从maven中央仓库上可以直接下载到源码

##### 

##### 2、添加remote运行程序

点击上方运行框添加一个remote的配置

![添加](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/01-add-run.png)



配置对应属性

host为远程程序所在的ip地址，port填写远程程序对应address参数，use module classpath选择源码能和远程对应上的项目，点击apply就可以了

![添加remote](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/02-add-remote.png)



##### 3、开始debugger

确定远程jvm处于运行状态后以`debug的方式`运行刚刚创建的remote configurations，就是图中的虫子图标，成功

后下面控制台有对应的输出。

![debug](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/03-connect.png)



在图中111行左键单击添加一个断点，然后到远程任意输入字符让程序继续运行可以看到程序运行到了这个位置

右键属性可以查看或者编辑变量，下方debugger窗口也可以查看。

![evaluate](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/03-get-change.png)



编辑变量，将user的值由原来的DB__ROOT修改为hello world，确定后继续

![evaluate](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/04-change.png)



修改变量后可以看到user改变后报错了，可以使用这种方式修改运行时的值

![error msg](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/05-remote-error.png)



#### 六、Hbase debug

##### 1、hbase参数配置

首先设置hbase启动脚本

```shell
vim conf/hbase-env.sh 

添加内容
export HBASE_MASTER_OPTS="$HBASE_MASTER_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=10445"
export HBASE_REGIONSERVER_OPTS="$HBASE_REGIONSERVER_OPTS -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=10446"

设置了master的调试端口为10445，regions的为10446

启动hbase
sh bin/start-hbase.sh
输出...
Listening for transport dt_socket at address: 10445
```

##### 2、idea调试设置

在maven中添加依赖

```
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-server</artifactId>
    <version>2.2.5</version>
</dependency>
```

添加一个remote程序，如果调试master相关代码则使用上面配置的`10445 `端口

![remote-hbase](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/07-remote-hb.png)



##### 3、调试建表代码

建表相关的代码在`HMaster#createTable`中，打一个断点

然后到服务器(虚拟机)上启动shell进行测试

```shell
 ./bin/hbase shell

hbase(main):003:0> create 'test', 'cf'
ERROR: Table already exists: test!
For usage try 'help "create"'
Took 677.1992 seconds  
```

可以看到程序停在了我们设置的断点位置

![hbase breakpoint](https://modprobe.oss-cn-beijing.aliyuncs.com/esgyn/08-hmaster-01.png)



#### 七、idea调试技巧

可以参考博客 [在Intellij IDEA中使用Debug](https://www.cnblogs.com/diaobiyong/p/10682996.html)

