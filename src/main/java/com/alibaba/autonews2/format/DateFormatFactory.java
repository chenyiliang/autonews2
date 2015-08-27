package com.alibaba.autonews2.format;

import java.text.SimpleDateFormat;

public class DateFormatFactory {
	private static final String PUB_DATE_FMT = "yyyy-MM-dd";

	public static SimpleDateFormat getPubDateFormat() {
		return new SimpleDateFormat(PUB_DATE_FMT);
	}
}
