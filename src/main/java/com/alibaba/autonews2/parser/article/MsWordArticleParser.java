package com.alibaba.autonews2.parser.article;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import com.alibaba.autonews2.format.DateFormatFactory;
import com.alibaba.autonews2.model.Article;
import com.alibaba.autonews2.model.Paragraph;
import com.alibaba.autonews2.model.Section;
import com.alibaba.autonews2.model.Sentence;
import com.alibaba.autonews2.regex.RegexPatternFactory;

public class MsWordArticleParser implements ArticleParser {

	// 匹配如2015-07-09的日期格式
	private Pattern pubDatePtn = RegexPatternFactory.getPubDate();

	// 如2015-07-09的日期格式化工具对象
	private SimpleDateFormat pubDateFmt = DateFormatFactory.getPubDateFormat();

	// 最终返回的文章列表结果
	private List<Article> articleList = new ArrayList<Article>();

	// 中间临时数据 队列 或 栈
	private LinkedList<String> tmpQueue = new LinkedList<String>();

	// 引用的正在装配过程中还未装配完的Article对象
	private Article tmpArticle = null;

	@Override
	public List<Article> parseMultiArticle(String filePath) {
		InputStream is = null;
		XWPFDocument doc = null;
		// 准备docx文件对象
		try {
			is = new FileInputStream(filePath);
			doc = new XWPFDocument(is);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("The file " + filePath
					+ " doesn't find !", e);
		} catch (IOException e) {
			throw new RuntimeException("Creating XWPFDocument fails !", e);
		}

		// 抽取出paragraph列表
		List<XWPFParagraph> paragraphs = doc.getParagraphs();
		// 在遍历paragraph列表的过程中解析并装配成Article对象及其列表
		for (int i = 0; i < paragraphs.size(); i++) {
			XWPFParagraph paragraph = paragraphs.get(i);
			String text = paragraph.getParagraphText().trim();
			// 如果该段落为空，不作任何处理，继续下一轮循环段落处理
			if (text == null || text.isEmpty()) {
				continue;
			}

			// 检查该段落是否为发布时间段落
			Matcher matcher = pubDatePtn.matcher(text);
			if (matcher.find()) {
				String pubDateStr = matcher.group();
				Date pubDate = null;
				try {
					pubDate = pubDateFmt.parse(pubDateStr);
				} catch (ParseException e) {
					throw new RuntimeException("Parse date string "
							+ pubDateStr + " fails", e);
				}
				// tmpArticle为null说明开始解析第一个article
				if (tmpArticle == null) {
					tmpArticle = new Article(parseTitle(tmpQueue.pollLast()),
							pubDate);
				} else {
					// 如果tmpArticle不为null，
					// 则说明之前有一个tmpArticle已经装配了标题和日期，
					// 但该tmpArticle的所有paragraph还未装配，这些paragraph暂存在queue中的0到queue.size()-2
					// 而queue.size()-1是新的article的标题
					Article nextArticle = new Article(
							parseTitle(tmpQueue.pollLast()), pubDate);
					assembleParagraphsToArticle(tmpArticle, tmpQueue);
					// 重置tmpQueue
					tmpQueue.clear();
					articleList.add(tmpArticle);
					tmpArticle = nextArticle;
				}
			} else {
				// 个别文章的所有真正的paragraph粘结成一段paragraph
				// 所以要以换行符\n正确区分paragraph
				String[] split = text.split("\n");
				for (int j = 0; j < split.length; j++) {
					tmpQueue.add(split[j]);
				}
			}
		}
		// 收尾工作还有最后一个Article要装配
		assembleParagraphsToArticle(tmpArticle, tmpQueue);
		articleList.add(tmpArticle);
		return articleList;
	}

	/**
	 * 
	 * @param article
	 * @param tmpQueue
	 * @return
	 * @descriptions 这里传入的article中的title和pubDate已经装配，但paragraphs还未装配
	 */
	private Article assembleParagraphsToArticle(Article article,
			LinkedList<String> tmpQueue) {
		// 首先遍历tmpQueue，找出符合subTitle条件的paragraph，记录下索引号放入subTitleIndexs
		List<Integer> subTitleIndexs = new ArrayList<Integer>();
		for (int i = 0; i < tmpQueue.size(); i++) {
			String text = tmpQueue.get(i);
			// 这里我们认为没有中文句号的paragraph是一个subtitle
			if (!text.contains("。")) {
				subTitleIndexs.add(i);
			}
		}

		// 这个sections填充完后，装配入Article
		List<Section> sections = new ArrayList<Section>();

		if (subTitleIndexs.size() == 0) {
			List<Paragraph> paragraphs = convertParagraphStrsToParagraphObjs(tmpQueue);
			sections.add(new Section(null, paragraphs));
			article.setSectionList(sections);
			tmpQueue.clear();
			return article;
		}

		for (int i = 0; i < subTitleIndexs.size(); i++) {
			if (i == 0) {
				Integer firstIndex = subTitleIndexs.get(i);
				if (firstIndex == 0) {
					String subTitle = tmpQueue.get(0);
					List<Paragraph> paragraphs = convertParagraphStrsToParagraphObjs(tmpQueue
							.subList(1, subTitleIndexs.get(1)));
					sections.add(new Section(subTitle, paragraphs));
				} else {
					List<Paragraph> paragraphs = convertParagraphStrsToParagraphObjs(tmpQueue
							.subList(0, firstIndex));
					sections.add(new Section(null, paragraphs));
				}
			} else {
				int subTitleIndex = subTitleIndexs.get(i - 1);
				int end = subTitleIndexs.get(i);
				String subTitle = tmpQueue.get(subTitleIndex);
				List<Paragraph> paragraphs = convertParagraphStrsToParagraphObjs(tmpQueue
						.subList(subTitleIndex + 1, end));
				sections.add(new Section(subTitle, paragraphs));
			}

			if (i == subTitleIndexs.size() - 1) {
				int end = subTitleIndexs.get(i);
				String subTitle = tmpQueue.get(end);
				List<Paragraph> paragraphs = convertParagraphStrsToParagraphObjs(tmpQueue
						.subList(end + 1, tmpQueue.size()));
				sections.add(new Section(subTitle, paragraphs));
			}
		}

		article.setSectionList(sections);
		tmpQueue.clear();
		return article;
	}

	// 有的标题title和前一篇文章的最会一段粘结成一段paragraph
	// 所以要解析出准确的title，要调用此函数
	private String parseTitle(String rowTitle) {
		String[] split = rowTitle.split("\n");
		if (split.length > 1) {
			// title前面所粘结的上一篇article的paragraph要放入tmpQueue
			for (int i = 0; i < split.length - 1; i++) {
				tmpQueue.add(split[i]);
			}
		}
		return split[split.length - 1];
	}

	private List<Paragraph> convertParagraphStrsToParagraphObjs(
			List<String> paragraphStrs) {
		// 返回的Paragraph列表
		List<Paragraph> paragraphs = new ArrayList<Paragraph>();
		for (int i = 0; i < paragraphStrs.size(); i++) {
			List<Sentence> sentences = new ArrayList<Sentence>();
			String paragraphStr = paragraphStrs.get(i);
			String[] split = paragraphStr.split("。");
			for (int j = 0; j < split.length; j++) {
				if (!split[j].trim().isEmpty()) {
					sentences.add(new Sentence(split[j].trim() + "。"));
				}
			}
			paragraphs.add(new Paragraph(sentences));
		}
		return paragraphs;
	}

}
