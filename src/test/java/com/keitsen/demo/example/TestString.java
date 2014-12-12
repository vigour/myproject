package com.keitsen.demo.example;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class TestString {

	@Test
	public void formatString(){
		String s = "11223344";
		if(s.length() <= 6){
			System.out.println(StringUtils.rightPad(s, 6,"0"));
		}else{
			System.out.println(StringUtils.substring(s, 0, 6));
		}
	}
	
	@Test
	public void formatString1(){
		for(int index = 0;index<10000;index++){
	
		       DecimalFormat df = new DecimalFormat("0000");
	
		      System.out.println(df.format(index)); //次id即为四位不重复的流水号
	
	
		}
	}
}
