package com.example.esutil.elastic.client;

import lombok.Data;

/**
 * @Author: zhouying
 * @Date: 2023/7/21 0021
 * @Description:
 */
@Data
public class ElasticClientConfig {

    /**
     * 使用的协议
     */
    private String schema = "http";

    /**
     * 集群地址 (ip:port), 多个逗号分隔
     */
    private String host = "127.0.0.1:9200";

    /**
     * 请求超时时间（毫秒）
     */
    private int requestTimeout = 15000;

    /**
     * 连接超时时间(毫秒)
     */
    private int connectTimeout = 1000;

    /**
     * 连接超时时间(毫秒)
     */
    private int socketTimeout = 30000;

    /**
     * 获取连接的超时时间(毫秒)
     */
    private int connectionRequestTimeout = 500;

    /**
     * 最大连接数
     */
    private int maxConnectNum = 300;

    /**
     * 最大路由连接数
     */
    private int maxConnectPerRoute = 100;

}