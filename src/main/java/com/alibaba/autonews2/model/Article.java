package com.alibaba.autonews2.model;

import java.util.Date;
import java.util.List;

public class Article {
	private String title;
	private Date publishDate;
	private List<Section> sectionList;

	public Article() {
	}

	public Article(String title, Date publishDate, List<Section> sectionList) {
		this.title = title;
		this.publishDate = publishDate;
		this.sectionList = sectionList;
	}

	public Article(String title, Date publishDate) {
		this.title = title;
		this.publishDate = publishDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public List<Section> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", publishDate=" + publishDate
				+ ", sectionList=" + sectionList + "]";
	}

}
