package com.bell.mf.mapper;

import java.util.Calendar;
import java.util.Date;

public enum MapperFieldPostHandlerEnum {
	/** 什么也不处理 */
	original((v, r) -> v),
	/** 16进制转2进制字符串 */
	hex2BinStr((v, r) -> Integer.toBinaryString(Integer.valueOf(v, 16))),
	/** 16进制转10进制整型 */
	hex2DecInt((v, r) -> Integer.valueOf(v, 16)),
	/** 16进制转10进制字符串 */
	hex2DecStr((v, r) -> Integer.valueOf(v, 16).toString()),
	/** 解析为Date对象 */
	datetime(datetimePostHandler());

	private MapperFieldPostHandler postHandler;

	private MapperFieldPostHandlerEnum(MapperFieldPostHandler postHandler) {
		this.postHandler = postHandler;
	}

	public MapperFieldPostHandler getPostHandler() {
		return this.postHandler;
	}

	private static MapperFieldPostHandler datetimePostHandler() {
		return (value, r) -> {
			int year = Integer.valueOf(value.substring(0, 2), 16) + 2000;// 18年
			int month = Integer.valueOf(value.substring(2, 4), 16) - 1;
			int day = Integer.valueOf(value.substring(4, 6), 16);
			int hour = Integer.valueOf(value.substring(6, 8), 16);
			int minute = Integer.valueOf(value.substring(8, 10), 16);
			int second = Integer.valueOf(value.substring(10, 12), 16);
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month, day, hour, minute, second);
			calendar.set(Calendar.MILLISECOND, 0);
			Date time = calendar.getTime();
			return time;
		};
	}
}