## Search API

#### URI Search

* 在url中使用参数查询

* 使用q指定查询字符串， query string syntax KV键值对

  ```
  curl -XGET "http://localhost:9200/movies/_search?q=title:Black
  例如这个查询就是movies索引下title为Black的数据
  ```



#### RequestBody Search

* 使用ElasticSearch提供的，基于json格式的更加完备的 query dynamic special language (DSL)

* 同时支持Get和Post

  Kibana中语法如下

  ```
  GET /movies/_search
  {
    "query": {
      "match_all": {}
    }
  }
  ```

  curl语法如下

  movies：为查询的索引   _search：指定的api  query:查询 match_all返回所有的文档

  ```shell
  curl -XGET "http://localhost:9200/movies/_search" -H 'Content-Type: application/json' -d'{  "query": {    "match_all": {}  }}'
  ```

  

- 指定查询索引

| 语法                   | 范围                  |
| ---------------------- | --------------------- |
| /_search               | 所有索引              |
| /index1/_search        | index1                |
| /index1,index2/_search | index1和index2        |
| /index*/_search        | 所有以index开头的索引 |



* 搜索的response

![image-20190714102340427](https://modprobe.oss-cn-beijing.aliyuncs.com/github/sts//es-response.png)



### 搜索相关性

* precision 查准率 - 尽可能返回较少的无关文档
* recall 查全率 -  尽可能返回较多的相关文档
* ranking - 是否能够按照相关度排序



![image-20190714103114009](https://modprobe.oss-cn-beijing.aliyuncs.com/github/sts//es-positive.png)

```
三角形：不相关的内容
绿色圆圈：需要被搜索的内容
大圆圈：搜索结果
大圆圈里黄色：不应该被搜索到的内容
大圆圈外黄色：应该被搜索到的内容	
```





#### URI Search搜索详解

```shell
GET /movies/_search?q=2012&df=title&sort=year:desc&from=0&size=10&timeout=1s
{
  "profile": "true"
}
```

- q指定查询语句

- df默认字段，不指定时查询全部字段，如果不用df，还可以用q=title:2012实现类似效果

- sort排序 from和size用于分页

- Profile查看查询是如何被执行的，此处感觉类似于mysql的explain

  

#### 不同的查询

- 指定字段查询与泛查询

  - q=title:2013 / q=2012

  - 泛查询，正对_all没有指定df字段，查询所有字段

- term vs phrase

  - term 查询 Beautiful Mind 等效于 Beautiful or Mind
  - phrase 查询 Beautiful Mind  等效于 Beautiful and Mind，并且还要求前后顺序一样，条件用引号包含起来

- 分组与引号

  - title :( Beautiful Mind)
  - title :"Beautiful Mind"

  ```
  ## 使用引号，Phrase查询
  GET /movies/_search?q=title:"Beautiful Mind"
  {
    "profile": "true"
  }
  
  ## 指定了title为Beautiful Mind泛查询
  GET /movies/_search?q=title:Beautiful Mind
  {
    "profile": "true"
  }
  
  
  ## 分组 布尔查询，含有Beautiful或者Mind
  GET /movies/_search?q=title:(Beautiful Mind)
  {
    "profile": "true"
  }
  ```

- 布尔操作 两个term在一起的时候默认是 or 的操作

  - AND / OR / NOT 或者 && / || /!
    - 必须大写
    - title(martix NOT reloaded)
  - 分组
    - +表示must
    - -表示not must
    - title(+martix -reloaded)

  ```
  GET /movies/_search?q=title:(Beautiful AND Mind)
  {
    "profile": "true"
  }
  "type" : "BooleanQuery",
  "description" : "+title:beautiful +title:mind",
  
  GET /movies/_search?q=title:(Beautiful NOT Mind)
  {
    "profile": "true"
  }
   "type" : "BooleanQuery",
   "description" : "title:beautiful -title:mind",
  ```

- 范围查询

- 通配符查询 效率较低，占用内存空间大 不建议使用，特别是放在前面

  - ?代表一个字符。*表示0个或多个字符
    - title:Bea?t
    - title:be*

- 正则表达式

  - title:[bt]oy

- 模糊匹配与近似查询 波浪线指支持最大编辑距离，[默认值为2 ](<https://www.elastic.co/guide/cn/elasticsearch/guide/current/fuzziness.html>)

  - title:beautfifl~1
  - title:"lord rings"~2

  ```
  # 范围查询 年大于1980
  GET /movies/_search?q=year:>1980
  {
    "profile": "true"
  }
  "type" : "IndexOrDocValuesQuery",
  "description" : "year:[1981 TO 9223372036854775807]",
  
  # 范围查询 年大于1980~2010
  GET /movies/_search?q=year:[1980 TO 2010]
  {
    "profile": "true"
  }
  "type" : "IndexOrDocValuesQuery",
  "description" : "year:[1980 TO 2010]",
  
  
  # 范围查询 title含有 mind 且年大于1980~2010
  GET /movies/_search?q=title:mind AND year:[1980 TO 2010]
  {
    "profile": "true"
  }
   "type" : "BooleanQuery",
   "description" : "+title:mind +year:[1980 TO 2010]",
   
   
   # 范围查询 title含有 s 且年大于1980~2010
  GET /movies/_search?q=title:*s* AND year:[1980 TO 2010]
  {
    "profile": "true"
  }
  ```



####  RequestBody Search 搜索详解

