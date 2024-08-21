package com.example.esutil.elastic.client.model.bucket;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author pw7563
 * @Date 2024/8/21 10:56
 * usage
 */
@Data
@Accessors(chain = true)
public class TermBucket {

    private Object key;

    private long count;

}
