package com.study.es.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsLogs {
	
	
	private String id;//唯一编号
	
	private Date createDate;//创建时间
	
	private Date sendDate;//发送时间
	
	private String longCode;//发送的长号码
	
	private String mobile; //下发手机号
	
	private String corpName;//发送公司名称
	
	private String smsContent;//下发短信内容
	
	private Integer state;// 短信下发状态 0成功1失败
	
	private Integer operatorId;//运营商编号1移动 2联通 3电信
	
	private String province;//省份
	
	private String ipAddr;//下发服务器ip地址
	
	private Integer replyTotal;//短信状态报告返回时长（秒）
	
	private Integer fee;//费用
	
}
