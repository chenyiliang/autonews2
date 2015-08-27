package com.alibaba.autonews2.parser;

import org.junit.Test;

import com.alibaba.autonews2.parser.file.FileStringParser;
import com.alibaba.autonews2.parser.file.TextFileStringParser;

public class TextToStringParserTest {

	@Test
	public void testExtractFileString() {
		FileStringParser parser = new TextFileStringParser();
		String string = parser.extractFileString("C:/Users/ChenYiliang/Desktop/重华 CPI.txt");
		System.out.println(string);
	}
}
