package com.alibaba.autonews2.parser.article;

import java.util.List;

import com.alibaba.autonews2.model.Article;

public interface ArticleParser {
	List<Article> parseMultiArticle(String filePath);
}
