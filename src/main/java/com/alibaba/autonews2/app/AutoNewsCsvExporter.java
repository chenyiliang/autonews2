package com.alibaba.autonews2.app;

import com.alibaba.autonews2.service.ArticleCsvExportService;

public class AutoNewsCsvExporter {
	public static void main(String[] args) {
		String inpath = "C:/Users/ChenYiliang/Documents/My WangWang/重华 CPI.docx";
		String outpath = "D:/data/articles.csv";
		ArticleCsvExportService exportService = new ArticleCsvExportService();
		exportService.exportArticlesToCsv(inpath, outpath);
	}
}
