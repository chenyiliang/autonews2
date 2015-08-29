package com.alibaba.autonews2.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.alibaba.autonews2.parser.file.FileStringParser;
import com.alibaba.autonews2.parser.file.TextFileStringParser;

public class RegexPatternTest {
	@Test
	public void testEmptyLine() {
		FileStringParser parser = new TextFileStringParser();
		String string = parser
				.extractFileString("C:/Users/ChenYiliang/Desktop/重华 CPI.txt");
		String string2 = string.replaceAll("(\r\n)$", "**");
		System.out.println(string);
	}

	@Test
	public void testPubDate() {
		Pattern pubDate = RegexPatternFactory.getPubDate();
		Matcher matcher = pubDate.matcher("1991-10-05");
		System.out.println(matcher.matches());
	}
}
