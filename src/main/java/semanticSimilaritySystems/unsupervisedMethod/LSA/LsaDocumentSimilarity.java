package semanticSimilaritySystems.unsupervisedMethod.LSA;
import com.google.common.io.Resources;
import edu.ucla.sspace.common.DocumentVectorBuilder;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.SemanticSpaceIO;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.vector.DenseVector;
import edu.ucla.sspace.vector.DoubleVector;
import semanticSimilaritySystems.core.SimilarityMeasure;

import java.io.*;

/**
 * Created by gizem on 31.01.2016.
 */
public class LsaDocumentSimilarity implements SimilarityMeasure {
    DocumentVectorBuilder s;

    public static double documentVectorSimilarity(String[] args) throws IOException {

        SemanticSpace sspace = SemanticSpaceIO.load(Resources.getResource(args[0]).getFile());

        // See how many dimensions are present in the SemanticSpace so that
        // we can initialize the document vectors correctly.
        int numDims = sspace.getVectorLength();

        // Create the DocumentVectorBuilder which will use the
        // term-to-vector mapping in the SemanticSpace to construct
        // document-level representations
        DocumentVectorBuilder builder = new DocumentVectorBuilder(sspace);

        // Create the first document's vector.  We create this manually (as
        // the caller) because we should have some sense of how big the
        // document is and whether it makes sense to have the document be
        // represented as a sparse or full vector.
        DoubleVector documentVector = new DenseVector(numDims);

        // Read in the first document and create a representation of it by
        // summing the vectors for all of its tokens
        BufferedReader br = new BufferedReader(new FileReader(args[1]));
        builder.buildVector(br, documentVector);
        br.close();

        // Read in a second document and create a representation of it by
        // summing the vectors for all of its tokens
        br = new BufferedReader(new FileReader(args[2]));
        DoubleVector documentVector2 = new DenseVector(numDims);
        builder.buildVector(br, documentVector2);
        br.close();

        double similarity =
                Similarity.cosineSimilarity(documentVector, documentVector2);
        System.out.printf("The similarity of %s and %s is %f%n",
                args[1], args[2], similarity);

        return similarity;
    }

    public static boolean isalphanumeric(String s){

        return s.matches("[^a-zA-Z0-9]");

    }
    public static boolean isNumeric(String s) {

        return s.matches("[-+]?\\d*\\.?\\d+");

    }
    public static String preprocess(String result){

        String last = "";
        String split[] = result.toLowerCase().split("\\s+");
         for(String s : split){
            s = s.replaceAll(",", "");
            s = s.replaceAll(":", "");
            s = s.replaceAll(";", "");
            s = s.replaceAll("!", "");
            s = s.replace(" " , "");
            s = s.replaceAll("-", "");
            s = s.replaceAll("_", "");
            s = s.replaceAll("\\?", "");
            s = s.replaceAll("-", "");
            s = s.replaceAll("\\(", "");
            s = s.replaceAll("\\)", "");
            s = s.replaceAll("\\[", "");
            s = s.replaceAll("\\]", "");
            s = s.replaceAll("\\*", "");
            s = s.replaceAll("\\\\", "");
            s = s.replaceAll("/", "");
            s = s.replaceAll("\"", "");
            s = s.replaceAll("\'", "");
            s = s.replaceAll("~", "");
            s = s.replaceAll("=", "");
            s = s.replaceAll("<", "");
            s = s.replaceAll(">", "");
            s = s.replaceAll("|", "");
            s = s.replaceAll("\\{", "");
            s = s.replaceAll("\\}", "");

            if((!s.equals(".")) && (!isNumeric(s)) && (!isalphanumeric(s))){
                s = s.replaceAll("\\.", "");
                if(!isNumeric(s)){
                    //Stemmer stem = new Stemmer();
                    //stem.add(s.toCharArray(), s.length());
                    //stem.stem();
                    //s = stem.toString();
                    if(!last.equals(""))
                        last += " " + s;
                    else{
                        last = s;
                    }

                }

            }
        }

        return last;
    }

    public static void createDocumentForSentences(String sentence1, String sentence2, String filePathForSentence1, String filePathForSentence2) throws IOException {

        BufferedWriter bufferForSentence1 = new BufferedWriter(new FileWriter(new File(Resources.getResource(filePathForSentence1).getFile())));
        BufferedWriter bufferForSentence2 = new BufferedWriter(new FileWriter(new File(Resources.getResource(filePathForSentence2).getFile())));

        bufferForSentence1.write(preprocess(sentence1));
        bufferForSentence2.write(preprocess(sentence2));

        bufferForSentence1.close();
        bufferForSentence2.close();

    }

    public double getSimilarity(String sentence1, String sentence2) throws IOException {

        String filePathForSentence1 = "lsaModel/document1.txt";
        String filePathForSentence2 = "lsaModel/document2.txt";
        createDocumentForSentences(sentence1,sentence2,filePathForSentence1,filePathForSentence2);
        String[] args=new String[3];
        args[0] = "lsaModel/mylsa1.sspace";
        args[1] = Resources.getResource(filePathForSentence1).getFile();
        args[2] = Resources.getResource(filePathForSentence2).getFile();
        return documentVectorSimilarity(args);

    }
}
