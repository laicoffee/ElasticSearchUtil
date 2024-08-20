package com.example.esutil.elastic.client.model.request;

import com.example.esutil.elastic.client.model.param.agg.AggregationParam;
import lombok.Data;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @Author pw7563
 * @Date 2024/8/20 16:51
 * usage
 */
@Data
public class ElasticAggregationRequest {

    private String indices;

    private QueryBuilder query;

    private AggregationParam agg;

    public ElasticAggregationRequest indices(String indices){
        this.indices = indices;
        return this;
    }

    public ElasticAggregationRequest query(QueryBuilder query){
        this.query = query;
        return this;
    }

    public ElasticAggregationRequest agg(AggregationParam agg){
        this.agg = agg;
        return this;
    }

    public SearchRequest toSearchRequest(){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(query);
        sourceBuilder.aggregation(agg.toAggregationBuilder());

        SearchRequest request = new SearchRequest();
        request.indices(indices);
        request.source(sourceBuilder);
        request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        return request;
    }


}
