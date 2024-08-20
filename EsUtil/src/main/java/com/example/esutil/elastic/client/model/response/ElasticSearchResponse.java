package com.example.esutil.elastic.client.model.response;

import com.example.esutil.elastic.client.model.ElasticDoc;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author pw7563
 * @Date 2024/8/20 15:23
 * usage
 */
@Data
@NoArgsConstructor
public class ElasticSearchResponse {

    private Long total;

    private List<ElasticDoc> docs;

}
