package com.alibaba.autonews2.parser.article;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.alibaba.autonews2.model.Article;
import com.alibaba.autonews2.model.Section;

public class MsWordArticleParserTest {

	@Test
	public void testParser() {
		ArticleParser articleParser = new MsWordArticleParser();
		List<Article> list = articleParser
				.parseMultiArticle("C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx");
		Article article = list.get(15);
		System.out.println("Article Title:" + article.getTitle());
		System.out.println("Article Pub Date:" + article.getPublishDate());
		List<Section> sectionList = article.getSectionList();
		for (Section section : sectionList) {
			System.out.println("Section Sub Title:" + section.getSubTitle());
			System.out.println("Section Paragraphs:"
					+ section.getParagraphList());
		}
	}

	@Test
	public void testList() {
		LinkedList<String> list = new LinkedList<String>();
		List<Integer> subTitleIndexs = new ArrayList<Integer>();
		list.add("p0");
		list.add("t1");
		list.add("p1");
		list.add("p2");
		list.add("p3");
		list.add("t2");
		list.add("p4");
		list.add("p5");
		for (int i = 0; i < list.size(); i++) {
			String p = list.get(i);
			if (p.startsWith("t")) {
				subTitleIndexs.add(i);
			}
		}
		System.out.println(subTitleIndexs);

		for (int i = 0; i < subTitleIndexs.size(); i++) {
			if (i == 0) {
				Integer firstIndex = subTitleIndexs.get(i);
				if (firstIndex == 0) {
					System.out.println("firstTitle:" + list.get(0));
					System.out.println("followParagraph:"
							+ list.subList(1, subTitleIndexs.get(2)));
				} else {
					System.out.println("firstTitle:" + null);
					System.out.println("followParagraph:"
							+ list.subList(0, firstIndex));
				}
			} else {
				int subTitleIndex = subTitleIndexs.get(i - 1);
				int end = subTitleIndexs.get(i);
				System.out.println("nextTitle:" + list.get(subTitleIndex));
				System.out.println("followParagraph:"
						+ list.subList(subTitleIndex + 1, end));
				if (i == subTitleIndexs.size() - 1) {
					System.out.println("nextTitle:" + list.get(end));
					System.out.println("followParagraph:"
							+ list.subList(end + 1, list.size()));
				}
			}
		}
	}
}
