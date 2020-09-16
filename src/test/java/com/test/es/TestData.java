package com.test.es;

import java.io.IOException;
import java.util.Date;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.es.ESClient;
import com.study.es.entity.SmsLogs;

public class TestData {
	ObjectMapper mapper = new ObjectMapper();
	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
	public void bulkDelete() throws IOException {
		BulkRequest br = new BulkRequest();
		br.add(new DeleteRequest(index,type,"1"));
		br.add(new DeleteRequest(index,type,"2"));
		br.add(new DeleteRequest(index,type,"3"));
		br.add(new DeleteRequest(index,type,"4"));
		br.add(new DeleteRequest(index,type,"5"));
		br.add(new DeleteRequest(index,type,"6"));
		
		br.add(new DeleteRequest(index,type,"7"));
		br.add(new DeleteRequest(index,type,"8"));
		br.add(new DeleteRequest(index,type,"9"));
		br.add(new DeleteRequest(index,type,"10"));
		br.add(new DeleteRequest(index,type,"11"));
		br.add(new DeleteRequest(index,type,"12"));
		
		client.bulk(br, RequestOptions.DEFAULT);
		
	}
	
	
	@Test
	public void createSmsLogsIndex() throws IOException {
		Settings.Builder builder =   Settings.builder()
				.put("number_of_shards",5)
				.put("number_of_replicas",1);
		
		XContentBuilder mappings =  JsonXContent.contentBuilder();
		
		mappings
				.startObject()
					.startObject("properties")
						.startObject("corpName")
							.field("type","keyword")
						.endObject()
						
						.startObject("createDate")
							.field("type","date")
						.endObject()
						
						.startObject("fee")
							.field("type","long")
						.endObject()
						
						.startObject("ipAddr")
							.field("type","ip")
						.endObject()
						
						.startObject("longCode")
							.field("type","keyword")
						.endObject()
						
						.startObject("mobile")
							.field("type","keyword")
						.endObject()
						
						.startObject("operatorId")
							.field("type","integer")
						.endObject()
					
						.startObject("province")
							.field("type","keyword")
						.endObject()
		
						.startObject("replyTotal")
							.field("type","integer")
						.endObject()
	
						.startObject("sendDate")
							.field("type","date")
						.endObject()

						.startObject("smsContent")
							.field("type","text")
							.field("analyzer","ik_max_word")
						.endObject()
					
						.startObject("state")
							.field("type","integer")
						.endObject()
				
					.endObject()
				.endObject();
		
		CreateIndexRequest request = new CreateIndexRequest(index)
					.settings(builder)
					.mapping(type,mappings);
		
		CreateIndexResponse resp = client.indices().create(request, RequestOptions.DEFAULT);
		
		System.out.println(  "resp----------" + resp.index());
	}
	@Test
	public void addTestData() throws IOException {
		SmsLogs s1 = 
			//new SmsLogs(id, createDate, sendDate, longCode, mobile, corpName, smsContent, state, operatorId, province, ipAddr, replyTotal, fee)
				new SmsLogs("21", new Date(), new Date(),"10690000988", "13700000001", "途虎养车","", 0, 1,"上海", "10.126.2.9", 10, 3);
		
		SmsLogs s2 = 
				new SmsLogs("22", new Date(), new Date(),"10660000988","13700000001", "盒马鲜生","", 0, 1,"北京", "10.126.2.9", 10, 4);
		
		SmsLogs s3 = 
				new SmsLogs("23", new Date(), new Date(),"10660000988", "15800000001", "滴滴打车","【滴滴出行】尊敬的乘客，为改善您的体验，诚邀您参加调研，请点击： https://z.didi.cn/qSqgi4VJGhO6x 退订TD", 0, 1,"上海", "10.126.2.9", 10, 3);
		
		SmsLogs s4 = 
				new SmsLogs("24", new Date(), new Date(),"10690000988", "15900000001", "滴滴打车","", 0, 1,"黑龙江", "10.126.2.9", 10, 4);
		
		SmsLogs s5 = 
				new SmsLogs("25", new Date(), new Date(),"10690000988","13700000001", "招商银行","", 0, 1,"上海", "10.126.2.9", 10, 5);
		
		SmsLogs s6 = 
				new SmsLogs("26", new Date(), new Date(),"10690000988","16600000001", "中国移动","【账单提醒】尊敬的客户，您2020年07月份通信账单信息如下：用户号码：17701339904；本月账户消费：10.20元，其中套餐基本费用:5.00元，套餐外的语音费、上网费、短彩信费等合计:5.20元。详情请点击 http://a.189.cn/JeybrJ 查询。回复TDDXZD退订本短信。【中国移动】", 0, 1,"天津", "10.126.2.8", 10, 5);
		
		SmsLogs s7 = 
				new SmsLogs("27", new Date(), new Date(),"10690000988","13700000001", "招商银行","【招商银行】您已成功注册一网通，可享受招行优质服务。一网通支持多家银行卡，网上支付安全便捷。滴滴出行率先接入，欢迎体验，详询95555。", 0, 1,"黑龙江", "10.126.2.9", 10, 3);
		
		SmsLogs s8 = 
				new SmsLogs("28", new Date(), new Date(),"10690000988","18800000001", "中国移动","", 0, 1,"宁夏", "10.126.2.8", 10, 6);
		
		SmsLogs s9 = 
				new SmsLogs("29", new Date(), new Date(),"10690000988","19900000001", "途虎养车","", 0, 1,"青海", "10.126.2.9", 10,7);
		
		SmsLogs s10 = 
				new SmsLogs("20", new Date(), new Date(),"10690000988","13700000001", "盒马先生","", 0, 1,"广东", "10.126.2.4", 10, 8);
		
		SmsLogs s11 = 
				new SmsLogs("211", new Date(), new Date(),"10690000988","13700000001", "途虎养车","", 0, 1,"广西", "10.126.2.9", 10, 9);
		
		SmsLogs s12 = 
				new SmsLogs("212", new Date(), new Date(),"10690000988", "13700000001", "途虎养车","", 0, 1,"海南", "10.126.2.4", 10, 2);
		
		String j1 = mapper.writeValueAsString(s1);
		String j2 = mapper.writeValueAsString(s2);
		String j3 = mapper.writeValueAsString(s3);
		String j4 = mapper.writeValueAsString(s4);
		String j5 = mapper.writeValueAsString(s5);
		String j6 = mapper.writeValueAsString(s6);
		String j7 = mapper.writeValueAsString(s7);
		String j8 = mapper.writeValueAsString(s8);
		String j9= mapper.writeValueAsString(s9);
		String j10 = mapper.writeValueAsString(s10);
		String j11 = mapper.writeValueAsString(s11);
		String j12 = mapper.writeValueAsString(s12);
		
		BulkRequest br = new BulkRequest();
		br.add(new IndexRequest(index,type,"1").source(j1, XContentType.JSON));
		br.add(new IndexRequest(index,type,"2").source(j2, XContentType.JSON));
		br.add(new IndexRequest(index,type,"3").source(j3, XContentType.JSON));
		
		br.add(new IndexRequest(index,type,"4").source(j4, XContentType.JSON));
		br.add(new IndexRequest(index,type,"5").source(j5, XContentType.JSON));
		br.add(new IndexRequest(index,type,"6").source(j6, XContentType.JSON));
		
		br.add(new IndexRequest(index,type,"7").source(j7, XContentType.JSON));
		br.add(new IndexRequest(index,type,"8").source(j8, XContentType.JSON));
		br.add(new IndexRequest(index,type,"9").source(j9, XContentType.JSON));
		
		br.add(new IndexRequest(index,type,"10").source(j10, XContentType.JSON));
		br.add(new IndexRequest(index,type,"11").source(j11, XContentType.JSON));
		br.add(new IndexRequest(index,type,"12").source(j12, XContentType.JSON));
		
		client.bulk(br, RequestOptions.DEFAULT);
	}

}
