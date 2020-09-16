package com.test.es.query;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.study.es.ESClient;

public class TermQuery {
	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
	public void term() throws IOException {
		SearchRequest sr = new SearchRequest(index);
		sr.types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.from(0);
		builder.size(5);
		builder.query(QueryBuilders.termQuery("province", "北京"));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			System.out.println( result );
		}
	}
	
	@Test
	public void terms() throws IOException {
		SearchRequest sr = new SearchRequest(index);
		sr.types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.termsQuery("province", "北京","上海"));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			System.out.println("terms-------" + result );
		}
	}
	
}
