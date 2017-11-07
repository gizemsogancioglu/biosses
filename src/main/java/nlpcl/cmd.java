package nlpcl;
/**
 * This class provides command-line interface for easily connecting
 * with other python tools during experiments.
 *
 * Usage:
 *   compile: cd to project root and 'mvn install'
 *   run: ./run.sh corpus.txt
 *   (or)
 *   run: java -classpath "target/sses-1.0-SNAPSHOT/WEB-INF/lib/*:target/sses-1.0-SNAPSHOT/WEB-INF/classes/." nlpcl.cmd corpus.txt
 *
 * The corpus should contain one pair of sentences per line, separated by ||| operator.
 *
 * author: Fitsum
 * github: fgaim
 *
 */

import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;
import semanticSimilaritySystems.core.FileOperations;
import semanticSimilaritySystems.supervisedMethod.regressors.LinearRegressionMethod;
import semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod.CombinedOntologyMethod;
import semanticSimilaritySystems.unsupervisedMethod.paragraphVector.WordVectorConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class cmd {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide corpus path!! ;)");
            System.exit(0);
        }
        String inputfile = args[0];

        // read corpus
        String delimiter = "|||";
        ArrayList<String> col1 = new ArrayList<String>();
        ArrayList<String> col2 = new ArrayList<String>();
        try {
            FileReader fr = new FileReader(inputfile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int idx;
            while ((line = br.readLine()) != null) {
                idx = line.indexOf(delimiter);
                if (idx != -1) {
                    col1.add(line.substring(0, idx));
                    col2.add(line.substring(idx + 1, line.length()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // read stopwords
        List<String> stopwords = new ArrayList<String>();
        try {
            FileOperations fileOperations = new FileOperations();
            stopwords = fileOperations.readStopWordsList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CombinedOntologyMethod measureOfWordNet = new CombinedOntologyMethod(stopwords);
        CombinedOntologyMethod measureOfUmls = new CombinedOntologyMethod(stopwords);
        CombinedOntologyMethod measureOfCombined = new CombinedOntologyMethod(stopwords);
        StringMetric qgram_metric = StringMetrics.qGramsDistance();
        WordVectorConstructor wordVectorConstructor = new WordVectorConstructor();
        LinearRegressionMethod linearRegressionMethod = new LinearRegressionMethod();

        String title = "#\tWordNet\tUMLS\tCombined\tQGRAM\tPVM\tSPRVSD";
        StringBuilder result = new StringBuilder(title);
        System.out.println(title);
        result.append("\n");
        String s1, s2;
        for (int i = 0; i < col1.size(); ++i) {
            s1 = col1.get(i);
            s2 = col2.get(i);

            System.out.print((i + 1) + ".\t");
            result.append(i + 1).append(".\t");

            try {
                /************ WORDNET-BASED SIMILARITY METHOD *************/
                double score = measureOfWordNet.getSimilarityForWordnet(s1, s2);
                System.out.print(score + "\t");
                result.append(score).append("\t");
            } catch (Exception e) {
                result.append("ERR");
            }

            try {
                /************ UMLS-BASED SIMILARITY METHOD *************/
                double score = measureOfUmls.getSimilarityForUMLS(s1, s2);
                System.out.print(score + "\t");
                result.append(score).append("\t");
            } catch (Exception e) {
                result.append("ERR");
            }

            try {
                /************ COMBINED ONTOLOGY METHOD ****************/
                double score = measureOfCombined.getSimilarity(s1, s2);
                System.out.print(score + "\t");
                result.append(score).append("\t");
            } catch (Exception e) {
                result.append("ERR");
            }

            try {
                /************* QGRAM STRING SIMILARIIY ****************/
                double score = qgram_metric.compare(s1, s2);
                System.out.print(score + "\t");
                result.append(score).append("\t");
            } catch (Exception e) {
                result.append("ERR");
            }

            try {
                /************* PARAGRAPH VECTOR METHOD *****************/
                double score = wordVectorConstructor.getSimilarity(s1, s2);
                System.out.print(score + "\t");
                result.append(score).append("\t");
            } catch (Exception e) {
                result.append("ERR");
            }

            try {
                /********* SUPERVISED SEMANTIC SIMILARITY SYSTEM **********/
                double score = linearRegressionMethod.getSimilarity(s1, s2);
                System.out.println(score);
                result.append(score).append("\n");
            } catch (Exception e) {
                result.append("ERR");
            }
        }

        try {
            FileWriter fw = new FileWriter(inputfile + ".result");
            fw.write(result.toString());
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
