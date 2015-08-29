package com.alibaba.autonews2.regex;

import java.util.regex.Pattern;

public class RegexPatternFactory {
	/**
	 * EMPTY_LINE匹配空行，即匹配换行符打头。windows换行符为\r\n，linux换行符为\n
	 */
	private static final Pattern EMPTY_LINE = Pattern.compile("^[\n|^\r\n]");

	private static final Pattern PUB_DATE = Pattern
			.compile("[\\d]{4}-[\\d]{2}-[\\d]{2}");

	public static Pattern getEmptyLine() {
		return EMPTY_LINE;
	}

	public static Pattern getPubDate() {
		return PUB_DATE;
	}
}
