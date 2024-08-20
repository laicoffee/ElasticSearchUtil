package com.example.esutil.elastic.client;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

import java.io.IOException;
import java.util.Arrays;

/**
 * @Author pw7563
 * @Date 2024/8/20 10:18
 * usage
 */
public class ElasticNativeClient {

    protected ElasticClientConfig config;
    protected RestHighLevelClient client;

    public ElasticNativeClient(ElasticClientConfig config) {
        this.config = config;
        this.client = createRestHighLevelClient(config);
    }

    private static RestHighLevelClient createRestHighLevelClient(ElasticClientConfig config) {

        HttpHost[] httpHosts = Arrays.stream(config.getHost().split(","))
                .map(s -> {
                    String[] split = s.split(":");
                    return new HttpHost(split[0], Integer.parseInt(split[1]), config.getSchema());
                })
                .toList()
                .toArray(new HttpHost[]{});

        RestClientBuilder clientBuilder = RestClient.builder(httpHosts)
                .setRequestConfigCallback(requestConfigBuilder -> {
                    requestConfigBuilder.setConnectTimeout(config.getConnectTimeout());
                    requestConfigBuilder.setSocketTimeout(config.getSocketTimeout());
                    requestConfigBuilder.setConnectionRequestTimeout(config.getConnectionRequestTimeout());
                    return requestConfigBuilder;
                })
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.setMaxConnTotal(config.getMaxConnectNum());
                    httpClientBuilder.setMaxConnPerRoute(config.getMaxConnectPerRoute());
                    return httpClientBuilder;
                });
        return new RestHighLevelClient(clientBuilder);
    }

    /**
     * 添加文档
     * @param request
     * @return
     */
    public IndexResponse indexNative(IndexRequest request){
        try{
            return client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es添加文档数据失败",e);
        }
    }

    /**
     * 删除指定文档
     * @param request
     * @return
     */
    public DeleteResponse deleteNative(DeleteRequest request){
        try{
            return client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es删除文档数据失败",e);
        }
    }

    /**
     * 根据指定条件删除文档
     * @param request
     * @return
     */
    public BulkByScrollResponse deleteByQueryNative(DeleteByQueryRequest request){
        try{
            return client.deleteByQuery(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es根据指定条件删除文档数据失败",e);
        }
    }

    /**
     * 根据id获取文档
     * @param request
     * @return
     */
    public GetResponse getNative(GetRequest request){
        try{
            return client.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
           throw new RuntimeException("es根据id获取文档数据失败",e);
        }
    }

    /**
     * 更新文档
     * @param request
     * @return
     */
    public UpdateResponse updateNative(UpdateRequest request){
        try{
            return client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es更新文档数据失败",e);
        }
    }

    /**
     * 搜索文档
     * @param request
     * @return
     */
    public SearchResponse searchNative(SearchRequest request) {
        request.source().timeout(new TimeValue(config.getRequestTimeout()));
        request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        try {
            return client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es搜索文档数据失败", e);
        }
    }

    // todo: scrollNative

    /**
     * 统计文档数量
     * @param request
     * @return
     */
    public CountResponse countNative(CountRequest request) {
        try {
            return client.count(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es统计文档数据失败", e);
        }
    }

    /**
     * 批量操作文档
     * @param request
     * @return
     */
    public BulkResponse bulkNative(BulkRequest request) {
        try{
            return client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("es批量操作文档数据失败",e);
        }
    }



}
