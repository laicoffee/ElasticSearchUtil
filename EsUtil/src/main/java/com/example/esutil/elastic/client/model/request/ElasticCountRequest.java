package com.example.esutil.elastic.client.model.request;

import lombok.Data;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @Author pw7563
 * @Date 2024/8/20 15:52
 * usage
 */
@Data
public class ElasticCountRequest {

    private String indices;

    private QueryBuilder query;

    private Integer terminateAfter;

    public static ElasticCountRequest create() {
        return new ElasticCountRequest();
    }

    public ElasticCountRequest indices(String indices) {
        this.indices = indices;
        return this;
    }

    public ElasticCountRequest query(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public ElasticCountRequest terminateAfter(Integer terminateAfter) {
        this.terminateAfter = terminateAfter;
        return this;
    }


    public CountRequest toCountRequest() {
        CountRequest request = new CountRequest();
        request.indices(indices);
        request.query(query);
        if (terminateAfter != null && terminateAfter > 0) {
            request.terminateAfter(terminateAfter);
        }
        request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        return request;
    }

}
