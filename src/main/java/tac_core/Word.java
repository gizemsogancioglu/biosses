/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tac_core;

/**
 *
 * @author gizem
 */
public class Word {
    
    private String word;
    private String processedWord;
    private String baseWord;
    private String chunkTag;
    private String posTag;
    private String NETag;
    private String stemmedWord;

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @return the baseWord
     */
    public String getBaseWord() {
        return baseWord;
    }

    /**
     * @param baseWord the baseWord to set
     */
    public void setBaseWord(String baseWord) {
        this.baseWord = baseWord;
    }

    /**
     * @return the chunkTag
     */
    public String getChunkTag() {
        return chunkTag;
    }

    /**
     * @param chunkTag the chunkTag to set
     */
    public void setChunkTag(String chunkTag) {
        this.chunkTag = chunkTag;
    }

    /**
     * @return the posTag
     */
    public String getPosTag() {
        return posTag;
    }

    /**
     * @param posTag the posTag to set
     */
    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }

    /**
     * @return the NETag
     */
    public String getNETag() {
        return NETag;
    }

    /**
     * @param NETag the NETag to set
     */
    public void setNETag(String NETag) {
        this.NETag = NETag;
    }

    /**
     * @return the stemmedWord
     */
    public String getStemmedWord() {
        return stemmedWord;
    }

    /**
     * @param stemmedWord the stemmedWord to set
     */
    public void setStemmedWord(String stemmedWord) {
        this.stemmedWord = stemmedWord;
    }

    /**
     * @return the processedWord
     */
    public String getProcessedWord() {
        return processedWord;
    }

    /**
     * @param processedWord the processedWord to set
     */
    public void setProcessedWord(String processedWord) {
        this.processedWord = processedWord;
    }
    
    
}
