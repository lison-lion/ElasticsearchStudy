package com.test.es.query;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.study.es.ESClient;

public class AggsQuery {
	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
	public void distinctCountQuery() throws IOException {
		SearchRequest requ = new SearchRequest(index).types(type);
		SearchSourceBuilder ssb = new SearchSourceBuilder()
				.aggregation(AggregationBuilders.cardinality("agg").field("province"));
		requ.source(ssb);
		SearchResponse resp = client.search(requ,RequestOptions.DEFAULT);
		Cardinality aggregation = resp.getAggregations().get("agg");
		System.out.println( aggregation.getValue() );
	}
}
