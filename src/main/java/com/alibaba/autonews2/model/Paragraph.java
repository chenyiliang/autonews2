package com.alibaba.autonews2.model;

import java.util.List;

public class Paragraph {
	private List<Sentence> sentenceList;

	public Paragraph() {
	}

	public Paragraph(List<Sentence> sentenceList) {
		this.sentenceList = sentenceList;
	}

	public List<Sentence> getSentenceList() {
		return sentenceList;
	}

	public void setSentenceList(List<Sentence> sentenceList) {
		this.sentenceList = sentenceList;
	}

	@Override
	public String toString() {
		return "Paragraph [sentenceList=" + sentenceList + "]";
	}

}
