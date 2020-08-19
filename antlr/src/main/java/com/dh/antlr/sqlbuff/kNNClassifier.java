package com.dh.antlr.sqlbuff;

import org.antlr.v4.runtime.misc.Pair;
import com.dh.antlr.sqlbuff.misc.HashBag;
import com.dh.antlr.sqlbuff.misc.MutableDouble;
import com.dh.antlr.sqlbuff.validation.FeatureVectorAsObject;

import java.util.*;

import static com.dh.antlr.sqlbuff.Trainer.*;

/*
 It would appear the context information provides the strongest evidence for
 injecting a new line or not.  The column number and even the width of the next
 statement or expression does not help and in fact probably confuses the
 issue. Looking at the histograms of both column and earliest ancestor
 with, I find that they overlap almost always. The sum of the two
 elements also overlapped heavily. It's possible that using the two
 values as coordinates would yield separability but it didn't look
 like it from a scan of the data.

 Using just context information provides amazingly polarized
 decisions. All but a few were 100% for or against. The closest
 decision was 11 against and 18 for injecting a newline at context
 where 'return'his current token:

     ')' '{' 23 'return', statement 49 Identifier

 There were k (29) exact context matches, but 62% of the time a new
 line was used. It was this case	that I looked at for column or with
 information as the distinguishing characteristic, but it didn't seem
 to help.  I bumped that to k=201 for all ST4 source (like 41000 records)
 and I get 60 against, 141 for a newline (70%).

 We can try more context but weight the significance of misses close to
 current token more heavily than tokens farther away.
 */

/** A kNN (k-Nearest Neighbor) classifier */
public class kNNClassifier {
	protected final com.dh.antlr.sqlbuff.Corpus corpus;
	protected final com.dh.antlr.sqlbuff.FeatureMetaData[] FEATURES;
	protected List<Integer> Y;
	protected final int maxDistanceCount;

	public boolean dumpVotes = false;

	public Map<FeatureVectorAsObject,Integer> classifyCache = new HashMap<>();
	public static int nClassifyCalls=0;
	public static int nClassifyCacheHits=0;

	public Map<FeatureVectorAsObject, com.dh.antlr.sqlbuff.Neighbor[]> neighborCache = new HashMap<>();
	public static int nNNCalls=0;
	public static int nNNCacheHits=0;

	public kNNClassifier(com.dh.antlr.sqlbuff.Corpus corpus, com.dh.antlr.sqlbuff.FeatureMetaData[] FEATURES, List<Integer> Y) {
		this.corpus = corpus;
		this.FEATURES = FEATURES;
		assert FEATURES.length <= com.dh.antlr.sqlbuff.Trainer.NUM_FEATURES;
		int n = 0;
		for (com.dh.antlr.sqlbuff.FeatureMetaData FEATURE : FEATURES) {
			n += FEATURE.mismatchCost;
		}
		maxDistanceCount = n;
		this.Y = Y;
	}

	public void resetCache() {
		classifyCache.clear();
		neighborCache.clear();
		nClassifyCacheHits = 0;
		nClassifyCalls=0;
		nClassifyCacheHits=0;
		nNNCalls=0;
		nNNCacheHits=0;
	}

	public int classify(int k, int[] unknown, double distanceThreshold) {
		FeatureVectorAsObject key = new FeatureVectorAsObject(unknown, FEATURES);
		Integer catI = classifyCache.get(key);
		nClassifyCalls++;
		if ( catI!=null ) {
			nClassifyCacheHits++;
			return catI;
		}
		com.dh.antlr.sqlbuff.Neighbor[] kNN = kNN(unknown, k, distanceThreshold);
		Map<Integer,MutableDouble> similarities = getCategoryToSimilarityMap(kNN, k, Y);
		int cat = getCategoryWithMaxValue(similarities);

		if ( cat==-1 ) {
			// try with less strict match threshold to get some indication of alignment
			kNN = kNN(unknown, k, MAX_CONTEXT_DIFF_THRESHOLD2);
			similarities = getCategoryToSimilarityMap(kNN, k, Y);
			cat = getCategoryWithMaxValue(similarities);
		}

		classifyCache.put(key, cat);
		return cat;
	}

	public static int getCategoryWithMostVotes(HashBag<Integer> votes) {
		int max = Integer.MIN_VALUE;
		int catWithMostVotes = 0;
		for (Integer category : votes.keySet()) {
			if ( votes.get(category)>max ) {
				max = votes.get(category);
				catWithMostVotes = category;
			}
		}

		return catWithMostVotes;
	}

	public HashBag<Integer> votes(int k, int[] unknown, List<Integer> Y, double distanceThreshold) {
		com.dh.antlr.sqlbuff.Neighbor[] kNN = kNN(unknown, k, distanceThreshold);
		return getVotesBag(kNN, k, unknown, Y);
	}

	public HashBag<Integer> getVotesBag(com.dh.antlr.sqlbuff.Neighbor[] kNN, int k, int[] unknown, List<Integer> Y) {
		HashBag<Integer> votes = new HashBag<>();
		for (int i = 0; i<k && i<kNN.length; i++) {
			votes.add(Y.get(kNN[i].corpusVectorIndex));
		}
		if ( dumpVotes && kNN.length>0 ) {
			System.out.print(com.dh.antlr.sqlbuff.Trainer.featureNameHeader(FEATURES));
			com.dh.antlr.sqlbuff.InputDocument firstDoc = corpus.documentsPerExemplar.get(kNN[0].corpusVectorIndex); // pick any neighbor to get parser
			System.out.println(com.dh.antlr.sqlbuff.Trainer._toString(FEATURES, firstDoc, unknown)+"->"+votes);
			kNN = Arrays.copyOfRange(kNN, 0, Math.min(k, kNN.length));
			StringBuilder buf = new StringBuilder();
			for (com.dh.antlr.sqlbuff.Neighbor n : kNN) {
				buf.append(n.toString(FEATURES, Y));
				buf.append("\n");
			}
			System.out.println(buf);
		}
		return votes;
	}

	// get category similarity (1.0-distance) so we can weight votes. Just add up similarity.
	// I.e., weight votes with similarity 1 (distances of 0) more than votes with lower similarity
	// If we have 2 votes of distance 0.2 and 1 vote of distance 0, it means
	// we have 2 votes of similarity .8 and 1 of similarity of 1, 1.6 vs 6.
	// The votes still outweigh the similarity in this case. For a tie, however,
	// the weights will matter.
	public Map<Integer,MutableDouble> getCategoryToSimilarityMap(com.dh.antlr.sqlbuff.Neighbor[] kNN, int k, List<Integer> Y) {
		Map<Integer,MutableDouble> catSimilarities = new HashMap<>();
		for (int i = 0; i<k && i<kNN.length; i++) {
			int y = Y.get(kNN[i].corpusVectorIndex);
			MutableDouble d = catSimilarities.get(y);
			if ( d==null ) {
				d = new MutableDouble(0.0);
				catSimilarities.put(y, d);
			}
			double emphasizedDistance = Math.pow(kNN[i].distance, 1.0/3); // cube root
			d.add(1.0 - emphasizedDistance);
		}
		return catSimilarities;
	}

	public int getCategoryWithMaxValue(Map<Integer,MutableDouble> catSimilarities) {
		double max = Integer.MIN_VALUE;
		int catWithMaxSimilarity = -1;
		for (Integer category : catSimilarities.keySet()) {
			MutableDouble mutableDouble = catSimilarities.get(category);
			if ( mutableDouble.d>max ) {
				max = mutableDouble.d;
				catWithMaxSimilarity = category;
			}
		}

		return catWithMaxSimilarity;
	}

	public String getPredictionAnalysis(com.dh.antlr.sqlbuff.InputDocument doc, int k, int[] unknown, List<Integer> Y, double distanceThreshold) {
		FeatureVectorAsObject key = new FeatureVectorAsObject(unknown, FEATURES);
		com.dh.antlr.sqlbuff.Neighbor[] kNN = neighborCache.get(key);
		nNNCalls++;
		if ( kNN==null ) {
			kNN = kNN(unknown, k, distanceThreshold);
			neighborCache.put(key, kNN);
		}
		else {
			nNNCacheHits++;
		}
		Map<Integer, MutableDouble> similarities = getCategoryToSimilarityMap(kNN, k, Y);
		int cat = getCategoryWithMaxValue(similarities);
		if (cat == -1) {
			// try with less strict match threshold to get some indication of alignment
			kNN = kNN(unknown, k, MAX_CONTEXT_DIFF_THRESHOLD2);
			similarities = getCategoryToSimilarityMap(kNN, k, Y);
			cat = getCategoryWithMaxValue(similarities);
		}

		String displayCat;
		int c = cat&0xFF;
		if ( c==CAT_INJECT_NL||c==CAT_INJECT_WS ) {
			displayCat = com.dh.antlr.sqlbuff.Formatter.getWSCategoryStr(cat);
		}
		else {
			displayCat = com.dh.antlr.sqlbuff.Formatter.getHPosCategoryStr(cat);
		}
		displayCat = displayCat!=null ? displayCat : "none";

		StringBuilder buf = new StringBuilder();
		buf.append(com.dh.antlr.sqlbuff.Trainer.featureNameHeader(FEATURES));
		buf.append(com.dh.antlr.sqlbuff.Trainer._toString(FEATURES, doc, unknown)+"->"+similarities+" predicts "+displayCat);
		buf.append("\n");
		if ( kNN.length>0 ) {
			kNN = Arrays.copyOfRange(kNN, 0, Math.min(k, kNN.length));
			for (com.dh.antlr.sqlbuff.Neighbor n : kNN) {
				buf.append(n.toString(FEATURES, Y));
				buf.append("\n");
			}
		}
		return buf.toString();
	}

	public com.dh.antlr.sqlbuff.Neighbor[] kNN(int[] unknown, int k, double distanceThreshold) {
		com.dh.antlr.sqlbuff.Neighbor[] distances = distances(unknown, k, distanceThreshold);
		Arrays.sort(distances,
		            (com.dh.antlr.sqlbuff.Neighbor o1, com.dh.antlr.sqlbuff.Neighbor o2) -> Double.compare(o1.distance, o2.distance));
		return Arrays.copyOfRange(distances, 0, Math.min(k, distances.length));
	}

	public com.dh.antlr.sqlbuff.Neighbor[] distances(int[] unknown, int k, double distanceThreshold) {
		int curTokenRuleIndex = unknown[com.dh.antlr.sqlbuff.Trainer.INDEX_PREV_EARLIEST_RIGHT_ANCESTOR];
		int prevTokenRuleIndex = unknown[com.dh.antlr.sqlbuff.Trainer.INDEX_EARLIEST_LEFT_ANCESTOR];
		int pr = com.dh.antlr.sqlbuff.Trainer.unrulealt(prevTokenRuleIndex)[0];
		int cr = com.dh.antlr.sqlbuff.Trainer.unrulealt(curTokenRuleIndex)[0];

		List<Integer> vectorIndexesMatchingContext = null;

		// look for exact match and take result even if < k results.  If we have exact matches they always win let's say
		if ( FEATURES==FEATURES_INJECT_WS ) {
			vectorIndexesMatchingContext = corpus.wsFeaturesToExemplarIndexes.get(new FeatureVectorAsObject(unknown, FEATURES));
		}
		else if ( FEATURES==FEATURES_HPOS ) {
			vectorIndexesMatchingContext = corpus.hposFeaturesToExemplarIndexes.get(new FeatureVectorAsObject(unknown, FEATURES));
		}
		// else might be specialized feature set for testing so ignore these caches in that case

		if ( FEATURES==FEATURES_INJECT_WS && // can't use this cache if we are testing out different feature sets
			(vectorIndexesMatchingContext==null || vectorIndexesMatchingContext.size()<=3) ) // must have at 4 or more dist=0.0 for WS else we search wider
		{
			// ok, not exact. look for match with prev and current rule index
			Pair<Integer, Integer> key = new Pair<>(pr, cr);
			vectorIndexesMatchingContext = corpus.curAndPrevTokenRuleIndexToExemplarIndexes.get(key);
		}
		if ( FEATURES==FEATURES_HPOS &&
			(vectorIndexesMatchingContext==null || vectorIndexesMatchingContext.size()<k) )
		{
			// ok, not exact. look for match with prev and current rule index
			Pair<Integer, Integer> key = new Pair<>(pr, cr);
			vectorIndexesMatchingContext = corpus.curAndPrevTokenRuleIndexToExemplarIndexes.get(key);
		}

		if ( distanceThreshold==MAX_CONTEXT_DIFF_THRESHOLD2 ) { // couldn't find anything, open it all up.
			vectorIndexesMatchingContext = null;
		}
		List<com.dh.antlr.sqlbuff.Neighbor> distances = new ArrayList<>();
		if ( vectorIndexesMatchingContext==null ) {
			// no matching contexts for this feature, must rely on full training set
			int n = corpus.featureVectors.size(); // num training samples
			int num0 = 0; // how many 0-distance elements have we seen? If k we can stop!
			for (int i = 0; i<n; i++) {
				int[] x = corpus.featureVectors.get(i);
				double d = distance(x, unknown);
				if ( d<=distanceThreshold ) {
					com.dh.antlr.sqlbuff.Neighbor neighbor = new com.dh.antlr.sqlbuff.Neighbor(corpus, d, i);
					distances.add(neighbor);
					if ( d==0.0 ) {
						num0++;
						if ( num0==k ) break;
					}
				}
			}
		}
		else {
			int num0 = 0; // how many 0-distance elements have we seen? If k we can stop!
			for (Integer vectorIndex : vectorIndexesMatchingContext) {
				int[] x = corpus.featureVectors.get(vectorIndex);
				double d = distance(x, unknown);
				if ( d<=distanceThreshold ) {
					com.dh.antlr.sqlbuff.Neighbor neighbor = new com.dh.antlr.sqlbuff.Neighbor(corpus, d, vectorIndex);
					distances.add(neighbor);
					if ( d==0.0 ) {
						num0++;
						if ( num0==k ) break;
					}
				}
			}
		}
		return distances.toArray(new com.dh.antlr.sqlbuff.Neighbor[distances.size()]);
	}

	/**
	 * Compute distance as a probability of match, based
	 * solely on context information.
	 * <p>
	 * Ratio of num differences / num total context positions.
	 */
	public double distance(int[] A, int[] B) {
//		return ((float)Tool.L0_Distance(categorical, A, B))/num_categorical;
		double d = com.dh.antlr.sqlbuff.Tool.weightedL0_Distance(FEATURES, A, B);
		return d/maxDistanceCount;
	}
}
