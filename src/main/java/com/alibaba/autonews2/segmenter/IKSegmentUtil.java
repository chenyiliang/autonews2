package com.alibaba.autonews2.segmenter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class IKSegmentUtil {
	public static List<String> split(String sentence) {
		List<String> terms = new ArrayList<String>();
		Reader input = new StringReader(sentence);
		// 智能分词关闭（对分词的精度影响很大）
		IKSegmenter iks = new IKSegmenter(input, true);
		Lexeme lexeme = null;

		try {
			while ((lexeme = iks.next()) != null) {
				terms.add(lexeme.getLexemeText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return terms;
	}
}
