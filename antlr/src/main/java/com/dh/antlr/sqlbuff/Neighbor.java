package com.dh.antlr.sqlbuff;

import java.util.List;

import static com.dh.antlr.sqlbuff.Formatter.getHPosCategoryStr;
import static com.dh.antlr.sqlbuff.Formatter.getWSCategoryStr;

public class Neighbor {
	public Corpus corpus;
	public final double distance;
	public final int corpusVectorIndex; // refers to both X (independent) and Y (dependent/predictor) variables

	public Neighbor(Corpus corpus, double distance, int corpusVectorIndex) {
		this.corpus = corpus;
		this.distance = distance;
		this.corpusVectorIndex = corpusVectorIndex;
	}

	public String toString(FeatureMetaData[] FEATURES, List<Integer> Y) {
		int[] X = corpus.featureVectors.get(corpusVectorIndex);
		InputDocument doc = corpus.documentsPerExemplar.get(corpusVectorIndex);
		String features = com.dh.antlr.sqlbuff.Trainer._toString(FEATURES, doc, X);
		int line = X[com.dh.antlr.sqlbuff.Trainer.INDEX_INFO_LINE];
		String lineText = doc.getLine(line);
		int col = X[com.dh.antlr.sqlbuff.Trainer.INDEX_INFO_CHARPOS];
		// insert a dot right before char position
		if ( lineText!=null ) {
			lineText = lineText.substring(0, col)+'\u00B7'+lineText.substring(col, lineText.length());
		}
		int cat = Y.get(corpusVectorIndex);
		int[] elements = com.dh.antlr.sqlbuff.Trainer.triple(cat);
//		String display = String.format("%d|%d|%d", cat&0xFF, elements[0], elements[1]);
		String wsDisplay = getWSCategoryStr(cat);
		String alignDisplay = getHPosCategoryStr(cat);
		String display = wsDisplay!=null ? wsDisplay : alignDisplay;
		if ( display==null ) display = String.format("%8s","none");
		return String.format("%s (%s,d=%1.3f): %s", features, display, distance, lineText);
	}
}
