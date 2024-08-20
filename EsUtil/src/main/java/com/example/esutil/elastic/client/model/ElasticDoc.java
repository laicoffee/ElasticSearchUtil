package com.example.esutil.elastic.client.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.elasticsearch.search.SearchHit;

/**
 * @Author pw7563
 * @Date 2024/8/20 14:28
 * usage 文档数据模型
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class ElasticDoc {

    private String index;

    private String id;

    private String doc;

    public static ElasticDoc create(){
        return new ElasticDoc();
    }
    public ElasticDoc index(String index) {
        this.index = index;
        return this;
    }

    public ElasticDoc id(String id) {
        this.id = id;
        return this;
    }

    public ElasticDoc doc(String doc) {
        this.doc = doc;
        return this;
    }

    public static ElasticDoc of(SearchHit hit){
        return ElasticDoc.create().index(hit.getIndex()).id(hit.getId()).doc(hit.getSourceAsString());
    }


}
