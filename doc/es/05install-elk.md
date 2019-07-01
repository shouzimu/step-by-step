### 一、安装ElasticSearch

#### 1、下载

https://www.elastic.co/cn/downloads/elasticsearch

选择对应的版本

此处我们是在Linux上安装，选择linux版本下载



#### 2 、安装



进入下载目录

```shell
 解压
 tar -zxvf elasticsearch-7.2.0-linux-x86_64.tar.gz
 
 移动
 mv elasticsearch-7.2.0   /usr/local/
```



#### 3、目录结构


| 目录 | 配置文件 | 描述  |
| ----  |   ----  |  ---- |
| bin |  | 脚本文件 包括启动elasticsearch，安转插件 <br />运行统计数据等 |
| config | elasticsearch.yml | 配置文件、user、role based相关配置 |
| JDK |  | Java运行环境 |
| data | path.data | 数据文件 |
| lib |  | Java类库 |
| logs | path.log | 日志文件 |
| modules |  | 包含所有ES模块 |
| plugins |  | 包含所有已安转插件 |



#### 4、配置建议

修改 JVM配置 config/jvm.options 7.2下默认是1G

1、Xmx和Xms设置为一样的值

2、Xmx不要超过机器内存的50%

3、不超过30GB



#### 5、启动

```shell
 bin/elasticsearch
 
 Exception in thread "main" java.lang.RuntimeException: starting java failed with [1]
output:
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 986513408 bytes for committing reserved memory.
# An error report file with more information is saved as:
# logs/hs_err_pid28582.log
error:
Java HotSpot(TM) 64-Bit Server VM warning: Option UseConcMarkSweepGC was deprecated in version 9.0 and will likely be removed in a future release.
Java HotSpot(TM) 64-Bit Server VM warning: INFO: os::commit_memory(0x00000000c5330000, 986513408, 0) failed; error='Not enough space' (errno=12)
	at org.elasticsearch.tools.launchers.JvmErgonomics.flagsFinal(JvmErgonomics.java:111)
	at org.elasticsearch.tools.launchers.JvmErgonomics.finalJvmOptions(JvmErgonomics.java:79)
	at org.elasticsearch.tools.launchers.JvmErgonomics.choose(JvmErgonomics.java:57)
	at org.elasticsearch.tools.launchers.JvmOptionsParser.main(JvmOptionsParser.java:89)
```



产生上述错误的原因是内存不足了，因为我的机器只有2G内存，所以清理一下进程正常启动



查看是否启动成功

```shell
curl localhost:9200
{
  "name" : "instance-nol4mhou",
  "cluster_name" : "elasticsearch",
  "cluster_uuid" : "ojidbIM6SNSjWvPje_ZZ0w",
  "version" : {
    "number" : "7.2.0",
    "build_flavor" : "default",
    "build_type" : "tar",
    "build_hash" : "508c38a",
    "build_date" : "2019-06-20T15:54:18.811730Z",
    "build_snapshot" : false,
    "lucene_version" : "8.0.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```

可以看到启动成功了



#### 6、安装插件

查看安装的插件列表

```
bin/elasticsearch-plugin list
```

安装通用的国际化分词插件analysis-icu

```shell
bin/elasticsearch-plugin install analysis-icu
```

插件需要重启才能生效

重启后我们可以访问浏览器查看

```shell
curl -i localhost:9200/_cat/plugins
HTTP/1.1 200 OK
content-type: text/plain; charset=UTF-8
content-length: 37

instance-nol4mhou analysis-icu 7.2.0
```



#### 7、配置安全

启用xpack

配置config/elasticsearch.yml

```
xpack.security.enabled: true

运行设置密码
bin/elasticsearch-setup-passwords interactive

```





### 二、安装Kibana

#### 1、下载

https://www.elastic.co/cn/downloads/kibana

选择对应的版本

此处我们是在Linux上安装，选择linux版本下载



#### 2 、安装



进入下载目录

```shell
 解压
 tar -zxvf kibana-7.2.0-linux-x86_64.tar.gz

```



#### 3、启动

```shell
bin/kibana
```



#### 4、安装插件

查看安装的插件列表，命令格式和elasticsearch基本一样

```
bin/kibana-plugin list

bin/kibana-plugin install xxx
```

X-Pack是一个Elastic Stack的扩展，将安全，警报，监视，报告和图形功能包含在一个易于安装的软件包中

 早期版本中需要自己安装，下载kibana已经默认集成了这个插件，不用再单独安装

Plugin installation was unsuccessful due to error "Kibana now contains X-Pack by default, there is no longer any need to install it as it is already present."



#### 5、配置

允许kibana远程访问

```
vim config/kibana.yml 

设置server.host
```



#### 6、DevTools快捷键



基于Mac

| 组合             | 作用                                |
| ---------------- | ----------------------------------- |
| Command + /      | 查看api帮助文档                     |
| Command + enter  | 执行当前行的操作                    |
| optiona+上下箭头 | 上移/下移选中的代码块或者当前行代码 |




### 三、安装Logstash
#### 1、下载

https://www.elastic.co/cn/downloads/logstash

此处我们选择tar.gz的格式进行下载

#### 2 、安装

进入下载目录

```shell
解压
tar -zxvf logstash-7.2.0.tar.gz

```
#### 3、运行

```shell
bin/logstash -f xxxx.conf
```



#### 四、集成测试

使用logstash导入测试数据集

我们到[movielens](<https://grouplens.org/datasets/movielens/>)下载MovieLens Latest Datasets的small版本



编写配置文件

```
touch data/movielens.conf
```



```yaml
input { 
    file {
        path => "/home/server/dev/elk/data/ml-latest-small/movies.csv"
        start_position => "beginning"
        sincedb_path => "/dev/null"
    }
}

filter {
    csv {
        separator => ","
        columns => ["id","content","genre"]
    }

    mutate {
        split => { "genre" => "|" }
        remove_field => ["path","hosts","@timestamp","message"]
    }

    mutate {
        split => { "content" => "(" }
        add_field => { "title" => "%{[content][0]}" }
        add_field => { "year" => "%{[content][1]}" }
    }

    mutate {
    convert => {
      "year" => "integer"
    }
    strip => ["title"]
    remove_field => ["path", "host","@timestamp","message","content"]
  }
}

output {
    elasticsearch {
        hosts => "http://localhost:9200"
        index => "movies"
        document_id => "%{id}"
        user => "logstash_system"
        password => "changeme"
    }
}
```

执行命令导入到es

```
 bin/logstash -f data/movielens.conf
```





