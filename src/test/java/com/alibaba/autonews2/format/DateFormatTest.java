package com.alibaba.autonews2.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateFormatTest {

	@Test
	public void testPubDateFormat() throws ParseException {
		SimpleDateFormat pubDateFormat = DateFormatFactory.getPubDateFormat();
		Date date = pubDateFormat.parse("2014-03-05");
		System.out.println(date);
	}
}
