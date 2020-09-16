package com.test.es;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import com.study.es.ESClient;

public class TestDemo {

	@Test
	public void test() {
		RestHighLevelClient client =  ESClient.getClient();
		System.out.println( "ok" +client );
	}

}
