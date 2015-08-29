package com.alibaba.autonews2.app;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.alibaba.autonews2.dao.ArticleDao;
import com.alibaba.autonews2.model.Article;
import com.alibaba.autonews2.model.Section;

public class AutoNewsApp {

	private static final Pattern numPattern = Pattern.compile("[\\d]+");

	private static void printArticle(Article article) {
		System.out.println("Article Title:" + article.getTitle());
		System.out.println("Article Pub Date:" + article.getPublishDate());
		List<Section> sectionList = article.getSectionList();
		for (Section section : sectionList) {
			System.out.println("Section Sub Title:" + section.getSubTitle());
			System.out.println("Section Paragraphs:"
					+ section.getParagraphList());
		}
	}

	public static void main(String[] args) {
		String filePath = "C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx";
		ArticleDao dao = new ArticleDao();
		List<Article> list = dao.read(filePath);
		if (list.size() < 1) {
			return;
		}
		while (true) {
			System.err.println("总共读入了" + list.size() + "篇文章，请输入序号1~"
					+ list.size() + "来选择输出的文章！");
			System.err.println("PS:输入exit退出程序！");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine().trim();
			if ("exit".equalsIgnoreCase(input)) {
				scanner.close();
				System.exit(0);
			} else if (numPattern.matcher(input).matches()) {
				int articleIndex = Integer.valueOf(input) - 1;
				if (articleIndex > 0 && articleIndex < list.size()) {
					printArticle(list.get(Integer.valueOf(input) - 1));
				}
			}
		}

	}
}
