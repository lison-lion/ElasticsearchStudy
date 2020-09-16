package com.test.es.query;

import java.io.IOException;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import com.study.es.ESClient;

public class ScrollDemo {
	RestHighLevelClient client = ESClient.getClient();
	String index = "sms-logs-index";
	String type = "sms-logs-type";
	
	@Test
	public void scrollQuery() throws IOException {
		//创建查询请求
		SearchRequest request = new SearchRequest(index).types(type);
		//设置scroll失效时间
		request.scroll(TimeValue.timeValueMinutes(5L));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		//指定每次显示多少条数据
		builder.size(4);
		//指定排序字段和规则（可选操作）
		builder.sort("fee", SortOrder.DESC);
		//查询全部数据
		builder.query(QueryBuilders.matchAllQuery());
		request.source(builder);
		//获取返回结果
		SearchResponse resp = client.search(request, RequestOptions.DEFAULT);
		//得到scrollID
		String sID = resp.getScrollId();
		
		System.out.println( "--------------首页------------------" );
		
		SearchHit [] hits = resp.getHits().getHits();
		
		for (SearchHit hit : hits) {
			System.out.println( hit.getSourceAsMap() );
		}
		
		while (true) {
			SearchScrollRequest ssreq = new SearchScrollRequest(sID);
			ssreq.scroll(TimeValue.timeValueMinutes(5L));
			SearchResponse scrollresp = client.scroll(ssreq, RequestOptions.DEFAULT);
			SearchHit [] scrollHits = scrollresp.getHits().getHits();
			
			//检查scroll里是否还有数据
			if( scrollHits !=null && scrollHits.length >0 ) {
				System.out.println( "--------------------下一页--------------------" );
				//有则遍历输出
				for (SearchHit hit : scrollHits) {
					System.out.println( hit.getSourceAsMap() );
				}
			}else {
				//无则，跳出循环
				System.out.println( "---------------------结束-----------------------" );
				break;
			}
		}
		
		//清除scroll
		ClearScrollRequest csr = new ClearScrollRequest();
		csr.addScrollId(sID);
		ClearScrollResponse csrpse =  client.clearScroll(csr, RequestOptions.DEFAULT);
		System.out.println( "清除结果-------" + csrpse.isSucceeded() );
	}
}
