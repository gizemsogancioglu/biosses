package semanticSimilaritySystems.baseline;
import semanticSimilaritySystems.core.SimilarityMeasure;
import stringSimilarityMeasures.CosineSimilarity;
import slib.utils.ex.SLIB_Exception;
import java.util.*;

/**
 * Created by gizem on 01.02.2016.
 */
public class BaselineMethod implements SimilarityMeasure{

    HashSet<String> dictionary;
    public BaselineMethod(HashSet<String> dictionary){
        this.dictionary = dictionary;
    }

    public double getSimilarity(String sentence1, String sentence2) throws SLIB_Exception {
        Vector<Double> vec1 = constructVectorForSentence(sentence1);
        Vector<Double> vec2  = constructVectorForSentence(sentence2);

        CosineSimilarity cosine = new CosineSimilarity(vec1,vec2);
        Double similarityScore = cosine.calculateDistanceAmongVectors();
        return similarityScore;
    }

    public Vector<Double> constructVectorForSentence(String sentence){
        //This method takes an sentence as a parameter.
        //As a result, returns the corresponding
        String[] split = sentence.toLowerCase().split("\\s+");
        Vector<Double> vec = new Vector<Double>();
        HashMap<String, Double> hashOfSentence = new HashMap<String, Double>();

        // Here we save each different word in a sentence and their corresponding 'term frequencies'.
        for(String s: split){
            if(!hashOfSentence.containsKey(s))
                hashOfSentence.put(s, 1.0);

            else{
                double count = hashOfSentence.get(s);
                count++;
                hashOfSentence.put(s, count);
            }
        }

        int index = 0 ;
        for(String s: this.dictionary){
            if(hashOfSentence.containsKey(s))
                vec.add(index, hashOfSentence.get(s));
            else
                vec.add(index, 0.0);

            index++;
        }

        return vec;
    }
}
