package semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod;

import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.*;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

import semanticSimilaritySystems.core.SimilarityMeasure;

import java.util.List;

/**
 * Created by gizem on 31.01.2016.
 */
public class WordNetSimilarity implements SimilarityMeasure {

    private static ILexicalDatabase db = new NictWordNet();

    public static void main(String[] args){

        WordNetSimilarity w = new WordNetSimilarity();
     //   Double score = w.getSimilarity("love", "like");
        Double score = w.calculateWordNetPairScore("i love you", "like");
        System.out.println(score);
    }

    public double calc(String word1, String word2){
        WS4JConfiguration.getInstance().setMFS(true);
        double s = new Path(db).calcRelatednessOfWords(word1, word2);
        return s;
    }

    public double calculateWordNetPairScore(String word1, String word2){

        // word1 = "love"; word2 = "like";
    ///   System.setProperty("wordnet.database.dir", "C:/Users/T082123/Desktop/sses/src/main/resources/dict/");
        //C:\Users\T082123\Desktop\sses\src\main\resources\dict
  //      WordNetDatabase database = WordNetDatabase.getFileInstance();
    //    Synset[] synsets = database.getSynsets(word1);
        //word1 = Stem(word1); word2 = Stem(word2);
        ILexicalDatabase db = new NictWordNet();
        WS4JConfiguration.getInstance().setMFS(true);

        RelatednessCalculator rc = new Path(db);
        List<POS[]> posPairs = rc.getPOSPairs();
        double maxScore = -1D;

        for(POS[] posPair: posPairs) {
            List<Concept> synsets1 = (List<Concept>)db.getAllConcepts(word1, posPair[0].toString());
            List<Concept> synsets2 = (List<Concept>)db.getAllConcepts(word2, posPair[1].toString());
            for(Concept synset1: synsets1) {
                for (Concept synset2: synsets2) {
                    Relatedness relatedness = rc.calcRelatednessOfSynset(synset1, synset2);
                    double score = relatedness.getScore();
                    if (score > maxScore) {
                        maxScore = score;
                    }
                }
            }
        }

        if (maxScore == -1D)
            maxScore = 0.0;

        return maxScore;
    }


    public double getSimilarity(String word1, String word2) {

        return calculateWordNetPairScore(word1, word2);
    }
}