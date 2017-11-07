package semanticSimilaritySystems.unsupervisedMethod.paragraphVector;

import com.google.common.io.Resources;
import org.apache.log4j.Logger;
import semanticSimilaritySystems.core.FileOperations;
import semanticSimilaritySystems.core.SimilarityMeasure;
import services.SSESService;
import similarityMeasures.CosineSimilarity;
import slib.utils.ex.SLIB_Exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

/**
 * Created by T082123 on 06.02.2017.
 */
public class WordVectorConstructor implements SimilarityMeasure{

    final static Logger logger = Logger.getLogger(WordVectorConstructor.class);

    static HashMap<String, Vector<Double>> wordvector;
    static {

        try {
            wordvector = loadWordVectors();
        } catch (IOException e) {
            logger.error("word vector could not be loaded....");
            e.printStackTrace();
        }

    }

    private static HashMap<String, Vector<Double>> loadWordVectors() throws IOException {
        HashMap<String, Vector<Double>> wordvector = new HashMap<String, Vector<Double>>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(Resources.getResource("wordVectors/word.txt").getFile()));
        String line;

        while((line = bufferedReader.readLine())!=null){
          //  System.out.println(line);
            String[] split = line.split("\\s+");
            String word = split[0];

            Vector<Double> vector = new Vector<Double>();
            for(int i=1; i< split.length; i++){
                vector.add(Double.valueOf(split[i]));
            }

            if(vector.size() == 200){
                word = FileOperations.replacePunctuations(word);
                wordvector.put(word, vector);
            //    System.out.println(word);
            }

        }
        return wordvector;
    }


    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(Resources.getResource("wordVectors/word.txt").getFile()));
        String line;


        while((line = bufferedReader.readLine())!=null){
            System.out.println(line);
            String[] split = line.split("\\s+");
            String word = split[0];

            Vector<Double> vector = new Vector<Double>();
            for(int i=1; i< split.length; i++){
                vector.add(Double.valueOf(split[i]));
            }

            if(vector.size() == 200){
                System.out.println("KIIIZ TABIIII!!!!!!!!!!!!!!!!!!!!");
                wordvector.put(word, vector);
                System.out.println(word);
            }

        }
    }

    public double getSimilarityRandomly(String sentence1, String sentence2){
        double similarityScoreOfWordVec;

        if(sentence1.equalsIgnoreCase(sentence2)){
            similarityScoreOfWordVec = 1;
        }

        else {
            if (SSESService.paragraphVecHash.containsKey(sentence1.toLowerCase() + "_" + sentence2.toLowerCase()))
                similarityScoreOfWordVec = SSESService.paragraphVecHash.get(sentence1.toLowerCase() + "_" + sentence2.toLowerCase());
            else if (SSESService.paragraphVecHash.containsKey(sentence2.toLowerCase() + "_" + sentence1.toLowerCase()))
                similarityScoreOfWordVec = SSESService.paragraphVecHash.get(sentence2.toLowerCase() + "_" + sentence1.toLowerCase());
            else {
                Random rand = new Random();
                int val = rand.nextInt(50) + 40;
                similarityScoreOfWordVec = (double) val / 100.0;
                SSESService.paragraphVecHash.put(sentence1.toLowerCase() + "_" + sentence2.toLowerCase(), similarityScoreOfWordVec);
            }

        }
        return similarityScoreOfWordVec;
    }


    public double getSimilarity(String sentence1, String sentence2) throws SLIB_Exception, IOException {
        String[] sentence1_split = sentence1.split("\\s+");
        String[] sentence2_split = sentence2.split("\\s+");

        Vector<Double> avgVec1 = new Vector<Double>();
        Vector<Double> avgVec2 = new Vector<Double>();

        Vector<Double> vector1 = new Vector<Double>();
        Vector<Double> vector2 = new Vector<Double>();

        int count1=0, count2=0;
        for(String s1: sentence1_split){
            if(wordvector.containsKey(s1)){
                vector1 = wordvector.get(s1);
            }
            else vector1.removeAllElements();
        }

        CosineSimilarity similarityMeasure = new CosineSimilarity(vector1, vector2);
        double similarityScore = similarityMeasure.calculateDistanceAmongVectors();
        return similarityScore;

    }
}
