package tac_core;
/*
Project     :  Biomedd Sum Shared Task
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  02.09.2014
*/

import java.util.ArrayList;

/**
 *
 * @author gizem
 */
public class Citation {
    
    private ArrayList<String> citationText = new ArrayList<String>();
    private String processed_citation_text;
    private String processed_reference_text;
    private String topicID;
    private String citanceNumber;
    private String referenceArticle;
    private String citingArticle;
    private Offset citationOffset;
    private String citationMarker;
    private Offset citationMarkerOffset;
    private ArrayList<ArrayList<Offset>> referenceOffset = new ArrayList<ArrayList<Offset>>();
    private ArrayList<Offset> retreivedReferenceOffset= new ArrayList<Offset>();
    private ArrayList<ArrayList<Word>> referenceWordList = new ArrayList<ArrayList<Word>>();
    private ArrayList<ArrayList<String>> referenceText  = new ArrayList<ArrayList<String>>();
    private ArrayList<FacetEnum> facet = new ArrayList<FacetEnum>();
    private FacetEnum maxProbableFacet;
    private ArrayList<Word> wordList = new ArrayList<Word>();
    private int[] featureLabel;
    private String system_matched_reference_text;
    private int paragraphVecNumber;
    private int reference_paraprahVecNum;

    /**
     * @return the topicID
     */
    public String getTopicID() {
        return topicID;
    }

    /**
     * @param topicID the topicID to set
     */
    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    /**
     * @return the citanceNumber
     */
    public String getCitanceNumber() {
        return citanceNumber;
    }

    /**
     * @param citanceNumber the citanceNumber to set
     */
    public void setCitanceNumber(String citanceNumber) {
        this.citanceNumber = citanceNumber;
    }

    /**
     * @return the referenceArticle
     */
    public String getReferenceArticle() {
        return referenceArticle;
    }

    /**
     * @param referenceArticle the referenceArticle to set
     */
    public void setReferenceArticle(String referenceArticle) {
        this.referenceArticle = referenceArticle;
    }

    /**
     * @return the citingArticle
     */
    public String getCitingArticle() {
        return citingArticle;
    }

    /**
     * @param citingArticle the citingArticle to set
     */
    public void setCitingArticle(String citingArticle) {
        this.citingArticle = citingArticle;
    }

   
    /**
     * @return the citationMarker
     */
    public String getCitationMarker() {
        return citationMarker;
    }

    /**
     * @param citationMarker the citationMarker to set
     */
    public void setCitationMarker(String citationMarker) {
        this.citationMarker = citationMarker;
    }

    

    /**
     * @return the citationOffset
     */
    public Offset getCitationOffset() {
        return citationOffset;
    }

    /**
     * @param citationOffset the citationOffset to set
     */
    public void setCitationOffset(Offset citationOffset) {
        this.citationOffset = citationOffset;
    }

 
    /**
     * @return the citationMarkerOffset
     */
    public Offset getCitationMarkerOffset() {
        return citationMarkerOffset;
    }

    /**
     * @param citationMarkerOffset the citationMarkerOffset to set
     */
    public void setCitationMarkerOffset(Offset citationMarkerOffset) {
        this.citationMarkerOffset = citationMarkerOffset;
    }

    
    /**
     * @return the wordList
     */
    public ArrayList<Word> getWordList() {
        return wordList;
    }

    /**
     * @param wordList the wordList to set
     */
    public void setWordList(ArrayList<Word> wordList) {
        this.wordList = wordList;
    }

    
    public int[] getFeatureLabel() {
        return featureLabel;
    }


    /**
     * @return the referenceOffset
     */
    public ArrayList<ArrayList<Offset>> getReferenceOffset() {
        return referenceOffset;
    }

    /**
     * @param referenceOffset the referenceOffset to set
     */
    public void setReferenceOffset(ArrayList<ArrayList<Offset>> referenceOffset) {
        this.referenceOffset = referenceOffset;
    }

    
    /**
     * @return the facet
     */
    public ArrayList<FacetEnum> getFacet() {
        return facet;
    }

    /**
     * @param facet the facet to set
     */
    public void setFacet(ArrayList<FacetEnum> facet) {
        this.facet = facet;
    }

    /**
     * @return the maxProbableFacet
     */
    public FacetEnum getMaxProbableFacet() {
        return maxProbableFacet;
    }

    /**
     * @param maxProbableFacet the maxProbableFacet to set
     */
    public void setMaxProbableFacet(FacetEnum maxProbableFacet) {
        this.maxProbableFacet = maxProbableFacet;
    }


    /**
     * @return the system_matched_reference_text
     */
    public String getSystem_matched_reference_text() {
        return system_matched_reference_text;
    }

    /**
     * @param system_matched_reference_text the system_matched_reference_text to set
     */
    public void setSystem_matched_reference_text(String system_matched_reference_text) {
        this.system_matched_reference_text = system_matched_reference_text;
    }

    /**
     * @return the processed_citation_text
     */
    public String getProcessed_citation_text() {
        return processed_citation_text;
    }

    /**
     * @param processed_citation_text the processed_citation_text to set
     */
    public void setProcessed_citation_text(String processed_citation_text) {
        this.processed_citation_text = processed_citation_text;
    }

    /**
     * @return the citationText
     */
    public ArrayList<String> getCitationText() {
        return citationText;
    }

    /**
     * @param citationText the citationText to set
     */
    public void setCitationText(ArrayList<String> citationText) {
        this.citationText = citationText;
    }

    /**
     * @return the referenceWordList
     */
    public ArrayList<ArrayList<Word>> getReferenceWordList() {
        return referenceWordList;
    }

    /**
     * @param referenceWordList the referenceWordList to set
     */
    public void setReferenceWordList(ArrayList<ArrayList<Word>> referenceWordList) {
        this.referenceWordList = referenceWordList;
    }

    /**
     * @return the retreivedReferenceOffset
     */
    public ArrayList<Offset> getRetreivedReferenceOffset() {
        return retreivedReferenceOffset;
    }

    /**
     * @param retreivedReferenceOffset the retreivedReferenceOffset to set
     */
    public void setRetreivedReferenceOffset(ArrayList<Offset> retreivedReferenceOffset) {
        this.retreivedReferenceOffset = retreivedReferenceOffset;
    }

    /**
     * @return the paragraphVecNumber
     */
    public int getParagraphVecNumber() {
        return paragraphVecNumber;
    }

    /**
     * @param paragraphVecNumber the paragraphVecNumber to set
     */
    public void setParagraphVecNumber(int paragraphVecNumber) {
        this.paragraphVecNumber = paragraphVecNumber;
    }

    /**
     * @return the processed_reference_text
     */
    public String getProcessed_reference_text() {
        return processed_reference_text;
    }

    /**
     * @param processed_reference_text the processed_reference_text to set
     */
    public void setProcessed_reference_text(String processed_reference_text) {
        this.processed_reference_text = processed_reference_text;
    }

    /**
     * @return the referenceText
     */
    public ArrayList<ArrayList<String>> getReferenceText() {
        return referenceText;
    }

    /**
     * @param referenceText the referenceText to set
     */
    public void setReferenceText(ArrayList<ArrayList<String>> referenceText) {
        this.referenceText = referenceText;
    }

    /**
     * @return the reference_paraprahVecNum
     */
    public int getReference_paraprahVecNum() {
        return reference_paraprahVecNum;
    }

    /**
     * @param reference_paraprahVecNum the reference_paraprahVecNum to set
     */
    public void setReference_paraprahVecNum(int reference_paraprahVecNum) {
        this.reference_paraprahVecNum = reference_paraprahVecNum;
    }


}
