package com.alibaba.autonews2.model;

import java.util.List;

public class Section {
	private String subTitle;
	private List<Paragraph> paragraphList;

	public Section() {
	}

	public Section(String subTitle, List<Paragraph> paragraphList) {
		this.subTitle = subTitle;
		this.paragraphList = paragraphList;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public List<Paragraph> getParagraphList() {
		return paragraphList;
	}

	public void setParagraphList(List<Paragraph> paragraphList) {
		this.paragraphList = paragraphList;
	}

	@Override
	public String toString() {
		return "Section [subTitle=" + subTitle + ", paragraphList="
				+ paragraphList + "]";
	}
}
