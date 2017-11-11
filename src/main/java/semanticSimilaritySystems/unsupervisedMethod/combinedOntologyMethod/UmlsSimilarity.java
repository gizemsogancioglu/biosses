package semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod;

/**
 * Created by gizem on 31.01.2016.
 */

import java.io.*;

import semanticSimilaritySystems.core.SimilarityMeasure;

import slib.utils.ex.SLIB_Exception;
import sun.security.util.Resources;


public class UmlsSimilarity implements SimilarityMeasure {

    public double getSimilarity(String word1, String word2) throws SLIB_Exception, IOException {
        try {
            return calculateUmlsPairScore(word1, word2);
        } catch (InterruptedException e) {
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(calculateUmlsPairScore("hand", "skull"));
    }


    public static double calculateUmlsPairScore(String word1, String word2) throws SLIB_Exception, IOException, InterruptedException {

        double similarity = 0;

        String perl_path = "perl /"; // default to linux
        if (System.getProperty("os.name", "generic")
                .toLowerCase().indexOf("windows") > -1) {
            perl_path = "C:\\Strawberry\\perl\\bin\\perl.exe ";
        }

        String cmd = perl_path
                + com.google.common.io.Resources.getResource("UMLS-Similarity-1.47/utils/")
                .getFile().substring(1)
                + "query-umls-similarity-webinterface.pl --measure cdist --sab OMIM,MSH --rel PAR/CHD "
                + word1 + " " + word2;
        Process process = Runtime.getRuntime().exec(cmd);

        process.waitFor();

        if (process.exitValue() == 0) {
            //System.out.println("Command Successful");
        } else {
            System.out.println("Perl Failure");
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            //System.out.println(line);
            if (line.contains(word1) && line.contains(word2)) {
                String[] split = line.split("<>");
                try {
                    similarity = Double.parseDouble(split[0]);
                    //System.out.println(similarity);
                } catch (NumberFormatException e) {
                }
            }

        }

        //System.out.println("SON:" + similarity);
        return similarity;
    }
}
