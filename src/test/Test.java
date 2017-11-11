import com.google.common.io.Resources;
import edu.ucla.sspace.similarity.PearsonCorrelation;
import edu.ucla.sspace.similarity.SpearmanRankCorrelation;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;
import slib.utils.ex.SLIB_Exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by gizem on 12.02.2016.
 */
public class Test {

    public static double calculateCorrelation(final DoubleVector xArray, final DoubleVector yArray) throws IllegalArgumentException {
        SpearmanRankCorrelation spearmanCorr = new SpearmanRankCorrelation();
        //KendallsTau kendalCor = new KendallsTau();
        PearsonCorrelation pearsonCorr = new PearsonCorrelation();
        double result = pearsonCorr.sim(xArray,yArray);

        return result;
    }
    public static DoubleVector readCorrelationFiles(String filePath) throws IOException {
        BufferedReader buffer1 = new BufferedReader(new FileReader(new File(Resources.getResource(filePath).getFile())));
        DoubleVector xArr = new DenseVector(100);

        String line;
        int i = 0;
        while ((line=buffer1.readLine())!=null){
            xArr.add(i, Double.valueOf(line));
            i++;
        }
        return xArr;
    }

    public static void evaluateBaselineResults(DoubleVector groundTruthMean) throws IOException {
        DoubleVector cosine = readCorrelationFiles("correlationResult/baselineResults/cosineResult.txt");
        DoubleVector jaccard = readCorrelationFiles("correlationResult/baselineResults/jaccardResult.txt");
        DoubleVector blockDistance = readCorrelationFiles("correlationResult/baselineResults/blockDistanceResult.txt");
        DoubleVector levenshtein = readCorrelationFiles("correlationResult/baselineResults/levenshteinResult.txt");
        DoubleVector longestCommonSubstring = readCorrelationFiles("correlationResult/baselineResults/longestCommonSubstringResult.txt");
        DoubleVector longestCommonSubsequence = readCorrelationFiles("correlationResult/baselineResults/longestCommonSubsequenceResult.txt");
        DoubleVector needLemanWunch = readCorrelationFiles("correlationResult/baselineResults/needLemanWunchResult.txt");
        DoubleVector jaroWinkler = readCorrelationFiles("correlationResult/baselineResults/jaroWinkler.txt");
        DoubleVector overlapCoefficient = readCorrelationFiles("correlationResult/baselineResults/overlapCoefficientResult.txt");
        DoubleVector smithWaterman = readCorrelationFiles("correlationResult/baselineResults/smithWatermanResult.txt");
        DoubleVector qGrams = readCorrelationFiles("correlationResult/baselineResults/qGramsDistanceResult.txt");
        DoubleVector simonWhite = readCorrelationFiles("correlationResult/baselineResults/simonWhite.txt");
        DoubleVector mongeElkan = readCorrelationFiles("correlationResult/baselineResults/mongeElkan.txt");

        DoubleVector qramSW = readCorrelationFiles("correlationResult/baselineResults/SW/qgramSW.txt");
        DoubleVector cosineSW = readCorrelationFiles("correlationResult/baselineResults/SW/cosineSW.txt");
        DoubleVector blockSW = readCorrelationFiles("correlationResult/baselineResults/SW/blockSW.txt");
        DoubleVector overlapSW  =readCorrelationFiles("correlationResult/baselineResults/SW/overlapSW.txt");
        DoubleVector smithSW = readCorrelationFiles("correlationResult/baselineResults/SW/smithWatermanSW.txt");
        DoubleVector jaccardSW = readCorrelationFiles("correlationResult/baselineResults/SW/jaccardSW.txt");
        DoubleVector wunchSW = readCorrelationFiles("correlationResult/baselineResults/SW/mongeElkanSW.txt");
        DoubleVector longestCommonSubseqSW = readCorrelationFiles("correlationResult/baselineResults/SW/longestCommonSubseqSW.txt");
        DoubleVector longestCommonSubstringSW = readCorrelationFiles("correlationResult/baselineResults/SW/longestCommonSubstringSW.txt");
        DoubleVector simonSW = readCorrelationFiles("correlationResult/baselineResults/SW/simonWhiteSW.txt");
        DoubleVector levenshteinSW = readCorrelationFiles("correlationResult/baselineResults/SW/levenshteinSW.txt");

        DoubleVector jaroWinklerSW = readCorrelationFiles("correlationResult/baselineResults/SW/jaroWinklerSW.txt");
        DoubleVector needLemanWunchSW = readCorrelationFiles("correlationResult/baselineResults/SW/needlemanSW.txt");
        double correlation = calculateCorrelation(cosine, groundTruthMean);
       // System.out.println("Correlation result between BASELINE-COSINE SIMILARIY and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(cosineSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-COSINE SIMILARIY SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(jaccard, groundTruthMean);
       // System.out.println("Correlation result between BASELINE-JACCARD and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(jaccardSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-JACCARD SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(blockDistance, groundTruthMean);
        //System.out.println("Correlation result between BASELINE-BLOCK DISTANCE and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(blockSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-BLOCK DISTANCE SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(levenshtein, groundTruthMean);
        //System.out.println("Correlation result between BASELINE-LEVENSHTEIN and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(levenshteinSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-LEVENSHTEIN SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(longestCommonSubstring, groundTruthMean);
       // System.out.println("Correlation result between BASELINE-LONGEST COMMON SUBSTRING and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(longestCommonSubstringSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-LONGEST COMMON SUBSTRING SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(longestCommonSubsequence, groundTruthMean);
        //System.out.println("Correlation result between BASELINE-LONGEST COMMON SUBSEQUENCE and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(longestCommonSubseqSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-LONGEST COMMON SUBSEQUENCE SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(needLemanWunch, groundTruthMean);
       // System.out.println("Correlation result between BASELINE-NEEDLEMANWUNCH and GROUNDTRUTH is: " + correlation);


        correlation = calculateCorrelation(needLemanWunchSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-NEEDLEMANWUNCH SW and GROUNDTRUTH is: " + correlation);


        correlation = calculateCorrelation(jaroWinkler, groundTruthMean);
       // System.out.println("Correlation result between BASELINE-JAROWINKLER and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(jaroWinklerSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-JAROWINKLER SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(overlapCoefficient, groundTruthMean);
        //System.out.println("Correlation result between BASELINE-OVERLAPCOEFFICIENT and GROUNDTRUTH is: " + correlation);


        correlation = calculateCorrelation(overlapSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-OVERLAPCOEFFICIENT SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(smithWaterman, groundTruthMean);
        //System.out.println("Correlation result between BASELINE-SMITHWATERMAN and GROUNDTRUTH is: " + correlation);


        correlation = calculateCorrelation(smithSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-SMITHWATERMAN SW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(qGrams, groundTruthMean);
        //System.out.println("Correlation result between BASELINE-QGRAMS DISTANCE and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(qramSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-QGRAMS DISTANCE WITH STOP WORDS and GROUNDTRUTH is: " + correlation);


        correlation = calculateCorrelation(simonWhite, groundTruthMean);
        //System.out.println("Correlation result between BASELINE-SIMON-WHITE DISTANCE and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(simonSW, groundTruthMean);
        System.out.println("Correlation result between BASELINE-SIMON-WHITE SW DISTANCE and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(mongeElkan, groundTruthMean);
        System.out.println("Correlation result between BASELINE-MONGEELKAN DISTANCE and GROUNDTRUTH is: " + correlation);
        System.out.println("\n************************************************************************\n");


    }

    public static void evaluateStateOfTheArtSystems(DoubleVector groundTruthMean) throws IOException {

        DoubleVector ADW = readCorrelationFiles("correlationResult/stateOfTheArtMethods/adw.txt");
        DoubleVector semilar = readCorrelationFiles("correlationResult/stateOfTheArtMethods/semilar.txt");
        double correlation = calculateCorrelation(ADW, groundTruthMean);
        System.out.println("Correlation result between STATE_OF_THE_ART-ADW and GROUNDTRUTH is: " + correlation);

        correlation = calculateCorrelation(semilar, groundTruthMean);
        System.out.println("Correlation result between STATE_OF_THE_ART-SEMILAR and GROUNDTRUTH is: " + correlation);
        System.out.println("\n************************************************************************\n");

    }

    public static void evaluateEachAnnotatorCorrelations(DoubleVector groundTruthMean) throws IOException {

        DoubleVector annotatorA = readCorrelationFiles("correlationResult/groundTruth/annotatorA.txt");
        DoubleVector annotatorB = readCorrelationFiles("correlationResult/groundTruth/annotatorB.txt");
        DoubleVector annotatorC = readCorrelationFiles("correlationResult/groundTruth/annotatorC.txt");
        DoubleVector annotatorD = readCorrelationFiles("correlationResult/groundTruth/annotatorD.txt");
        DoubleVector annotatorE = readCorrelationFiles("correlationResult/groundTruth/annotatorE.txt");

        double correlationA = calculateCorrelation(groundTruthMean, annotatorA);
        double correlationB = calculateCorrelation(groundTruthMean, annotatorB);

        double correlationE = calculateCorrelation(groundTruthMean, annotatorE);
        System.out.println("\n********"+ correlationE);

    }
    public static  void evaluateOurMethods(DoubleVector groundTruthMean) throws IOException {

        System.out.println("\n************************************************************************\n");


        DoubleVector paragraphVector = readCorrelationFiles("correlationResult/ourResults/paragraphVector.txt");
        double correlation = calculateCorrelation(paragraphVector, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(PARAGRAPH VECTOR) and GROUNDTRUTH is: " + correlation);

        DoubleVector lsa = readCorrelationFiles("correlationResult/ourResults/lsa.txt");
        correlation = calculateCorrelation(lsa, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(LSA) and GROUNDTRUTH is: " + correlation);

//        DoubleVector baselineCombination = readCorrelationFiles("correlationResult/ourResults/baselineCombination.txt");
//        correlation = calculateCorrelation(baselineCombination, groundTruthMean);
//        System.out.println("Correlation result between OUR METHOD-(ONLY BASELINE) and GROUNDTRUTH is: " + correlation);


        System.out.println("\n************************************************************************\n");

        evaluateOurMethodsUsingWordNet(groundTruthMean);
        evaluateOurMethodsUsingUmls(groundTruthMean);
        evaluateOurMethodsUsingCombinedOntology(groundTruthMean);

    }

    public static void evaluateOurMethodsUsingCombinedOntology(DoubleVector groundTruthMean) throws IOException {

        System.out.println("\n************************************************************************\n");

        DoubleVector umlsCombinedMethod = readCorrelationFiles("correlationResult/ourResults/combined/combinedMethod0,5.txt");
        double correlation = calculateCorrelation(umlsCombinedMethod, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(COMBINED METHOD-0.5 and 1 threshold)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsCombinedMethodAverage = readCorrelationFiles("correlationResult/ourResults/combined/averageCombinedMethod.txt");
        correlation = calculateCorrelation(umlsCombinedMethodAverage, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(COMBINED METHOD - AVERAGE CDIST&PATH)) and GROUNDTRUTH is: " + correlation);


//        DoubleVector combinedVectorAvgSW = readCorrelationFiles("correlationResult/ourResults/combined/avgCombinedSw.txt");
//        correlation = calculateCorrelation(combinedVectorAvgSW, groundTruthMean);
//        System.out.println("Correlation result between OUR METHOD-(COMBINED METHOD - AVERAGE CDIST&Path-SW SENTENCE LEVEL)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsCdistUsingWordnetInfo = readCorrelationFiles("correlationResult/ourResults/combined/umlsCdistUsingWordnetInfo.txt");
        correlation = calculateCorrelation(umlsCdistUsingWordnetInfo, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(COMBINED METHOD - UMLS Cdit Using Wordnet Info)) and GROUNDTRUTH is: " + correlation);

        DoubleVector combinedTest = readCorrelationFiles("correlationResult/ourResults/combined/test.txt");
        correlation = calculateCorrelation(combinedTest, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(COMBINED METHOD - TEST)) and GROUNDTRUTH is: " + correlation);
//
        System.out.println("\n************************************************************************\n");

    }
    public static void evaluateOurMethodsUsingUmls(DoubleVector groundTruthMean) throws IOException {
        System.out.println("\n************************************************************************\n");
        DoubleVector onlyUmls_SnometCt = readCorrelationFiles("correlationResult/ourResults/onlyUmls/onlyUmls_SnomedCt.txt");
        double correlation = calculateCorrelation(onlyUmls_SnometCt, groundTruthMean);
   //     System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (SNOMED-CT-PATH)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsRelatedness = readCorrelationFiles("correlationResult/ourResults/onlyUmls/umlsRelatednessVector.txt");
        correlation = calculateCorrelation(umlsRelatedness, groundTruthMean);
    //    System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS RELATEDNESS-ALL - VECTOR)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsRelatednessCUI = readCorrelationFiles("correlationResult/ourResults/onlyUmls/umlsRelatednessCUILesk.txt");
        correlation = calculateCorrelation(umlsRelatednessCUI, groundTruthMean);
        //System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS RELATEDNESS-CUI - LESK)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsOMIMMSH_WP = readCorrelationFiles("correlationResult/ourResults/onlyUmls/umlsOMIM_MESH_WP.txt");
        correlation = calculateCorrelation(umlsOMIMMSH_WP, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS OMIM-MSH - WP)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsOMIMMSH_JCN = readCorrelationFiles("correlationResult/ourResults/onlyUmls/umlsOMIM_MESH_JCN.txt");
        correlation = calculateCorrelation(umlsOMIMMSH_JCN, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS OMIM-MSH - JCN)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsOMIMMSH_LIN = readCorrelationFiles("correlationResult/ourResults/onlyUmls/umlsOMIM_MESH_LIN.txt");
        correlation = calculateCorrelation(umlsOMIMMSH_LIN, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS OMIM-MSH - LIN)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsAllCUI_Vector = readCorrelationFiles("correlationResult/ourResults/onlyUmls/umlsAll_vector_CUI.txt");
        correlation = calculateCorrelation(umlsAllCUI_Vector, groundTruthMean);
       // System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS ALL - CUI-VECTOR)) and GROUNDTRUTH is: " + correlation);

        DoubleVector umlsOMIMMSH_CDIST = readCorrelationFiles("correlationResult/ourResults/onlyUmls/umlsOMIM_MESH_CDIST.txt");
        correlation = calculateCorrelation(umlsOMIMMSH_CDIST, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS OMIM-MSH - CDIST)) and GROUNDTRUTH is: " + correlation);

//        DoubleVector umlsCDISTSW = readCorrelationFiles("correlationResult/ourResults/onlyUmls/cdistSW.txt");
//        correlation = calculateCorrelation(umlsCDISTSW, groundTruthMean);
//        System.out.println("Correlation result between OUR METHOD-(ONLY UMLS (UMLS OMIM-MSH - CDIST SW)) and GROUNDTRUTH is: " + correlation);

        System.out.println("\n************************************************************************\n");

    }
    public static void evaluateOurMethodsUsingWordNet(DoubleVector groundTruthMean) throws IOException {
        System.out.println("\n************************************************************************\n");

        DoubleVector path = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/pathWordnet.txt");
        double correlation = calculateCorrelation(path, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - PATH) and GROUNDTRUTH is: " + correlation);

        DoubleVector pathWithIdf = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/pathWordnetWithIdfScores.txt");
        correlation = calculateCorrelation(pathWithIdf, groundTruthMean);
       // System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - PATH WITH IDF) and GROUNDTRUTH is: " + correlation);

//        DoubleVector pathWithStopWords = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/pathWordnetStopWords.txt");
//        correlation = calculateCorrelation(pathWithStopWords, groundTruthMean);
//        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - PATH WITH REMOVAL STOPWORDS) and GROUNDTRUTH is: " + correlation);

        DoubleVector resnik = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/resnikWordNet.txt");
        correlation = calculateCorrelation(resnik, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - RESNIK) and GROUNDTRUTH is: " + correlation);

        DoubleVector lin = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/linWordNet.txt");
        correlation = calculateCorrelation(lin, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - LIN) and GROUNDTRUTH is: " + correlation);


        DoubleVector lesk = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/lesk.txt");
        correlation = calculateCorrelation(lesk, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - LESK) and GROUNDTRUTH is: " + correlation);

        DoubleVector wuPalmer = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/wuPalmer.txt");
        correlation = calculateCorrelation(wuPalmer, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - WUANDPALMER) and GROUNDTRUTH is: " + correlation);

        DoubleVector jiangConrath = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/jiangConrath.txt");
        correlation = calculateCorrelation(jiangConrath, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - JIANGCONRADTH) and GROUNDTRUTH is: " + correlation);


        DoubleVector leacockChodorow = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/leacockChodorow.txt");
        correlation = calculateCorrelation(leacockChodorow, groundTruthMean);
        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - LEACOCKCHODOROW) and GROUNDTRUTH is: " + correlation);

//        DoubleVector hirstStOnge = readCorrelationFiles("correlationResult/ourResults/onlyWordnet/hirstStOnge.txt");
//        correlation = calculateCorrelation(hirstStOnge, groundTruthMean);
//        System.out.println("Correlation result between OUR METHOD-(ONLY WORDNET - HIRSTONGE) and GROUNDTRUTH is: " + correlation);


        System.out.println("\n************************************************************************\n");

    }
    public static void main(String[] args) throws SLIB_Exception, IOException {

        DoubleVector groundTruthMean = readCorrelationFiles("correlationResult/groundTruth/test.txt");
        DoubleVector biomedicalAnnotator = readCorrelationFiles("correlationResult/groundTruth/annotatorD.txt");
        evaluateBaselineResults(biomedicalAnnotator);
       // evaluateEachAnnotatorCorrelations(groundTruthMean);
        evaluateStateOfTheArtSystems(biomedicalAnnotator);
        evaluateOurMethods(biomedicalAnnotator);



    }
}