package com.dh.es;

import java.util.LinkedList;
import java.util.Map;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ElasticServiceImpl implements ElasticService {

    private static final Logger log = LoggerFactory.getLogger(ElasticServiceImpl.class);

    @Autowired
    private TransportClient transportClient;

    private String index = "hisappzstt";

    private static final String drug_type = "drug";

    @Override
    public LinkedList<String> queryDrug(String keyWord) {
        LinkedList<String> elasticList = new LinkedList<>();
        if (!StringUtils.isEmpty(keyWord) && keyWord.length() > 5) {
            return elasticList;
        }

        long start = System.currentTimeMillis();
        //ES检索并排序
        try {

            BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();

            QueryBuilder pinying = QueryBuilders.queryStringQuery(keyWord)
                    .field("drugname.pinyin").defaultOperator(Operator.AND).boost(100);

            QueryBuilder pinyinCode = QueryBuilders.matchQuery("pinyin_code", keyWord).operator(Operator.AND).boost(80);

            float aliasBoost = 50;
            QueryBuilder alias1 = QueryBuilders.matchQuery("alias1", keyWord).operator(Operator.AND).boost(aliasBoost);
            QueryBuilder alias2 = QueryBuilders.matchQuery("alias2", keyWord).operator(Operator.AND).boost(aliasBoost);
            QueryBuilder alias3 = QueryBuilders.matchQuery("alias3", keyWord).operator(Operator.AND).boost(aliasBoost);
            QueryBuilder alias4 = QueryBuilders.matchQuery("alias4", keyWord).operator(Operator.AND).boost(aliasBoost);
            QueryBuilder alias5 = QueryBuilders.matchQuery("alias5", keyWord).operator(Operator.AND).boost(aliasBoost);

            booleanQuery.should(pinying);
            booleanQuery.should(pinyinCode);
            booleanQuery.should(alias1);
            booleanQuery.should(alias2);
            booleanQuery.should(alias3);
            booleanQuery.should(alias4);
            booleanQuery.should(alias5);

            QueryBuilder prefx = QueryBuilders.queryStringQuery(keyWord + "*")
                    .field("drugname.prefix").field("drugname.pinyin").field("ddrugname.SPY").field("drugname.fpy")
                    .field("drugname.iks").boost(40).defaultOperator(Operator.AND);

            QueryBuilder suffix = QueryBuilders.queryStringQuery("*" + keyWord)
                    .field("drugname.prefix").field("drugname.pinyin").field("ddrugname.SPY").field("drugname.fpy")
                    .field("drugname.iks").boost(30).defaultOperator(Operator.AND);

            QueryBuilder fullLike = QueryBuilders.queryStringQuery("*" + keyWord + "*")
                    .field("drugname.prefix").field("drugname.pinyin").field("ddrugname.SPY").field("drugname.fpy")
                    .field("drugname.iks").boost(20).defaultOperator(Operator.AND);
            booleanQuery.should(prefx);
            booleanQuery.should(suffix);
            booleanQuery.should(fullLike);

            QueryBuilder name = QueryBuilders.queryStringQuery(keyWord + "*")
                    .field("drugname").defaultOperator(Operator.AND).boost(15);

            booleanQuery.should(name);

            SearchHits searchHits = transportClient.prepareSearch(index)
                    .setSearchType(SearchType.QUERY_THEN_FETCH)
                    .setTimeout(TimeValue.timeValueSeconds(3)).
                            setExplain(true).setTypes(drug_type).setQuery(booleanQuery).setFrom(0).setSize(50).get()
                    .getHits();
            searchHits.forEach(searchHitFields -> {
                Map<String, Object> map = searchHitFields.getSource();
                elasticList.add(map.get("drugid").toString());
            });
        } catch (Exception e) {
            log.error("es搜索异常:{}", e);
        }
        long end = System.currentTimeMillis();
        log.info("从es中搜索出来的值 key:{} time:{}ms res：{}", keyWord, end - start, elasticList.toString());
        return elasticList;
    }


}
