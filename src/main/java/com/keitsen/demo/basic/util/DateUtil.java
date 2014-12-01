package com.keitsen.demo.basic.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	// 日期格式
	public static final String YYMM_EN = "yyyy-MM";
	
	public static final String YY = "yyyy";

	public static final String YYMMDD_EN = "yyyy-MM-dd";

	public static final String YYMMDDHHMMSS_EN = "yyyy-MM-dd HH:mm:ss";

	public static final String YYMMDD_ZH = "yyyy年MM月dd日";

	public static final String YYMMDDHHMMSS_ZH = "yyyy年MM月dd日 HH时mm分ss秒";
	
	public static String dateToStr(Date date, String format) {
		if(date == null){
			return null;
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static Date strToDate(String str, String format) {
		if(StringUtils.isEmpty(str)){
			return null;
		}
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			date = null;
		}
		return date;
	}


}
