package com.example.esutil.elastic.client.model.response;

import com.example.esutil.elastic.client.model.bucket.TermBucket;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author pw7563
 * @Date 2024/8/20 17:10
 * usage 定义聚合查询结果的返回类型
 */
@Data
@Accessors(chain = true)
public class ElasticAggregationResult {

    private List<TermBucket> termBuckets;

}
