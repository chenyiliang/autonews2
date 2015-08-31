package com.alibaba.autonews2.model;

import java.util.List;

public class Sentence {
	private String sentence;
	private List<String> terms;
	private int type;

	public Sentence() {
	}

	public Sentence(String sentence) {
		this.sentence = sentence;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<String> getTerms() {
		return terms;
	}

	public void setTerms(List<String> terms) {
		this.terms = terms;
	}

	@Override
	public String toString() {
		return "Sentence [sentence=" + sentence + ", terms=" + terms
				+ ", type=" + type + "]";
	}

}
