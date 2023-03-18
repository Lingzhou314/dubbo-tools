package com.zlz.tools.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result<T> {

	int code;
	String msg;
	T data;
	
	public static Result<String> success(){
		return new Result<>(0, null, null);
	}
	
	public static <T> Result<T> success(T data){
		return new Result<>(0, null, data);
	}
	
	public static <T> Result<T> error(){
		return new Result<>(-1, "服务异常", null);
	}
	
	public static <T> Result<T> error(String msg, T data){
		return new Result<>(-1, msg, data);
	}
}
