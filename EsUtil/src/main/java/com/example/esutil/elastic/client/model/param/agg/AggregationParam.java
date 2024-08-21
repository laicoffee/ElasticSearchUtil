package com.example.esutil.elastic.client.model.param.agg;

import com.example.esutil.elastic.client.model.response.ElasticAggregationResult;
import lombok.Data;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;

/**
 * @Author pw7563
 * @Date 2024/8/20 16:56
 * usage 聚合参数的抽象类，并实现好需要的聚合参数，如桶聚合、指标聚合
 */
@Data
public abstract class AggregationParam {


    public static TermsAggregationParam termsAgg(String name, String field) {
        return new TermsAggregationParam().setName(name).setField(field);
    }


    /**
     * 指定聚合的名称
     * @return
     */
    public abstract String getName();

    /**
     * 将聚合参数转换为聚合参数构建器
     * @return
     */
    public abstract AggregationBuilder toAggregationBuilder();

    /**
     * 结果数据格式转换
     * @param aggregation
     * @return
     */
    public abstract ElasticAggregationResult toElasticAggregationResult(Aggregation aggregation);

}
