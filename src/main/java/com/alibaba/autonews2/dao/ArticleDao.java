package com.alibaba.autonews2.dao;

import java.util.List;

import com.alibaba.autonews2.model.Article;
import com.alibaba.autonews2.parser.article.ArticleParser;
import com.alibaba.autonews2.parser.article.MsWordArticleParser;

public class ArticleDao {
	private ArticleParser articleParser = new MsWordArticleParser();

	public List<Article> read(String filePath) {
		return articleParser.parseMultiArticle(filePath);
	}
}
