package com.example.esutil.elastic.client.model.param.agg;

import com.example.esutil.elastic.client.model.bucket.TermBucket;
import com.example.esutil.elastic.client.model.response.ElasticAggregationResult;
import lombok.Data;
import lombok.experimental.Accessors;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author pw7563
 * @Date 2024/8/21 10:53
 * usage 桶聚合参数
 */
@Data
@Accessors(chain = true)
public class TermsAggregationParam extends AggregationParam{

    private String name;

    private String field;


    /**
     * 返回的数量为默认数量
     * @return
     */
    @Override
    public AggregationBuilder toAggregationBuilder() {
        return AggregationBuilders.terms(name).field(field);
    }

    @Override
    public ElasticAggregationResult toElasticAggregationResult(Aggregation aggregation) {
        ParsedTerms parsedTerms;
        if(aggregation instanceof ParsedTerms){
            parsedTerms = (ParsedTerms) aggregation;
        }else{
            return null;
        }
        List<TermBucket> collect = parsedTerms.getBuckets().stream()
                .map(x -> new TermBucket()
                        .setKey(x.getKey())
                        .setCount(x.getDocCount()))
                .collect(Collectors.toList());
        return new ElasticAggregationResult().setTermBuckets(collect);
    }
}
