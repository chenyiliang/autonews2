package com.alibaba.autonews2.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.FileWriterWithEncoding;

import com.alibaba.autonews2.dao.ArticleDao;
import com.alibaba.autonews2.model.Article;
import com.alibaba.autonews2.model.Paragraph;
import com.alibaba.autonews2.model.Section;
import com.alibaba.autonews2.model.Sentence;
import com.alibaba.autonews2.sort.SortUtil;

public class ArticleWordStatisticService {
	private ArticleDao articleDao = new ArticleDao();

	public void exportWordFrequencyCsv(String inpath, String outpath) {
		Map<Object, Integer> map = getWordFrequency(inpath);

		// 数据输出准备
		File file = new File(outpath);
		CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator('\n'); // 每条记录间隔符
		if (file.exists()) {
			file.delete();
		}
		FileWriterWithEncoding writer = null;
		CSVPrinter printer = null;
		try {
			writer = new FileWriterWithEncoding(outpath, "gbk", true);
			printer = new CSVPrinter(writer, format);
		} catch (Exception e) {
			throw new RuntimeException("Csv File output preparing fails", e);
		}

		appendKeyValuesToCsv(printer, map);

		try {
			printer.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<Object, Integer> getWordFrequency(String inpath) {
		List<Article> articles = articleDao.read(inpath);
		Map<Object, Integer> map = new HashMap<Object, Integer>();

		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			List<Section> sections = article.getSectionList();
			for (int j = 0; j < sections.size(); j++) {
				Section section = sections.get(j);
				List<Paragraph> paragraphs = section.getParagraphList();
				for (int k = 0; k < paragraphs.size(); k++) {
					Paragraph paragraph = paragraphs.get(k);
					List<Sentence> sentences = paragraph.getSentenceList();
					for (int l = 0; l < sentences.size(); l++) {
						Sentence sentence = sentences.get(l);
						List<String> terms = sentence.getTerms();
						for (int m = 0; m < terms.size(); m++) {
							String term = terms.get(m);
							Integer integer = map.get(term);
							if (integer == null || integer.intValue() == 0) {
								map.put(term, 1);
							} else {
								map.put(term, map.get(term) + 1);
							}
						}
					}
				}
			}
		}

		LinkedHashMap<Object, Integer> sortedMap = SortUtil
				.sortMapByIntValue(map);
		return sortedMap;
	}

	private void appendKeyValuesToCsv(CSVPrinter printer,
			Map<Object, Integer> map) {
		Set<Entry<Object, Integer>> entrySet = map.entrySet();
		for (Entry<Object, Integer> entry : entrySet) {
			try {
				printer.printRecord(entry.getKey(), entry.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
