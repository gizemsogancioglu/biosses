package semanticSimilaritySystems.core;

import java.util.List;

/**
 * Created by orhan on 10.02.2016.
 */
public class Pair {
    private String sentence1;
    private String sentence2;
    private List<String> preprocessedWordListForSentence1;
    private List<String> preprocessedWordListForSentence2;
    private List<String> bigramFeaturesForSentence1;
    private List<String> bigramFeaturesForSentence2;
    private double groundTruthSimilarityScore;
    private String pairId;
    private double paragraphVecResult;
    private double wordnetMeasure;
    private double umlsMeasureJCN;
    private double umlsMeasureLin;
    private double umlsMeasureCdist;
    private double combinedOntologyMethod;
    private double qGramSimilarityScore;

    public Pair(String sentence1, String sentence2){

        this.sentence1 = sentence1;
        this.sentence2 = sentence2;
    }

    public Pair(String pairId, String sentence1, String sentence2){
        this.pairId = pairId;
        this.sentence1 = sentence1;
        this.sentence2 = sentence2;
    }
    public Pair(){

    }
    public String getSentence1() {
        return sentence1;
    }

    public void setSentence1(String sentence1) {
        this.sentence1 = sentence1;
    }

    public String getSentence2() {
        return sentence2;
    }

    public void setSentence2(String sentence2) {
        this.sentence2 = sentence2;
    }

    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }

    public double getGroundTruthSimilarityScore() {
        return groundTruthSimilarityScore;
    }

    public void setGroundTruthSimilarityScore(double groundTruthSimilarityScore) {
        this.groundTruthSimilarityScore = groundTruthSimilarityScore;
    }

    public double getParagraphVecResult() {
        return paragraphVecResult;
    }

    public void setParagraphVecResult(double paragraphVecResult) {
        this.paragraphVecResult = paragraphVecResult;
    }

    public double getCombinedOntologyMethod() {
        return combinedOntologyMethod;
    }

    public void setCombinedOntologyMethod(double combinedOntologyMethod) {
        this.combinedOntologyMethod = combinedOntologyMethod;
    }

    public double getqGramSimilarityScore() {
        return qGramSimilarityScore;
    }

    public void setqGramSimilarityScore(double qGramSimilarityScore) {
        this.qGramSimilarityScore = qGramSimilarityScore;
    }

    public List<String> getPreprocessedWordListForSentence1() {
        return preprocessedWordListForSentence1;
    }

    public void setPreprocessedWordListForSentence1(List<String> preprocessedWordListForSentence1) {
        this.preprocessedWordListForSentence1 = preprocessedWordListForSentence1;
    }

    public List<String> getPreprocessedWordListForSentence2() {
        return preprocessedWordListForSentence2;
    }

    public void setPreprocessedWordListForSentence2(List<String> preprocessedWordListForSentence2) {
        this.preprocessedWordListForSentence2 = preprocessedWordListForSentence2;
    }

    public List<String> getBigramFeaturesForSentence1() {
        return bigramFeaturesForSentence1;
    }

    public void setBigramFeaturesForSentence1(List<String> bigramFeaturesForSentence1) {
        this.bigramFeaturesForSentence1 = bigramFeaturesForSentence1;
    }

    public List<String> getBigramFeaturesForSentence2() {
        return bigramFeaturesForSentence2;
    }

    public void setBigramFeaturesForSentence2(List<String> bigramFeaturesForSentence2) {
        this.bigramFeaturesForSentence2 = bigramFeaturesForSentence2;
    }
}
