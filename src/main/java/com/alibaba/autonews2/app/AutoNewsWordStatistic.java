package com.alibaba.autonews2.app;

import com.alibaba.autonews2.service.ArticleWordStatisticService;

public class AutoNewsWordStatistic {

	public static void main(String[] args) {
		String inpath = "C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx";
		String outpath = "D:/data/wordfrequency.csv";
		ArticleWordStatisticService service = new ArticleWordStatisticService();
		service.exportWordFrequencyCsv(inpath, outpath);
	}

}
