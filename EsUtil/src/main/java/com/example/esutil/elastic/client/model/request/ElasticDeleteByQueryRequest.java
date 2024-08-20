package com.example.esutil.elastic.client.model.request;

import lombok.Data;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

/**
 * @Author pw7563
 * @Date 2024/8/20 15:23
 * usage 请求es的通用参数模板
 */
@Data
public class ElasticDeleteByQueryRequest {

    private String indices;
    private Integer size;
    private QueryBuilder query;

    public DeleteByQueryRequest toDeleteByQueryRequest() {
        DeleteByQueryRequest request = new DeleteByQueryRequest();
        request.indices(indices);
        request.setMaxDocs(size);
        request.setQuery(query);
        request.setConflicts("proceed");
        request.setIndicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        return request;
    }

}