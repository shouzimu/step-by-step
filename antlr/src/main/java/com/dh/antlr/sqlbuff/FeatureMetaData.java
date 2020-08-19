package com.dh.antlr.sqlbuff;

public class FeatureMetaData {
	public static final com.dh.antlr.sqlbuff.FeatureMetaData UNUSED = new com.dh.antlr.sqlbuff.FeatureMetaData(com.dh.antlr.sqlbuff.FeatureType.UNUSED, null, 0);
	public String[] abbrevHeaderRows;
	public com.dh.antlr.sqlbuff.FeatureType type;
	public double mismatchCost;

	public FeatureMetaData(com.dh.antlr.sqlbuff.FeatureMetaData old) {
		this.abbrevHeaderRows = old.abbrevHeaderRows;
		this.type = old.type;
		this.mismatchCost = old.mismatchCost;
	}

	public FeatureMetaData(com.dh.antlr.sqlbuff.FeatureType type, String[] abbrevHeaderRows, int mismatchCost) {
		this.abbrevHeaderRows = abbrevHeaderRows;
		this.mismatchCost = mismatchCost;
		this.type = type;
	}

}
