package com.test.es.query;

import java.io.IOException;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import com.study.es.ESClient;

public class FilterQuery {

	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
    public void filterQuery() throws IOException {
        
        SearchRequest sr = new SearchRequest(index).types(type);
        
        SearchSourceBuilder ssb = new SearchSourceBuilder();
       
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.filter(QueryBuilders.termQuery("corpName", "途虎养车"));
        bqb.filter(QueryBuilders.rangeQuery("fee").lte(5));
        
        ssb.query(bqb);
        sr.source(ssb);
        
        SearchResponse sresp = client.search(sr, RequestOptions.DEFAULT);
        
        for (SearchHit hit : sresp.getHits().getHits()) {
            System.out.println( hit.getSourceAsMap() );
        }
    }
	
}
