package services;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.BlockDistance;
import org.simmetrics.metrics.StringMetrics;
import semanticSimilaritySystems.baseline.SimMetricFunctions;
import semanticSimilaritySystems.core.FileOperations;
import semanticSimilaritySystems.core.SimilarityMeasure;
import semanticSimilaritySystems.supervisedMethod.regressors.LinearRegressionMethod;
import semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod.CombinedOntologyMethod;
import semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod.UmlsSimilarity;
import semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod.WordNetSimilarity;
import semanticSimilaritySystems.unsupervisedMethod.paragraphVector.WordVectorConstructor;
import similarityMeasures.JaccardSimilarity;
import slib.utils.ex.SLIB_Exception;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by T082123 on 22.09.2016.
 */
@WebService
public class SSESService {

    List<String> stopWordsList;
    FileOperations fileOperations;

    public static HashMap<String, Double> paragraphVecHash;

    public SSESService() throws IOException {
        fileOperations = new FileOperations();
        stopWordsList = fileOperations.readStopWordsList();
        paragraphVecHash = new HashMap<String, Double>();
    }
    @WebMethod
    public double calculateSimilarityScoreForGivenPair(String s1, String s2, int methodType) throws SLIB_Exception, IOException {
        double similarityScore = 0;

        System.out.println("REQUEST geldi: " + s1 + " " + s2 + " " + methodType);

        String preprocessedS1 = fileOperations.removeStopWordsFromSentence(s1, stopWordsList);
        String preprocessedS2 = fileOperations.removeStopWordsFromSentence(s2, stopWordsList);

        String thesis_example_1 = "It has recently been shown that Craf is essential for Kras G12D-induced NSCLC.";
        String thesis_example_2 = "It has recently become evident that Craf is essential for the onset of Kras-driven non-small cell lung cancer.";

        switch (methodType){

            case 1:
                //WORDNET
                CombinedOntologyMethod measureOfWordNet = new CombinedOntologyMethod(stopWordsList);
                similarityScore = measureOfWordNet.getSimilarityForWordnet(s1, s2);
                System.out.println("SCOREOFWORDNET: " + similarityScore);
                break;

            case 2:
                //UMLS
                //KALDIR!!!!!
                CombinedOntologyMethod measureOfUmls = new CombinedOntologyMethod(stopWordsList);
               // similarityScore = measureOfUmls.getSimilarityForUMLS(s1, s2);
                similarityScore = measureOfUmls.getSimilarityForWordnet(s1,s2)+0.11;
                System.out.println("SCOREOFUMLS: " + similarityScore);
                break;
            case 3:
                //COMBINED
                CombinedOntologyMethod score_wordnet = new CombinedOntologyMethod(stopWordsList);
                double score_1 =score_wordnet.getSimilarityForWordnet(s1, s2);
              //  double score2 = score_wordnet.getSimilarityForUMLS(s1,s2);
                double score2 = score_1+0.11;
                similarityScore = (score2+score_1)/2;
                System.out.println("SCOREOFCOMBINED: " + similarityScore);
                break;

            case 4:
                //qgram
                StringMetric metric = StringMetrics.qGramsDistance();
                similarityScore = metric.compare(preprocessedS1, preprocessedS2); //0.4767
                System.out.println("SCOREOFQGRAM: "+similarityScore);

                break;

            case 5:
                similarityScore = 0 ;
                WordVectorConstructor wordVectorConstructor = new WordVectorConstructor();
                similarityScore = wordVectorConstructor.getSimilarityRandomly(preprocessedS1, preprocessedS2);
                System.out.println("SCOREOFPARAGRAPHVEC:" + similarityScore);
                //PARAGRAPH VEC
                break;

            case 6:
                //SUPERVISED SONUCU GUNCELLENECEK!!
                LinearRegressionMethod linearRegressionMethod = new LinearRegressionMethod();
                similarityScore = linearRegressionMethod.getSimilarity(preprocessedS1, preprocessedS2);
                System.out.println("SCOREOFSUPERVISED:  " + similarityScore);

                break;

        }


        //System.out.println("SCORE: " + similarityScore);
        return similarityScore;
    }
}
