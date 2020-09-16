package com.test.es.query;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
//import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.study.es.ESClient;

public class OtherQuery {

	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
	public void deleteByQuery() throws IOException {
		System.out.println("----------------------------------------------------deleteByQuery-------------------------------------------------------------------------");
		DeleteByQueryRequest dbqr = new DeleteByQueryRequest(index).types(type);
		
		dbqr.setQuery(QueryBuilders.rangeQuery("fee").lt(6));
		
		BulkByScrollResponse bulkresp = client.deleteByQuery(dbqr, RequestOptions.DEFAULT);
		System.out.println("match----length---" + bulkresp.toString() );
		
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	@Test
	public void regexpQuery() throws IOException {
		System.out.println("----------------------------------------------------regexpQuery-------------------------------------------------------------------------");
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.regexpQuery("mobile", "199[0-9]{8}"));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		System.out.println("match----length---" + resp.getHits().getHits().length );
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			
			System.out.println("match--- result----" + result );
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	@Test
	public void rangeQuery() throws IOException {
		System.out.println("----------------------------------------------------rangeQuery-------------------------------------------------------------------------");
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.rangeQuery("fee").gte(3).lte(10));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		System.out.println("match----length---" + resp.getHits().getHits().length );
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			
			System.out.println("match--- result----" + result );
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	@Test
	public void wildcardQuery() throws IOException {
		System.out.println("----------------------------------------------------wildcardQuery-------------------------------------------------------------------------");
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.wildcardQuery("corpName", "滴滴*"));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		System.out.println("match----length---" + resp.getHits().getHits().length );
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			
			System.out.println("match--- result----" + result );
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	
	@Test
	public void fuzzyQuery() throws IOException {
		System.out.println("----------------------------------------------------fuzzyQuery-------------------------------------------------------------------------");
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.fuzzyQuery("corpName", "盒马先果").prefixLength(3));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		System.out.println("match----length---" + resp.getHits().getHits().length );
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			
			System.out.println("match--- result----" + result );
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	@Test
	public void prefixQuery() throws IOException {
		System.out.println("----------------------------------------------------prefixQuery-------------------------------------------------------------------------");
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.prefixQuery("corpName", "招商"));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		System.out.println("match----length---" + resp.getHits().getHits().length );
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			
			System.out.println("match--- result----" + result );
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	} 
	
	@Test
	public void queryByIds() throws IOException {
		System.out.println("----------------------------------------------------queryByIds-------------------------------------------------------------------------");
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.idsQuery().addIds("1","2","3","5"));
		sr.source(builder);
		
		SearchResponse resp = client.search(sr, RequestOptions.DEFAULT);
		System.out.println("match----length---" + resp.getHits().getHits().length );
		for (SearchHit hit : resp.getHits().getHits()) {
			Map<String,Object> result = hit.getSourceAsMap();
			
			System.out.println("match--- result----" + result );
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
	
	@Test
	public void findById() throws IOException {
		System.out.println("----------------------------------------------------findById-------------------------------------------------------------------------");
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.idsQuery().addIds("1"));
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
	public void queryID() throws IOException {
		System.out.println("----------------------------------------------------queryID-------------------------------------------------------------------------");
		
		GetRequest gir = new GetRequest(index,type,"1");
		
		GetResponse grsp =  client.get(gir, RequestOptions.DEFAULT);
		
		System.out.println( grsp.getSourceAsMap() );
		
		System.out.println("------------------------------------------------------------------------------------------------------------------------------");
	}
}
