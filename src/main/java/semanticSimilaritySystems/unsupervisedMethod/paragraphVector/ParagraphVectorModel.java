package semanticSimilaritySystems.unsupervisedMethod.paragraphVector;

import com.google.common.io.Resources;
import java.io.*;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by gizem on 08.02.2016.
 */
public class ParagraphVectorModel {
    private String paragraphVectorModelFile;

    public ParagraphVectorModel(String paragraphVectorModelFile){
        this.paragraphVectorModelFile  = paragraphVectorModelFile;

    }

    public BufferedReader openFile() throws FileNotFoundException {
        BufferedReader buffer = new BufferedReader(new FileReader(new File(Resources.getResource(paragraphVectorModelFile).getFile())));
        return buffer;
    }

    public void closeFile(BufferedReader buffer) throws IOException {
        buffer.close();;
    }

    public Vector<Double> getDoubleVector(String line){
        Vector<Double> vector = new Vector<Double>();

        String[] split = line.split("\\s+");
        int vecCount = 0;
        for(int i =1; i<split.length; i++){

            vector.add(vecCount,Double.valueOf(split[i]));
            vecCount++;
        }

        return vector;
    }
    public HashMap<String, Vector<Double>> readModelFile() throws IOException {
        BufferedReader buffer = openFile();

        HashMap<String, Vector<Double>> sentenceVectors = new HashMap<String, Vector<Double>>();
        String line;int count = 1 ;
        while((line=buffer.readLine())!=null && count <= 200){
           // System.out.println(line);
            sentenceVectors.put(String.valueOf(count), getDoubleVector(line));
            count++;
        }

        closeFile(buffer);

        return sentenceVectors;

    }

}
