package com.dh.antlr.sqlbuff.validation;

import org.antlr.v4.runtime.misc.MurmurHash;
import com.dh.antlr.sqlbuff.FeatureMetaData;

import java.util.Arrays;


public class FeatureVectorAsObject {
	public final int[] features;
	public final FeatureMetaData[] featureMetaData;

	public FeatureVectorAsObject(int[] features, FeatureMetaData[] featureMetaData) {
		this.features = features;
		this.featureMetaData = featureMetaData;
	}

	@Override
	public int hashCode() {
		int hash = MurmurHash.initialize();
		int n = 0;
		for (int i = 0; i<features.length-3; i++) { // don't include INFO
			if ( featureMetaData!=null && featureMetaData[i]==FeatureMetaData.UNUSED ) continue;
			n++;
			int feature = features[i];
			hash = MurmurHash.update(hash, feature);
		}
		return MurmurHash.finish(hash, n);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		else if ( !(obj instanceof com.dh.antlr.sqlbuff.validation.FeatureVectorAsObject) ) {
			return false;
		}
		com.dh.antlr.sqlbuff.validation.FeatureVectorAsObject other = (com.dh.antlr.sqlbuff.validation.FeatureVectorAsObject) obj;
		for (int i = 0; i<features.length-3; i++) { // don't include INFO
			if ( featureMetaData!=null && featureMetaData[i]==FeatureMetaData.UNUSED ) continue;
			if ( features[i]!=other.features[i] ) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return Arrays.toString(features);
	}
}
