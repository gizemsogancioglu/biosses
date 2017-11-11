package services;

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;
import semanticSimilaritySystems.core.FileOperations;
import semanticSimilaritySystems.supervisedMethod.regressors.LinearRegressionMethod;
import semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod.CombinedOntologyMethod;
import semanticSimilaritySystems.unsupervisedMethod.paragraphVector.WordVectorConstructor;
import slib.utils.ex.SLIB_Exception;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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
        System.out.println("REQUEST has been received for: " + s1 + " " + s2 + " " + methodType);

        String preprocessedS1 = fileOperations.removeStopWordsFromSentence(s1, stopWordsList);
        String preprocessedS2 = fileOperations.removeStopWordsFromSentence(s2, stopWordsList);

        String example_1 = "It has recently been shown that Craf is essential for Kras G12D-induced NSCLC.";
        String example_2 = "It has recently become evident that Craf is essential for the onset of Kras-driven non-small cell lung cancer.";

        switch (methodType){
            case 1:
                //WordNet-based similarity was selected
                CombinedOntologyMethod measureOfWordNet = new CombinedOntologyMethod(stopWordsList);
                similarityScore = measureOfWordNet.getSimilarityForWordnet(s1, s2);
                System.out.println("SCOREOFWORDNET: " + similarityScore);
                break;

            case 2:
                //UMLS-based similarity option was selected
                CombinedOntologyMethod measureOfUmls = new CombinedOntologyMethod(stopWordsList);
                similarityScore = measureOfUmls.getSimilarityForUMLS(s1, s2);
                System.out.println("SCOREOFUMLS: " + similarityScore);
                break;
            case 3:
                //COMBINED ontology based similarity option was selected
                CombinedOntologyMethod score_wordnet = new CombinedOntologyMethod(stopWordsList);
                double score_1 =score_wordnet.getSimilarityForWordnet(s1, s2);
                double score2 = score_wordnet.getSimilarityForUMLS(s1,s2);
                similarityScore = (score2+score_1)/2;
                System.out.println("SCOREOFCOMBINED: " + similarityScore);
                break;

            case 4:
                //qgram string similarity option was selected.
                StringMetric metric = StringMetrics.qGramsDistance();
                similarityScore = metric.compare(preprocessedS1, preprocessedS2); //0.4767
                System.out.println("SCOREOFQGRAM: "+similarityScore);
                break;

            case 5:
                //paragraph vector model based similarity was selected as an option.
                similarityScore = 0 ;
                WordVectorConstructor wordVectorConstructor = new WordVectorConstructor();
                //    similarityScore = wordVectorConstructor.getSimilarityRandomly(preprocessedS1, preprocessedS2);
                //     System.out.println("SCOREOFPARAGRAPHVEC:" + similarityScore);
                //PARAGRAPH VEC
                break;

            case 6:
                //TODO: needs modification
                //Supervised Semantic Similarity was selected from the options list.
                LinearRegressionMethod linearRegressionMethod = new LinearRegressionMethod();
                similarityScore = linearRegressionMethod.getSimilarity(preprocessedS1, preprocessedS2);
                System.out.println("SCOREOFSUPERVISED:  " + similarityScore);
                break;
        }
        return similarityScore;
    }
}
