package com.test.es.query;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.study.es.ESClient;

public class MatchQuery {
		RestHighLevelClient client = ESClient.getClient();
		String index = "sms-logs-index";
		String type = "sms-logs-type";
		
		@Test
		public void multiMatch() throws IOException {
			System.out.println("----------------------------------------------------multiMatch-------------------------------------------------------------------------");
			SearchRequest sr = new SearchRequest(index).types(type);
			
			SearchSourceBuilder builder = new SearchSourceBuilder();
			builder.query(QueryBuilders.multiMatchQuery("上海", "smsContent","province"));
			sr.source(builder);
			
			SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
			for (SearchHit hit : resp.getHits().getHits()) {
				Map<String,Object> result = hit.getSourceAsMap();
				System.out.println("match----length---" + resp.getHits().getHits().length );
				System.out.println("match--- result----" + result );
			}
			System.out.println("------------------------------------------------------------------------------------------------------------------------------");
		}
		
		@Test
		public void matchBool() throws IOException {
			SearchRequest sr = new SearchRequest(index).types(type);
			
			SearchSourceBuilder builder = new SearchSourceBuilder();
			builder.query(QueryBuilders.matchQuery("smsContent", "滴滴 银行").operator(Operator.OR));
			sr.source(builder);
			
			SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
			for (SearchHit hit : resp.getHits().getHits()) {
				Map<String,Object> result = hit.getSourceAsMap();
				System.out.println("match----length---" + resp.getHits().getHits().length );
				System.out.println("match--- result----" + result );
			}
			System.out.println("------------------------------------------------------------------------------------------------------------------------------");
		}
		
		@Test
		public void match() throws IOException {
			SearchRequest sr = new SearchRequest(index).types(type);
			
			SearchSourceBuilder builder = new SearchSourceBuilder();
			builder.query(QueryBuilders.matchQuery("smsContent", "滴滴生活"));
			sr.source(builder);
			
			SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
			for (SearchHit hit : resp.getHits().getHits()) {
				Map<String,Object> result = hit.getSourceAsMap();
				System.out.println("match-------" + resp.getHits().getHits().length );
				System.out.println("match--- result----" + result );
			}
			System.out.println("------------------------------------------------------------------------------------------------------------------------------");
		}
		
		@Test
		public void matchAll() throws IOException {
			SearchRequest sr = new SearchRequest(index).types(type);
			
			SearchSourceBuilder builder = new SearchSourceBuilder();
			builder.query(QueryBuilders.matchAllQuery());
			builder.size(20);
			sr.source(builder);
			
			SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
			for (SearchHit hit : resp.getHits().getHits()) {
				Map<String,Object> result = hit.getSourceAsMap();
				System.out.println("match_ALL-------" + result );
			}
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------" );
		}
}
