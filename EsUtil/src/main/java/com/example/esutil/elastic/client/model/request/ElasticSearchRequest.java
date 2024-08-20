package com.example.esutil.elastic.client.model.request;

import lombok.Data;
import org.apache.lucene.util.CollectionUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author pw7563
 * @Date 2024/8/20 14:53
 * usage 请求es的通用参数模板
 */
@Data
public class ElasticSearchRequest {

    private String indices;

    private Integer from;

    private Integer size;

    /**
     * 用于启用或禁用对 track_total_hits 的追踪，特别适用于深度分页时控制查询效率。
     */
    private Boolean trackTotal;

    private List<SortBuilder<?>> sorts = new ArrayList<>(1);

    private QueryBuilder query;

    /**
     * 在查询结果中按某个字段进行去重。常用于在结果中只保留某字段的唯一值（例如按用户 ID 去重）
     */
    private CollapseBuilder collapse;

    public static ElasticSearchRequest create(){
        return new ElasticSearchRequest();
    }

    public ElasticSearchRequest indices(String indices) {
        this.indices = indices;
        return this;
    }

    public ElasticSearchRequest from(Integer from) {
        this.from = from;
        return this;
    }

    public ElasticSearchRequest size(Integer size) {
        this.size = size;
        return this;
    }

    public ElasticSearchRequest trackTotal(Boolean trackTotal) {
        this.trackTotal = trackTotal;
        return this;
    }

    public ElasticSearchRequest addSort(SortBuilder<?> sort, SortBuilder<?>... others) {
        this.sorts.add(sort);
        if(others.length > 0 && others != null){
            this.sorts.addAll(List.of(others));
        }
        return this;
    }

    public ElasticSearchRequest sort(List<SortBuilder<?>> sorts) {
        this.sorts = sorts;
        return this;
    }

    public ElasticSearchRequest sort(String field, SortOrder order) {
        this.sorts.add(SortBuilders.fieldSort(field).order(order));
        return this;
    }

    public ElasticSearchRequest query(QueryBuilder query) {
        this.query = query;
        return this;
    }

    public ElasticSearchRequest collapse(CollapseBuilder collapse) {
        this.collapse = collapse;
        return this;
    }

    public SearchRequest toSearchRequest(){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (from != null) {
            sourceBuilder.from(from);
        }
        if (size != null) {
            sourceBuilder.size(size);
        }
        if (trackTotal != null) {
            sourceBuilder.trackTotalHits(trackTotal);
        }
        if (sorts != null && !sorts.isEmpty()) {
            sorts.forEach(sourceBuilder::sort);
        }
        sourceBuilder.query(query);
        if (collapse != null) {
            sourceBuilder.collapse(collapse);
        }
        SearchRequest request = new SearchRequest();
        request.indices(indices);
        request.source(sourceBuilder);
        request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
        return request;
    }



}
