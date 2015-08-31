package com.alibaba.autonews2.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.junit.Test;

public class CommonsCsvTest {

	@Test
	public void testCsv() throws Exception {
		File file = new File("D:/data/test.csv");
		CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator('\n'); // 每条记录间隔符
		if (file.exists()) {
			file.delete();
		}
		FileWriterWithEncoding writer = new FileWriterWithEncoding("D:/data/test.csv", "utf-8", true);
		CSVPrinter printer = new CSVPrinter(writer, format);
		for (int i = 0; i <1000; i++) {
			printer.printRecord(Arrays.asList("你好，天天向上"));
			printer.printRecord(Arrays.asList(null, "你好"));
			printer.printRecord(Arrays.asList(null, null, "你好"));
			printer.printRecord(Arrays.asList(null, null, null, null, "你好"));
		}
		printer.close();
		writer.close();
	}
}
