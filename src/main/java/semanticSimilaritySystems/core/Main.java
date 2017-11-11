package semanticSimilaritySystems.core;
import com.google.common.io.Resources;
import semanticSimilaritySystems.baseline.BaselineMethod;
import semanticSimilaritySystems.baseline.SimMetricFunctions;
import semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod.CombinedOntologyMethod;
import semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod.WordNetSimilarity;
import slib.utils.ex.SLIB_Exception;
import java.io.*;
import java.util.*;

/**
 * Created by gizem on 07.02.2016.
 */
public class Main {


    public static void calculateSimilarityScoreAmongSentencesUsingUnsupervisedMethods(List<Pair> pairList, FileOperations fileOperations) throws SLIB_Exception, IOException {

        List<String> stopWordsList = fileOperations.readStopWordsList();

        /****************************BASELINE**********************************************/
        SimilarityMeasure measure = new BaselineMethod(fileOperations.constructDictionary(pairList,stopWordsList));
//          for(Pair currentPair: pairList){
//
//          double similarityScore =measure.getSimilarity(currentPair.getSentence1(), currentPair.getSentence2());
//         System.out.println(similarityScore);
//         }

        /**********************************************************************************/


        /****************************SIMMETRICS***************************************************/

       measure = new SimMetricFunctions();
  /*      for(Pair currentPair: pairList){
            String preprocessedS1 = fileOperations.removeStopWordsFromSentence(currentPair.getSentence1(), stopWordsList);
            String preprocessedS2 = fileOperations.removeStopWordsFromSentence(currentPair.getSentence2(), stopWordsList);

            double similarityScore =measure.getSimilarity(preprocessedS1, preprocessedS2);
            //System.out.println("Sentence 1: " + preprocessedS1);
           // System.out.println("Sentence 2: " + preprocessedS2);
             System.out.println(similarityScore);
        }*/

        /**********************************************************************************/




        /********************************PARAGRAPH VECTOR**********************************/

        //   measure = new ParagraphVector(new ParagraphVectorModel("paragraphVector/sentence_vectors.txt"));
//        for(Pair currentPair: pairList){
//       //     double similarityScore =measure.getSimilarity(currentPair.getPairId(), currentPair.getPairId());
//     //       System.out.println(similarityScore);
//        }
        /**********************************************************************************/


        /***********************************LSA********************************************/

//        measure = new LsaDocumentSimilarity();
//        for(Pair currentPair: pairList){
//            double similarityScore =measure.getSimilarity(currentPair.getSentence1(), currentPair.getSentence2());
//       //     System.out.println(similarityScore);
//        }
        /**********************************************************************************/

        WordNetSimilarity measure5 = new WordNetSimilarity();
        double sims = measure5.getSimilarity("love","love");
        System.out.println(sims);
        /**************************************COMBINED METHOD**************************************/

       /* CombinedOntologyMethod measure1 = new CombinedOntologyMethod(stopWordsList);
         for(Pair currentPair: pairList){
            double similarityScore =measure1.getSimilarity(currentPair.getSentence1(), currentPair.getSentence2());
            System.out.println(similarityScore);
            //break;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("pair_score_by_combined_method.txt")));


        HashMap<String,Double> pair_scores = measure1.getPair_score();
        Iterator<String> it = pair_scores.keySet().iterator();
        while (it.hasNext()){
            String pairs = it.next();
            Double sim = pair_scores.get(pairs);
            writer.write(pairs + " " + sim);
            writer.newLine();
        }
        writer.close();
*/
//        /**********************************************************************************/

    }

    public static void main(String[] args) throws SLIB_Exception, IOException {
        LinkedList<Pair> pairList;
        FileOperations operations = new FileOperations();
        pairList = operations.readPairsFromFile("sentencePairsData/pairs.txt");

        calculateSimilarityScoreAmongSentencesUsingUnsupervisedMethods(pairList, operations);
//        FeatureExtractor featureExtractor = new FeatureExtractor(pairList);
//       // featureExtractor.createArffFileFromInstances();
//        featureExtractor.createSVMFileFormatInstances();
//

    }
}