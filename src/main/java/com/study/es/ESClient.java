package com.study.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESClient {
	
	public static RestHighLevelClient getClient() {
		HttpHost hosts = new HttpHost("192.168.139.128", 9200);
		RestClientBuilder builder = RestClient.builder(hosts);
		RestHighLevelClient client = new RestHighLevelClient(builder);
		return client;
	}
}
