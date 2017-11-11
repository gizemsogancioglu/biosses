package semanticSimilaritySystems.supervisedMethod.features;
import com.google.common.io.Resources;
import semanticSimilaritySystems.core.Pair;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gizem on 01.02.2016.
 */
public class FeatureExtractor {
    LinkedList<Pair> pairs;
    List<Double> groundTruthList = new LinkedList<Double>();
    List<String> stopWordsList;

    public FeatureExtractor(LinkedList<Pair> pairs, List<String> stopWordsList){
        this.pairs = pairs;
        this.stopWordsList = stopWordsList;
    }
    public void readParagraphVecFeature() throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(Resources.getResource("correlationResult/ourResults/paragraphVector.txt").getFile()));
        String line;
        int currentPairIndex = 0;
        while((line=buffer.readLine())!= null){
            Pair currentPair = pairs.get(currentPairIndex);
            currentPair.setParagraphVecResult(Double.valueOf(line));
            currentPairIndex++;
        }
    }

    public void readQGramFeature() throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(Resources.getResource("correlationResult/baselineResults/SW/qgramSW.txt").getFile()));
        String line;
        int currentPairIndex = 0;
        while((line=buffer.readLine())!= null){
            Pair currentPair = pairs.get(currentPairIndex);
            currentPair.setqGramSimilarityScore(Double.valueOf(line));
            currentPairIndex++;
        }

    }

    public void readGroundTruth() throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(Resources.getResource("correlationResult/groundTruth/test.txt").getFile()));
        String line;
        while((line=buffer.readLine())!= null){
            groundTruthList.add(Double.valueOf(line));
        }
    }
    public void readCombineOntologyMethodFeature() throws IOException {

        BufferedReader buffer = new BufferedReader(new FileReader(Resources.getResource("correlationResult/ourResults/combined/avgCombinedSw.txt").getFile()));
        String line;
        int currentPairIndex = 0;
        while((line=buffer.readLine())!= null){
            Pair currentPair = pairs.get(currentPairIndex);
            currentPair.setCombinedOntologyMethod(Double.valueOf(line));
            currentPairIndex++;

        }

    }

    public String defineWordAttributes(int size){
        String wordAtt ="";
        for(int i  = 0; i < size; i++){
            wordAtt += "@attribute " + i + " {0,1}\n";
        }

        return wordAtt;

    }

    public void defineAttributesInArffFileFormat(BufferedWriter writer, LinkedList<String> dict) throws IOException {
       String wordAttributes = defineWordAttributes(dict.size());
       // String bigramFeatures = defineWordAttributes(dict.size());
        writer.write("@relation semantic-similarity\n" +
                "\n" +
                "@attribute paragraphVec REAL\n" +
                "@attribute qGram\tREAL\n" +
                "@attribute combinedOntologyMethod\tREAL\n" +
               // wordAttributes +
                "@attribute class\tREAL\n" +
                "\n" +
                "@data");

        writer.newLine();
    }
    public static String replacePunctuations(String phrase){

        phrase = phrase.trim();
        phrase = phrase.replaceAll("\\.","");
        phrase = phrase.replaceAll(";","");
        phrase = phrase.replaceAll("-","");
        phrase = phrase.replaceAll(":","");
        phrase = phrase.replaceAll(",","");
        phrase = phrase.replaceAll("_","");
        phrase = phrase.replaceAll("!", "");
        phrase = phrase.replace(" " , "");
        phrase = phrase.replaceAll("\\(", "");
        phrase = phrase.replaceAll("\\)", "");
        phrase = phrase.replaceAll("\\[", "");
        phrase = phrase.replaceAll("\\]", "");
        phrase = phrase.replaceAll("\\*", "");
        phrase = phrase.replaceAll("/", "");
        phrase = phrase.replaceAll("\\?", "");


        return phrase.toLowerCase();
    }

    public String defineCommonWordFeatureInstance(Pair pair, LinkedList<String> dict){
        List<String> sentence1 = pair.getPreprocessedWordListForSentence1();
        List<String> sentence2 = pair.getPreprocessedWordListForSentence2();
        String result ="";
        for(String dictWord : dict){
            if(sentence1.contains(dictWord) && sentence2.contains(dictWord)){
                result += "1,";
            }
            else result += "0,";

        }

        return  result;
    }
    public String defineCommonBigramFeatures(Pair pair, LinkedList<String> dict){
        List<String> bigram1 = pair.getBigramFeaturesForSentence1();
        List<String> bigram2 = pair.getBigramFeaturesForSentence2();
        String result ="";
        for(String dictWord : dict){
            if(bigram1.contains(dictWord) && bigram2.contains(dictWord)){
                result += "1,";
            }
            else result += "0,";

        }

        return  result;
    }
    public  void defineInstances(BufferedWriter writer, LinkedList<String> dict) throws IOException {
        for(Pair currentPair: pairs){
            String commonWordFeaturesInstance = defineCommonWordFeatureInstance(currentPair, dict);
           // String commonBigramFeatures = defineCommonBigramFeatures(currentPair, dict);
            writer.write(currentPair.getParagraphVecResult()  + "," + currentPair.getqGramSimilarityScore()+"," +  currentPair.getCombinedOntologyMethod()+","+
                   groundTruthList.get(Integer.valueOf(currentPair.getPairId())-1));

            writer.newLine();
        }

    }
    public LinkedList<String> addDictionary(LinkedList<String> dict, List<String> sentence){
        for(String word : sentence){
             if(!stopWordsList.contains(word))
                dict.add(word);
        }

        return dict;
    }


    public LinkedList<String> createDictionary(){
        LinkedList<String> dictinary = new LinkedList<String>();
        for(Pair currentPair: pairs){
            List<String> sentence1 = currentPair.getPreprocessedWordListForSentence1();
            List<String> sentence2 = currentPair.getPreprocessedWordListForSentence2();

            dictinary = addDictionary(dictinary, sentence1);
            dictinary = addDictionary(dictinary, sentence2);

        }
        return dictinary;
    }
    public LinkedList<String> createBigramDictionary(){
        LinkedList<String> bigramDict = new LinkedList<String>();
        for(Pair currentPair: pairs) {

            List<String> bigramF1 = currentPair.getBigramFeaturesForSentence1();
            List<String> bigramF2 = currentPair.getBigramFeaturesForSentence2();

            bigramDict = addDictionary(bigramDict, bigramF1);
            bigramDict = addDictionary(bigramDict, bigramF2);
        }
        return bigramDict;
    }
    public void createArffFileFromInstances() throws IOException {
        readParagraphVecFeature();
        readCombineOntologyMethodFeature();
        readQGramFeature();
        readGroundTruth();

        LinkedList<String> dict = createDictionary();
        LinkedList<String> bigramDict = createBigramDictionary();

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("rawData_biomedical.arff")));
        defineAttributesInArffFileFormat(writer, dict);
        defineInstances(writer, dict);
        writer.close();
    }

    public void writeInstances(BufferedWriter writer) throws IOException {
        for(Pair currentPair: pairs){
            writer.write(groundTruthList.get(Integer.valueOf(currentPair.getPairId())-1) +" "+ "1:"+currentPair.getParagraphVecResult() + " 2:" + currentPair.getqGramSimilarityScore()+" 3:" + currentPair.getCombinedOntologyMethod());

            writer.newLine();
        }
    }

    public void createSVMFileFormatInstances() throws IOException {

        readParagraphVecFeature();
        readQGramFeature();
        readCombineOntologyMethodFeature();
        readGroundTruth();

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("svm_raw_data.txt")));

        writeInstances(writer);
        writer.close();

    }


}
