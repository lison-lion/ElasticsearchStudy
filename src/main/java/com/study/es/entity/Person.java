package com.study.es.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	
	
	
	
	
	@JsonIgnore
	private Integer id;
	
	private String name;
	
	private Integer age;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
}
