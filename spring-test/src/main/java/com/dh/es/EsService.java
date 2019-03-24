//package com.dh.es;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.TransportAddress;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//import org.junit.Test;
//
//public class EsService {
//
//    TransportClient client;
//
//    public void init() {
//        try {
//            client = new PreBuiltTransportClient(Settings.EMPTY)
//                    .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testMatchQuery() {
//        init();
//        QueryBuilder matchQuery = QueryBuilders.matchQuery("about","rock climbing");
//
//        SearchHits searchHits = client.prepareSearch("megacorp")
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setTimeout(TimeValue.timeValueSeconds(3)).setQuery(matchQuery).get().getHits();
//
//        SearchHit[] hits = searchHits.getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit);
//        }
//    }
//
//    @Test
//    public void testMatchQueryHighlight() {
//        init();
//        QueryBuilder matchQuery = QueryBuilders.matchQuery("about","rock climbing");
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("about");
//
//        SearchHits searchHits = client.prepareSearch("megacorp")
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setTimeout(TimeValue.timeValueSeconds(3)).setQuery(matchQuery).highlighter(highlightBuilder).get().getHits();
//
//        SearchHit[] hits = searchHits.getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit);
//        }
//    }
//
//    @Test
//    public void testBoolFilterQuery() {
//        init();
//        QueryBuilder builder = QueryBuilders.queryStringQuery("smith").field("last_name");
//        QueryBuilder filter = QueryBuilders.queryStringQuery("smith").field("last_name");
//        QueryBuilder matchQuery = QueryBuilders.boolQuery().must(builder).filter(filter);
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.field("about");
//
//        SearchHits searchHits = client.prepareSearch("megacorp")
//                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
//                .setTimeout(TimeValue.timeValueSeconds(3)).setQuery(matchQuery).highlighter(highlightBuilder).get().getHits();
//
//        SearchHit[] hits = searchHits.getHits();
//        for (SearchHit hit : hits) {
//            System.out.println(hit);
//        }
//    }
//}
