package com.example.esutil.elastic.client;

import com.example.esutil.elastic.client.model.ElasticDoc;
import com.example.esutil.elastic.client.model.request.ElasticCountRequest;
import com.example.esutil.elastic.client.model.request.ElasticDeleteByQueryRequest;
import com.example.esutil.elastic.client.model.request.ElasticSearchRequest;
import com.example.esutil.elastic.client.model.response.ElasticBulkResult;
import com.example.esutil.elastic.client.model.response.ElasticCountResult;
import com.example.esutil.elastic.client.model.response.ElasticSearchResponse;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHits;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author pw7563
 * @Date 2024/8/20 11:19
 * usage 一个包装类，向上提供一个es的操作接口
 */
public class ElasticClient extends ElasticNativeClient {

    public ElasticClient(ElasticClientConfig config) {
        super(config);
    }

    /**
     * 新增文档
     * @param index
     * @param id
     * @param doc json字符串
     * @param refresh 是否立即刷新
     * @return
     */
    public ElasticDoc index(String index, String id, String doc, Boolean refresh){
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.id(id);
        indexRequest.index(index);
        indexRequest.source(doc, XContentType.JSON);
        if (refresh != null && refresh){
            indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        }
        IndexResponse indexResponse = indexNative(indexRequest);
        return ElasticDoc.create().index(indexResponse.getIndex()).id(indexResponse.getId());
    }

    /**
     * 通过指定id查询文档
     * @param index
     * @param id
     * @return
     */
    public Object get(String index, String id){
        GetRequest request = new GetRequest(index, id);
        GetResponse aNative = getNative(request);
        return ElasticDoc.create().index(aNative.getIndex()).doc(aNative.getSourceAsString());
    }

    /**
     * 搜索文档
     * @param request
     * @return
     */
    public Object search(ElasticSearchRequest request){
        SearchResponse response = searchNative(request.toSearchRequest());
        SearchHits hits = response.getHits();
        List<ElasticDoc> collect = Arrays.stream(hits.getHits()).map(x -> ElasticDoc.of(x)).collect(Collectors.toList());
//        TotalHits totalHits = hits.getTotalHits();
//        List<ElasticDoc> collect = Arrays.stream(hits.getHits()).map(x -> {
//            String sourceAsString = x.getSourceAsString();
//            ElasticDoc doc = ElasticDoc.create().id(x.getId()).index(x.getIndex()).doc(sourceAsString);
//            return doc;
//        }).collect(Collectors.toList());
        ElasticSearchResponse elasticSearchResponse = new ElasticSearchResponse();
        elasticSearchResponse.setTotal(hits.getTotalHits() == null ? null : hits.getTotalHits().value);
        elasticSearchResponse.setDocs(collect);
        return elasticSearchResponse;
    }

    public void delete(String index, String id, Boolean refresh){
        DeleteRequest deleteRequest = new DeleteRequest(index, id);
        if(refresh!= null && refresh){
            deleteRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        }
        deleteNative(deleteRequest);
    }

    /**
     * 删除指定条件的文档
     * @param queryRequest
     * @param refresh
     * @return
     */
    public ElasticBulkResult deleteByQuery(ElasticDeleteByQueryRequest queryRequest, Boolean refresh){
        DeleteByQueryRequest deleteByQueryRequest = queryRequest.toDeleteByQueryRequest();
        if(refresh != null && refresh){
            deleteByQueryRequest.setRefresh(refresh);
        }
        BulkByScrollResponse response = deleteByQueryNative(deleteByQueryRequest);
        ElasticBulkResult result = new ElasticBulkResult();
        result.setSize(response.getDeleted());
        result.setTotal(response.getTotal());
        return result;
    }

    /**
     * 更新指定id 的文档
     * @param index
     * @param id
     * @param doc
     * @param refresh
     */
    public void update(String index, String id, String doc, Boolean refresh) {
        UpdateRequest request = new UpdateRequest(index, id);
        request.doc(doc, XContentType.JSON);
        if (refresh != null && refresh) {
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        }
        updateNative(request);
    }

    public ElasticCountResult count(ElasticCountRequest request){
        CountResponse response = countNative(request.toCountRequest());
        ElasticCountResult result = new ElasticCountResult();
        result.setCount(response.getCount());
        result.setTerminatedEarly(response.isTerminatedEarly() != null && response.isTerminatedEarly());
        return result;
    }


}
