package com.alibaba.autonews2.parser.article;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.Test;

import com.alibaba.autonews2.format.DateFormatFactory;
import com.alibaba.autonews2.model.Article;
import com.alibaba.autonews2.regex.RegexPatternFactory;

public class MsWordTest {

	@Test
	public void testHelloWorld() throws Exception {
		InputStream is = new FileInputStream(
				"C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx");
		XWPFDocument doc = new XWPFDocument(is);

		Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
		while (iterator.hasNext()) {
			XWPFParagraph next = iterator.next();
			System.out.println(next.getParagraphText());
			System.out.println("--------------------------");
		}
	}

	@Test
	public void testHelloWorld2() throws Exception {
		InputStream is = new FileInputStream(
				"C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx");
		XWPFDocument doc = new XWPFDocument(is);
		List<XWPFParagraph> paragraphs = doc.getParagraphs();
		for (XWPFParagraph paragraph : paragraphs) {
			System.out.println(paragraph.getText());
			System.out.println("---------------");
		}
	}

	@Test
	public void testHelloWorld3() throws Exception {
		InputStream is = new FileInputStream(
				"C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx");
		XWPFDocument doc = new XWPFDocument(is);
		List<XWPFParagraph> paragraphs = doc.getParagraphs();
		for (XWPFParagraph paragraph : paragraphs) {
			if (paragraph.getPictureText().trim().isEmpty()) {
				System.out.println("oo");
			}
		}
	}

	@Test
	public void testHelloWorld4() throws Exception {
		InputStream is = new FileInputStream(
				"C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx");
		XWPFDocument doc = new XWPFDocument(is);
		List<XWPFParagraph> paragraphs = doc.getParagraphs();
		Pattern pubDate = RegexPatternFactory.getPubDate();
		for (XWPFParagraph paragraph : paragraphs) {
			Matcher matcher = pubDate.matcher(paragraph.getParagraphText()
					.trim());
			if (matcher.find()) {
				System.out.println(matcher.group());
			}
		}
	}

	@Test
	public void testParse() throws Exception {
		InputStream is = new FileInputStream(
				"C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx");
		XWPFDocument doc = new XWPFDocument(is);
		List<XWPFParagraph> paragraphs = doc.getParagraphs();
		Pattern pubDate = RegexPatternFactory.getPubDate();

		// 定义一个装配过程中的Article
		Article tmpArticle = null;
		// 定义一个临时队列
		LinkedList<String> queue = new LinkedList<String>();

		for (int i = 0; i < paragraphs.size(); i++) {
			XWPFParagraph paragraph = paragraphs.get(i);
			String text = paragraph.getParagraphText().trim();
			// 如果该段落为空，不作处理返回
			if (text == null || text.isEmpty()) {
				continue;
			}

			Matcher pubDateMatcher = pubDate.matcher(text);
			if (pubDateMatcher.find()) {
				SimpleDateFormat pubDateFormat = DateFormatFactory
						.getPubDateFormat();
				String pubDateStr = pubDateMatcher.group();
				Date date = pubDateFormat.parse(pubDateStr);
				// tmpArticle为null说明这是要解析的第一个article
				if (tmpArticle == null) {
					tmpArticle = new Article();
					tmpArticle.setPublishDate(date);
					tmpArticle.setTitle(queue.pollLast());
					System.out.println(tmpArticle);
				} else {
					// 如果tmpArticle不为null，
					// 则说明之前有一个tmpArticle已经装配了标题和日期，
					// 但该tmpArticle的所有paragraph还未装配，这些paragraph暂存在queue中
				}
			} else {
				queue.add(text);
			}
		}
	}

}
