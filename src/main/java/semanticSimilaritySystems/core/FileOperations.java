package semanticSimilaritySystems.core;
import com.google.common.io.Resources;
import semanticSimilaritySystems.core.Pair;
import java.io.*;
import java.util.*;

public class FileOperations {

    public static String replacePunctuations(String phrase){

        phrase = phrase.trim();
        phrase = phrase.replaceAll("\\.","");
        phrase = phrase.replaceAll(";","");
        phrase = phrase.replaceAll("-","");
        phrase = phrase.replaceAll(":","");
        phrase = phrase.replaceAll(",","");
        phrase = phrase.replaceAll("_","");
        phrase = phrase.replaceAll("!", "");
        // phrase = phrase.replace(" " , "");
        phrase = phrase.replaceAll("\\(", "");
        phrase = phrase.replaceAll("\\)", "");
        phrase = phrase.replaceAll("\\[", "");
        phrase = phrase.replaceAll("\\]", "");
        phrase = phrase.replaceAll("\\*", "");
        phrase = phrase.replaceAll("/", "");
        phrase = phrase.replaceAll("\\?", "");


        return phrase.toLowerCase();
    }



    public HashMap<String, Double> readIDFScores() throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(Resources.getResource("idfScores/idf_values_tac_data.txt").getFile()));
        String line;
        HashMap<String , Double> idf_hash = new HashMap<String, Double>();

        while ((line = buffer.readLine())!=null){
            String[] split = line.split("\\s+");
            String word = split[0];
            Double value = Double.valueOf(split[1]);
            if(!idf_hash.containsKey(word))
                idf_hash.put(word, value);
        }


        return idf_hash;

    }
    public static List<String> readStopWordsList() throws IOException {
        List<String> stopWordsList = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(Resources.getResource("stopWords/stop_words.txt").getFile()));
        String line;

        while ((line = bufferedReader.readLine())!=null){
            if(!stopWordsList.contains(line))
                stopWordsList.add(line.toLowerCase());
        }

        return stopWordsList;

    }
    public static HashSet<String> addSentenceToDictionary(HashSet<String> dictionary, String sentence, List<String> stops){
        String[] split = sentence.toLowerCase().split("\\s+");
        for(String word:split){
            word = replacePunctuations(word);
            word = word.trim();
            if(!dictionary.contains(word) && !stops.contains(word))
                dictionary.add(word);
        }

        return dictionary;
    }
    public static HashSet<String> constructDictionary(List<Pair> pairList, List<String> stops){
        HashSet<String> dictionary = new HashSet<String>();
        for(Pair current: pairList){

            dictionary = addSentenceToDictionary(dictionary, current.getSentence1(),stops);

            dictionary = addSentenceToDictionary(dictionary,current.getSentence2(),stops);

        }
        return dictionary;
    }

    public static String removeStopWordsFromSentence(String sentence, List<String> stopwords){
        String processedS = sentence;
        String split[] = sentence.split("\\s+");
//        for (String s: split){
//            if(!stopwords.contains(s)){
//                processedS  = processedS +" " +s;
//            }
//
//        }

        processedS  =processedS.trim();
        processedS = replacePunctuations(processedS);
        return processedS.trim();
    }
    public static BufferedReader openFile(String filePath) throws FileNotFoundException {
        BufferedReader buffer = new BufferedReader(new FileReader(new File(Resources.getResource(filePath).getFile())));
        return buffer;
    }

    public static void closeFile(BufferedReader buffer) throws IOException {
        buffer.close();
    }

    public static Pair fillPairProperties(String line){
        Pair newPair = new Pair();
        String[] split = line.split("\\s+");
        newPair.setPairId(split[0]);
        line = line.substring(split[0].length(), line.length());
        split = line.trim().split("\t");
        int count =1;
        for(String sentence: split){
            sentence = sentence.trim();
            if(!sentence.equals("")){
                String[] splitSentence = sentence.split("\\s+");
                List<String> lst = new LinkedList<String>();

                String beginBigram ="\\B"; String endBigram  ="\\E";
                String preWord =beginBigram;
                List<String> bigramlst = new LinkedList<String>();
//                for(String word: split){
//                    word = word.toLowerCase();
//                    word = replacePunctuations(word);
//                    lst.add(word);
//                    bigramlst.add(preWord +"-"+word);
//                    preWord = word;
//                }
//                bigramlst.add(preWord + "-" + endBigram);
                if(count==1){
                    newPair.setSentence1(sentence);
                    newPair.setPreprocessedWordListForSentence1(lst);
                    newPair.setBigramFeaturesForSentence1(bigramlst);
                }
                else{
                    newPair.setSentence2(sentence);
                    newPair.setPreprocessedWordListForSentence2(lst);
                    newPair.setBigramFeaturesForSentence2(bigramlst);
                }
                count++;
              //  System.out.println(sentence);
            }
        }
        return newPair;
    }




    public static LinkedList<Pair> readPairsFromFile(String filePath) throws IOException {
        LinkedList<Pair> pairList = new LinkedList<Pair>();
        BufferedReader buffer = openFile(filePath);
        String line;
        while((line=buffer.readLine())!=null){
            Pair newPair = fillPairProperties(line);
           // System.out.println(line);
            pairList.add(newPair);
        }
        closeFile(buffer);
        return pairList;
    }
}
