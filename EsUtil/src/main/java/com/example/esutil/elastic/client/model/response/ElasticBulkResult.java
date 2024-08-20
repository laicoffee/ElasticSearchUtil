package com.example.esutil.elastic.client.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author pw7563
 * @Date 2024/8/20 15:48
 * usage
 */
@Data
@NoArgsConstructor
public class ElasticBulkResult {

    private Long size;
    private Long total;

}
