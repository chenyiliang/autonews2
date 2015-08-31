package com.alibaba.autonews2.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.FileWriterWithEncoding;

import com.alibaba.autonews2.dao.ArticleDao;
import com.alibaba.autonews2.model.Article;
import com.alibaba.autonews2.model.Paragraph;
import com.alibaba.autonews2.model.Section;
import com.alibaba.autonews2.model.Sentence;

public class ArticleCsvExportService {
	private ArticleDao articleDao = new ArticleDao();

	public void exportArticlesToCsv(String inpath, String outpath) {
		// 数据读入
		List<Article> articles = articleDao.read(inpath);
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

		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			List<List<String>> recordsList = oneArticleToRecordList(article);
			appendRecordsListToCsv(printer, recordsList);
		}

		// try {
		// printer.printRecord(Arrays.asList("你好", "子案件"));
		// printer.printRecord(Arrays.asList(null, "子案件"));
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		try {
			printer.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void appendRecordsListToCsv(CSVPrinter printer,
			List<List<String>> recordsList) {
		for (int i = 0; i < recordsList.size(); i++) {
			List<String> record = recordsList.get(i);
			try {
				printer.printRecord(record);
				System.out.println(record);
			} catch (IOException e) {
				throw new RuntimeException("Writing to csv file fails", e);
			}
		}
	}

	private List<List<String>> oneArticleToRecordList(Article article) {
		// 整张csv表的数据
		List<List<String>> recordsList = new ArrayList<List<String>>();

		String title = article.getTitle();
		String date = article.getPublishDate().toString();
		oneRecordToList(recordsList, 0, title, date);// 写入Article.title,Article.date

		List<Section> sections = article.getSectionList();
		for (int i = 0; i < sections.size(); i++) {
			Section section = sections.get(i);
			String subTitle = section.getSubTitle();
			oneRecordToList(recordsList, 2, subTitle);// 写入Section.subTitle

			List<Paragraph> paragraphs = section.getParagraphList();
			for (int j = 0; j < paragraphs.size(); j++) {
				Paragraph paragraph = paragraphs.get(j);
				oneRecordToList(recordsList, 3, String.valueOf(j));// 写入Paragraph.index
				List<Sentence> sentences = paragraph.getSentenceList();
				for (int k = 0; k < sentences.size(); k++) {
					Sentence sentence = sentences.get(k);
					String sentenceStr = sentence.getSentence();
					oneRecordToList(recordsList, 4, sentenceStr);// 写入Section.subTitle
				}
			}
		}

		return recordsList;
	}

	private void oneRecordToList(List<List<String>> recordsList,
			int preColumnOffset, String... args) {
		recordsList.add(generateOneRecord(preColumnOffset, args));
	}

	private List<String> generateOneRecord(int preColumnOffset, String... args) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg != null) {
				list.add(arg);
			} else {
				list.add("NULL");
			}
		}
		for (int i = 0; i < preColumnOffset; i++) {
			list.add(0, null);
		}
		return Arrays.asList(list.toArray(new String[list.size()]));
	}
}
