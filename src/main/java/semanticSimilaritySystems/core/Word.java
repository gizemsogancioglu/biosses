package semanticSimilaritySystems.core;

/**
 * Created by gizem on 06.03.2016.
 */
public class Word {
    private boolean isInUmls;
    private String word;
    private boolean isStopWord;

    public boolean isInUmls() {
        return isInUmls;
    }

    public void setInUmls(boolean inUmls) {
        isInUmls = inUmls;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isStopWord() {
        return isStopWord;
    }

    public void setStopWord(boolean stopWord) {
        isStopWord = stopWord;
    }


}
