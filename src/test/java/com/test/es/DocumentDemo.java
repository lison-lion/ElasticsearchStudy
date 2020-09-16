package com.test.es;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.es.ESClient;
import com.study.es.entity.Person;

public class DocumentDemo {

	RestHighLevelClient client = ESClient.getClient();
	String index = "person";
	String type = "man";
	ObjectMapper om = new ObjectMapper();
	
	@Test
	public void bulkDelete() throws IOException {
		BulkRequest br = new BulkRequest();
		br.add(new DeleteRequest(index,type,"1"));
		br.add(new DeleteRequest(index,type,"2"));
		BulkResponse brse = client.bulk(br, RequestOptions.DEFAULT);
		
		System.out.println("批量删除---------"  + brse.hasFailures() );
	}
	
	//批量添加
	@Test
	public void bulkCreate() throws IOException {
		Person p1 = new Person(1,"张三",23,new Date());
		Person p2 = new Person(1,"李四",25,new Date());
		Person p3 = new Person(1,"王五",29,new Date());
		
		String j1 = om.writeValueAsString(p1);
		String j2 = om.writeValueAsString(p2);
		String j3 = om.writeValueAsString(p3);
		
		BulkRequest br = new BulkRequest();
		br.add(new IndexRequest(index,type,"1").source(j1, XContentType.JSON));
		br.add(new IndexRequest(index,type,"2").source(j2, XContentType.JSON));
		br.add(new IndexRequest(index,type,"3").source(j3, XContentType.JSON));
		
		BulkResponse bre = client.bulk(br, RequestOptions.DEFAULT);
		System.out.println( "批量添加结果----------"  + bre.buildFailureMessage());
		
		
		
	}
	
	@Test
	public void create() throws IOException {
		Person per = new Person(1, "佳佳", 25, new Date());
		String person = om.writeValueAsString(per);
		
		IndexRequest ir = new IndexRequest(index,type,"1");
		ir.source(person, XContentType.JSON);
		
		IndexResponse ire =   client.index(ir, RequestOptions.DEFAULT);
		System.out.println(  "插入结果-------" +  ire.getResult() );
		
		}
	
	@Test
	public void update() throws IOException {
		Map<String,String> map = new HashMap<String, String>();
		map.put("name", "李佳佳");
		
		UpdateRequest request = new UpdateRequest(index,type,"1");
		request.doc(map);
		
		UpdateResponse resp =  client.update(request, RequestOptions.DEFAULT);
		System.out.println( "修改结果---------" + resp.getResult() );
	}
	
	@Test
	public void del() throws IOException {
			DeleteRequest dir = new DeleteRequest(index, type, "1");
			DeleteResponse dr =  client.delete(dir, RequestOptions.DEFAULT);
			
			System.out.println(  "删除结果---------- " + dr.getResult() );
	}
}
