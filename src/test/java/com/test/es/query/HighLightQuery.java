package com.test.es.query;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import com.study.es.ESClient;


public class HighLightQuery {

	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
	public void highLightQuery() throws IOException {
		SearchRequest request = new SearchRequest(index).types(type);
		
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.matchQuery("smsContent", "招商"));
		HighlightBuilder hlb = new HighlightBuilder();
		hlb.field("smsContent", 10).preTags("<font color='red'>").postTags("</font>");
		builder.highlighter(hlb);
		request.source(builder);
		
		SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
		
		System.out.println( "length：" + resp.getHits().getHits().length );
		
		for (SearchHit hit : resp.getHits().getHits() ) {
			System.out.println( hit.getHighlightFields().get("smsContent") );
		}
	}
}
