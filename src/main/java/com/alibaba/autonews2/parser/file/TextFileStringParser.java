package com.alibaba.autonews2.parser.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TextFileStringParser implements FileStringParser {

	@Override
	public String extractFileString(String filePath) {
		File file = new File(filePath);
		String content = null;
		if (file.isFile()) {
			try {
				// 这里有个问题，如何判断文本文件编码，可能是utf-8，也可能是gbk，编码设置错，读出来就乱码
				content = FileUtils.readFileToString(file, "gbk");
			} catch (IOException e) {
				throw new RuntimeException("Read Text File To String Fails!", e);
			}
		} else {
			throw new RuntimeException("File " + filePath + " doesn't exists!");
		}
		return content;
	}

}
