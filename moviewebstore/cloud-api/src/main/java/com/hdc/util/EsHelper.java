//package com.hdc.util;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHost;
//import org.apache.http.HttpStatus;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.aggregations.Aggregation;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author herui04
// * @create 2021/2/3 14:11
// * @Description es high level restclint api
// */
//@Slf4j
//public class EsHelper {
//    private static RestHighLevelClient esClient = null;
//    private static Logger logger = LoggerFactory.getLogger(EsHelper.class);
//
//    static {
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
////        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));
//        RestClient client = RestClient.builder(
//                new HttpHost("192.168.202.128", 9200, "http"))
//                .setFailureListener(new RestClient.FailureListener() { // 连接失败策略
//                    public void onFailure(HttpHost host) {
//                        logger.error("init client error, host:{}", host);
//                    }
//                })
//                .setMaxRetryTimeoutMillis(10000) // 超时时间
//                .setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider))
//                .build();
//        esClient = new RestHighLevelClient(client);
//        System.out.println(esClient);
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param index
//     * @param type
//     * @param dataMap
//     * @return
//     * @throws Exception
//     */
//    public static boolean index(String index, String type, Map<String, Object> dataMap) throws Exception {
//        IndexResponse response = null;
//        try {
//            IndexRequest request = new IndexRequest(index, type).source(dataMap);
//            response = esClient.index(request);
//            if (response.status().getStatus() == HttpStatus.SC_CREATED || response.status().getStatus() == HttpStatus.SC_OK) {
//                logger.info("index success");
//                return true;
//            } else {
//                logger.error("index error : {}", response.toString());
//                return false;
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    /**
//     * 检索
//     *
//     * @param index
//     * @param type
//     * @param sourceBuilder
//     * @param clzz
//     * @param <T>
//     * @return
//     */
//    public static <T> List<T> search(String index, String type, SearchSourceBuilder sourceBuilder, Class<T> clzz) throws Exception {
//        List<T> dataList = Lists.newArrayList();
//        SearchRequest searchRequest = new SearchRequest()
//                .indices(index)
//                .types(type)
//                .source(sourceBuilder);
//        SearchResponse response = null;
//        try {
//            response = esClient.search(searchRequest);
//            if (response.status().getStatus() == HttpStatus.SC_OK) {
//                // 提取数据
//                response.getHits().forEach(hit -> {
//                    T t = JSON.parseObject(hit.getSourceAsString(), clzz);
//                    dataList.add(t);
//                });
//                return dataList;
//            } else {
//                logger.error("index false, error : {}", response);
//                return null;
//            }
//        } catch (IOException e) {
//            logger.error("系统异常", e);
//            return null;
//        }
//    }
//
//
//    /**
//     * 聚合
//     *
//     * @param index
//     * @param type
//     * @param sourceBuilder
//     * @return
//     */
//    public static Map<String, Long> aggregation(String index, String type, SearchSourceBuilder sourceBuilder, String groupBy) throws Exception {
//        Map<String, Long> aggMap = Maps.newHashMap();
//        SearchRequest searchRequest = new SearchRequest()
//                .indices(index)
//                .types(type)
//                .source(sourceBuilder);
//        SearchResponse response = null;
//        try {
//            response = esClient.search(searchRequest);
//            if (response.status().getStatus() == HttpStatus.SC_OK) {
//                // 提取数据
//                Aggregations aggregations = response.getAggregations();
//                Terms byStateAggs = aggregations.get(groupBy);
//                List<? extends Terms.Bucket> buckets = byStateAggs.getBuckets();
//                buckets.forEach(vo -> {
//                    aggMap.put(vo.getKeyAsString(), vo.getDocCount());
//                });
//            } else {
//                logger.error("aggregation false, error : {}", response);
//            }
//        } catch (IOException e) {
//            logger.error("系统异常", e);
//        }
//        return aggMap;
//    }
//
//
//    /**
//     * 聚合
//     *
//     * @param index
//     * @param type
//     * @param sourceBuilder
//     * @return
//     */
//    public static Map<String, Long> aggregationDateHistogram(String index, String type, SearchSourceBuilder sourceBuilder, String groupBy) throws Exception {
//        Map<String, Long> aggMap = Maps.newHashMap();
//        SearchRequest searchRequest = new SearchRequest()
//                .indices(index)
//                .types(type)
//                .source(sourceBuilder);
//        SearchResponse response = null;
//        try {
//            response = esClient.search(searchRequest);
//            if (response.status().getStatus() == HttpStatus.SC_OK) {
//                // 提取数据
//                Aggregations aggregations = response.getAggregations();
//                Aggregation aggs = aggregations.get(groupBy);
//                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(aggs));
//                JSONArray buckets = jsonObject.getJSONArray("buckets");
//                buckets.forEach(vo -> {
//                    JSONObject buck = (JSONObject) vo;
//                    aggMap.put(buck.getString("keyAsString"), Long.parseLong(buck.getString("docCount")));
//                });
//            } else {
//                logger.error("aggregation false, error : {}", response);
//            }
//        } catch (IOException e) {
//            logger.error("系统异常", e);
//        }
//        return aggMap;
//    }
//
//
//    /**
//     * 双层聚合
//     *
//     * @param index
//     * @param type
//     * @param sourceBuilder
//     * @return
//     */
//    public static Map<String, Map<String, Long>> aggregationDateHistogramDouble(String index, String type, SearchSourceBuilder sourceBuilder, String groupBy1, String groupBy2) throws Exception {
//        Map<String, Map<String, Long>> resultMap = Maps.newHashMap();
//        SearchRequest searchRequest = new SearchRequest()
//                .indices(index)
//                .types(type)
//                .source(sourceBuilder);
//        SearchResponse response = null;
//        try {
//            response = esClient.search(searchRequest);
//
//            if (response.status().getStatus() == HttpStatus.SC_OK) {
//                // 提取数据
//                Aggregations aggregations = response.getAggregations();
//                Terms byStateAggs = aggregations.get(groupBy1);
//                List<? extends Terms.Bucket> buckets = byStateAggs.getBuckets();
//                buckets.forEach(vo -> {
//                    Aggregations timeAggs = vo.getAggregations();
//                    Aggregation innerAgg = timeAggs.get(groupBy2);
//                    Map<String, Long> aggMap = Maps.newHashMap();
//                    JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(innerAgg));
//                    JSONArray innerBuckets = jsonObject.getJSONArray("buckets");
//                    innerBuckets.forEach(po -> {
//                        JSONObject buck = (JSONObject) po;
//                        aggMap.put(buck.getString("keyAsString"), Long.parseLong(buck.getString("docCount")));
//                    });
//                    resultMap.put(vo.getKeyAsString(), aggMap);
//                });
//            } else {
//                logger.error("aggregation false, error : {}", response);
//            }
//        } catch (IOException e) {
//            logger.error("系统异常", e);
//        }
//        return resultMap;
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        System.out.println(1);
//        Map<String, Object> dataMap = Maps.newHashMap();
//        dataMap.put("imei", "abcderd");
////        dataMap.put("title", "hi，今天给你推荐的帖子你来看看2222~");
////        dataMap.put("subTitle", "null");
////        dataMap.put("document", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
////        dataMap.put("url", "https://www.58corp.com");
////        dataMap.put("batchId", "0Kw5k7PE");
////        dataMap.put("ncode", "tcbl_crzh");
////        dataMap.put("pushSource", "31");
////        dataMap.put("pushTime", "2021-02-03 06:12:35");
////        dataMap.put("level", "INFO");
////        dataMap.put("status", "onSuccess");
//        EsHelper.index("wptpush", "wptpush", dataMap);
//
////        List<JSONObject> search = EsHelper.search("wptpush", "wptpush", new SearchSourceBuilder()
////                .query(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("ncode", "tcbl_htjgtz"))), JSONObject.class);
////
////        System.out.println(search);
//
//
////        Map<String, Long> aggregation = EsHelper.aggregation("wptpush", "wptpush",
////                new SearchSourceBuilder().size(0).query(
////                        QueryBuilders.boolQuery().must(
////                                QueryBuilders.termQuery("status", "onSuccess")
////                        )
////                ).aggregation(AggregationBuilders.terms("group_by_ncode")
////                        .field("ncode"))
////                , "group_by_ncode");
////        System.out.println(aggregation);
////    }
//
//}}
