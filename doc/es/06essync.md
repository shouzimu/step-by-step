记录一次使用es优化用户模糊查询的过程

相关中间件及版本如下

| 应用          | 版本          |
| ------------- | :------------ |
| ElasticSearch | 5.5           |
| mysql         | 5.6           |
| logstash      | -             |
| canal         | 1.1.4         |
| Springboot    | 2.0.5.RELEASE |



替换前：mysql模糊查询用户，耗时3s+

替换后：es查询id，再in查询数据库 100ms



#### 思路：

```
用户表的更新走binlog同步，中间间使用canal和otter，二者介绍这里不再赘述
```



#### 一、ElasticSearch配置

创建索引，5.5版本还需要设置type，比较蛋疼

```curl
1、创建索引member_info
PUT /member_info

2、设置type member
PUT /member_info/_mapping/member

es工作到此结束
```



#### 二、canal配置

##### 1、canal server端配置

```shell
直接使用的单机版

github下载的canal.deployer-1.1.4.tar.gz

解压，编辑 conf/example/instance.properties 修改以下关键配置
canal.instance.master.address=数据库ip:数据库端口
canal.instance.dbUsername=数据库用户名
canal.instance.dbPassword=数据库密码
canal.instance.filter.regex=member_info (注：这个地方我只需要监听这一个表的数据变化)

启动
bin/startup.sh
```


##### 2、canal 客户端 otter的配置，这个地方写的代码，直接上

MemberCanalClient.java

```java
@Component
@Slf4j
public class MemberCanalClient {

    private TransportClient transportClient;
    
    String index = "member_info";
    String type = "member";
    
    public void initElasticSearch() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "my-application-1").put("xpack.security.user", "elastic:Zanchina2016").put("client.transport.sniff", false).build();
        transportClient = new PreBuiltXPackTransportClient(settings);
        TransportAddress transportAddress = new InetSocketTransportAddress(InetAddress.getByName("10.141.183.28"), 9300);
        transportClient.addTransportAddress(transportAddress);
    }
    
    public void otterSync() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11113), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe("member.member_info");
            connector.rollback();
            while (true) {
                // 获取指定数量的数据
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }
    
                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }
        } finally {
            connector.disconnect();
        }
    }
    
    private void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }
    
            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
    
            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));
    
            for (RowData rowData : rowChage.getRowDatasList()) {
                try {
                    operator(rowData, eventType);
                }catch (Exception e){
    
                }
            }
        }
    }
    
    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }


    private void operator(RowData rowData, EventType eventType) {
        System.out.println(eventType);
        List<Column> columnList = rowData.getAfterColumnsList();
        if (eventType == EventType.DELETE) {
            columnList = rowData.getBeforeColumnsList();
        }
        Member m = new Member();
        boolean update = false;
        for (Column column : columnList) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
    
            switch (column.getName()) {
                case "id":
                    m.setId(column.getValue());
                    update = checkUpdate(update, column.getUpdated());
                    break;
                case "mobile":
                    m.setMobile(column.getValue());
                    update = checkUpdate(update, column.getUpdated());
                    break;
                case "name":
                    m.setName(column.getValue());
                    update = checkUpdate(update, column.getUpdated());
                    break;
                case "pinyin_code":
                    m.setPy(column.getValue());
                    update = checkUpdate(update, column.getUpdated());
                    break;
                case "is_deleted":
                    m.setIsDeleted(column.getValue());
                    update = checkUpdate(update, column.getUpdated());
                    break;
                default:
                    break;
            }
        }
        //关键字段更新才执行后续操作
        if (update) {
            log.info("sync member:{}", m);
            if (eventType == EventType.DELETE || Objects.equals(m.getIsDeleted(), "1")) {
                deleteById(m.getId());
            } else {
                saveOrUpdateIndex(m);
            }
        }
    
    }
    
    private boolean checkUpdate(boolean u, boolean updated) {
        if (u || updated) {
            return true;
        }
        return false;
    }
    
    public void saveOrUpdateIndex(Member member) {
        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id", member.getId())
                    .field("mobile", member.getMobile())
                    .field("name", member.getName())
                    .field("py", member.getPy())
                    .endObject();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    
        GetResponse getResponse = searchById(member.getId());
        if (null == getResponse || null == getResponse.getSourceAsString()) {
            //创建索引
            IndexResponse indexResponse = transportClient.prepareIndex(index, type, member.getId()).setSource(builder).execute().actionGet();
            System.out.println(indexResponse);
    
        } else {
            //更新索引
            try {
                UpdateResponse response = transportClient.prepareUpdate(index, type, member.getId()).setDoc(builder).get();
                System.out.println(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public GetResponse searchById(String id) {
        GetResponse response = transportClient.prepareGet(index, type, id).execute().actionGet();
        return response;
    }
    
    public DeleteResponse deleteById(String id) {
        DeleteResponse response = transportClient.prepareDelete(index, type, id).execute().actionGet();
        log.info("deleteById:{},response:{}", id, response);
        return response;
    }
}
```

Member.java

```java
public class Member implements Serializable {
    private String id;
    private String mobile;
    private String name;
    private String py;
    private String isDeleted;
 }
```

canal client使用springboot启动，此处不展开

启动clinet

#### 三、logstash的处理

canal只能处理增量的数据，那么历史的怎么办呢，这个地方使用logstash导入，配置文件如下

sync.conf

```properties
input {
    stdin {
    }
    jdbc {


mysql 数据库链接,database为数据库名

filter {
    json {
        source => "message"
        remove_field => ["message"]
    }
}

output {
    elasticsearch {
        hosts => ["es地址:es端口"]
        index => "member_info"
        user  => "es用户名"
        password => "es密码"
        # 文档id映射，为了更新或者删除方便
        document_id => "%{id}"
    }
    stdout {
        codec => json_lines
    }
}
```

启动logstatsh同步
```shell
bin/logstash -f sync.conf
```

#### 四、查询代码

从es中查询数据

```java
@Override
public List<Member> queryMember(String keyWord) {
        long start = System.currentTimeMillis();
        //搜索用户
        List<Member> elasticList = new ArrayList<>(30);
        if(StringUtils.isBlank(keyWord)){
            return elasticList;
        }

        //ES检索并排序
        try {

            BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();

            QueryBuilder match = QueryBuilders.queryStringQuery(keyWord)
                    .field("name").field("mobile").field("py").defaultOperator(Operator.AND).boost(100);
            booleanQuery.should(match);

            QueryBuilder like =  QueryBuilders.queryStringQuery(keyWord+"*")
                    .field("name").field("mobile").field("py").defaultOperator(Operator.AND).boost(50);
            booleanQuery.should(like);


            QueryBuilder likeAll =  QueryBuilders.queryStringQuery("*"+keyWord+"*")
                    .field("name").field("mobile").field("py").defaultOperator(Operator.AND).boost(50);
            booleanQuery.should(likeAll);



            SearchHits searchHits = transportClient.prepareSearch(memberIndex)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setTimeout(TimeValue.timeValueSeconds(3)).
                            setExplain(true).setQuery(booleanQuery).setFrom(0).setSize(30).get()
                    .getHits();
            searchHits.forEach(searchHitFields -> {
                Map<String, Object> map = searchHitFields.getSource();
                String name = (String) map.get("name");
                String mobile = (String) map.get("mobile");
                String py = (String) map.get("py");
                String id = (String) map.get("id");
                Member m = new Member();
                m.setId(id);
                m.setName(name);
                m.setMobile(mobile);
                m.setPy(py);
                elasticList.add(m);
            });
        } catch (Exception e) {
            log.error("es搜索异常:{}", ExceptionStackUtil.getExceptionStackTrace(e));
        }
        long end = System.currentTimeMillis();
        log.info("从es中搜索出来的值 key:{} time:{}ms res：{}",keyWord, end - start, elasticList.size());
        return elasticList;
}
```
