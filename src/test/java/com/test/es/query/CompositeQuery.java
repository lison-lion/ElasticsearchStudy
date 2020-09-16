package com.test.es.query;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.study.es.ESClient;

public class CompositeQuery {
	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
	public void boolQuery() throws IOException {
		
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		BoolQueryBuilder bqb = new BoolQueryBuilder();
		
		bqb.should(QueryBuilders.termQuery("province", "广东"));
		bqb.should(QueryBuilders.termQuery("province", "青海"));
		bqb.mustNot(QueryBuilders.termQuery("operatorId", "2"));
		bqb.must(QueryBuilders.termQuery("corpName", "途虎养车"));
		//bqb.must(QueryBuilders.termQuery("", ""));
		ssb.query(bqb);
		sr.source(ssb);
		
		SearchResponse sresp = client.search(sr, RequestOptions.DEFAULT);
		
		for (SearchHit hit : sresp.getHits().getHits()) {
			System.out.println( hit.getSourceAsMap() );
		}
	}
	
	@Test
	public void boostingQuery() throws IOException {
		
		SearchRequest sr = new SearchRequest(index).types(type);
		
		SearchSourceBuilder ssb = new SearchSourceBuilder();
		
		 BoostingQueryBuilder boostQuery = QueryBuilders.boostingQuery(
				QueryBuilders.matchQuery("smsContent", "滴滴"),
				QueryBuilders.matchQuery("smsContent", "银行")
		).negativeBoost(0.5F);
		 
		 ssb.query(boostQuery);
		 sr.source(ssb);
		
		SearchResponse sresp = client.search(sr, RequestOptions.DEFAULT);
		
		for (SearchHit hit : sresp.getHits().getHits()) {
			System.out.println( hit.getSourceAsMap() );
		}
	}
}
