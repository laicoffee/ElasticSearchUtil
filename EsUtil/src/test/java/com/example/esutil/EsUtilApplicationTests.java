package com.example.esutil;

import com.alibaba.fastjson2.JSONObject;
import com.example.esutil.elastic.client.ElasticClient;
import com.example.esutil.elastic.client.config.ESConstans;
import com.example.esutil.elastic.client.model.request.ElasticCountRequest;
import com.example.esutil.elastic.client.model.request.ElasticSearchRequest;
import com.example.esutil.elastic.client.model.response.ElasticCountResult;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.Query;
import java.util.HashMap;

@SpringBootTest
class EsUtilApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private ElasticClient elasticClient;

    @Test
    public void test() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("ruleName", "zhangsan");
        Object index = elasticClient.index(ESConstans.index, null, JSONObject.toJSONString(objectObjectHashMap), false);
        System.out.println(index);
    }

    @Test
    public void 查询id(){
        HashMap param = new HashMap();
//        param.put("id")
        Object o = elasticClient.get(ESConstans.index, "HP9ybpEBIIVCVi6XRM-S");
        System.out.println(o);
    }

    @Test
    public void 查询(){
        ElasticSearchRequest req = new ElasticSearchRequest();
        req.setIndices(ESConstans.index);
        Object search = elasticClient.search(req);
        System.out.println(search);
    }

    @Test
    public void 统计总量(){
        ElasticCountRequest elasticCountRequest = new ElasticCountRequest();
        elasticCountRequest.indices(ESConstans.index);
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        elasticCountRequest.query(matchAllQueryBuilder);
        ElasticCountResult count = elasticClient.count(elasticCountRequest);
        System.out.println(count);
    }

}
