package com.test.es;

import java.io.IOException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import com.study.es.ESClient;

public class IndexDemo {
	RestHighLevelClient client = ESClient.getClient();
	String index = "person";
	String type = "man";
	
	@Test
	public void createIndex() throws IOException {
		Settings.Builder builder =   Settings.builder()
				.put("number_of_shards",3)
				.put("number_of_replicas",1);
		
		XContentBuilder mappings =  JsonXContent.contentBuilder();
		
		mappings
				.startObject()
					.startObject("properties")
						.startObject("name")
							.field("type","text")
						.endObject()
						
						.startObject("age")
							.field("type","integer")
						.endObject()
						
						.startObject("birthday")
							.field("type","date")
							.field("format","yyyy-MM-dd")
						.endObject()
					.endObject()
				.endObject();
		
		CreateIndexRequest request = new CreateIndexRequest(index)
					.settings(builder)
					.mapping(type,mappings);
		
		CreateIndexResponse resp = client.indices().create(request, RequestOptions.DEFAULT);
		
		System.out.println(  "resp----------" + resp);
	}
	
	@Test
	public void existsIndex() throws IOException {
		GetIndexRequest requ = new GetIndexRequest();
		requ.indices(index);
		boolean result = client.indices().exists(requ, RequestOptions.DEFAULT);
		System.out.println("查询结果" +  result );
	}
	
	@Test
	public void deleteIndex() throws IOException {
//		DeleteIndexRequest re = new DeleteIndexRequest().indices(index);
//		 AcknowledgedResponse ar =  client.indices().delete(re, RequestOptions.DEFAULT);
//		 
//		 System.out.println( " 删除结果-------  " + ar.isAcknowledged() );
	}
	
}
