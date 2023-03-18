package com.zlz.tools.bean;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class InvokeBean {

	String provider;
	String method;
	String params;
}
