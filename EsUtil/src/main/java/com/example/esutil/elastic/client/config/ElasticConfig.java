package com.example.esutil.elastic.client.config;

import com.example.esutil.elastic.client.ElasticClient;
import com.example.esutil.elastic.client.ElasticClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author pw7563
 * @Date 2024/8/20 11:28
 * usage
 */
@Configuration
public class ElasticConfig {

    @Bean
    public ElasticClientConfig elasticClientConfig(){
        ElasticClientConfig elasticClientConfig = new ElasticClientConfig();
        return elasticClientConfig;
    }

    @Bean
    public ElasticClient elasticClient(@Autowired ElasticClientConfig elasticClientConfig){
        return new ElasticClient(elasticClientConfig);
    }

}
