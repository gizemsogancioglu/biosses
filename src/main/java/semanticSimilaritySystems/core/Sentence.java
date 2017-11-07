package semanticSimilaritySystems.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by gizem on 06.03.2016.
 */
public class Sentence {
    private LinkedList<Word> words;
    private List<String> stringWords;

    public LinkedList<Word> getWords() {
        return words;
    }

    public void setWords(LinkedList<Word> words) {
        this.words = words;
    }

    public List<String> getStringWords() {
        return stringWords;
    }

    public void setStringWords(List<String> stringWords) {
        this.stringWords = stringWords;
    }
}
