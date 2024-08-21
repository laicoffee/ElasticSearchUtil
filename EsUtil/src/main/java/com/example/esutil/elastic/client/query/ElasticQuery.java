package com.example.esutil.elastic.client.query;

import com.example.esutil.elastic.client.model.param.agg.AggregationParam;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;

import java.util.List;

/**
 * @Author pw7563
 * @Date 2024/8/20 10:10
 * usage 设置ElasticSearch查询参数接口，指定每个参数必须实现的方法，方便参数转换
 */
public interface ElasticQuery {

    /**
     * 返回当前索引名称
     * @return
     */
    String toElasticIndices();

    /**
     * 指定排序字段
     * @return
     */
    List<SortBuilder<?>> toElasticSorts();

    /**
     * 创建查询条件
     * @return
     */
    QueryBuilder toElasticQueryBuilder();

    /**
     * 创建聚合参数
     * @return
     */
    AggregationParam toAggregationParam();

}
