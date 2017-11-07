package semanticSimilaritySystems.unsupervisedMethod.combinedOntologyMethod;
import semanticSimilaritySystems.core.Pair;
import semanticSimilaritySystems.core.Sentence;
import semanticSimilaritySystems.core.SimilarityMeasure;
import semanticSimilaritySystems.core.Word;
import similarityMeasures.CosineSimilarity;
import slib.utils.ex.SLIB_Exception;

import java.io.*;
import java.util.*;

/**
 * Created by orhan on 31.01.2016.
 */
public class CombinedOntologyMethod implements SimilarityMeasure{

    private UmlsSimilarity umls_similarity_score;
    private WordNetSimilarity wordnet_similarity_score;
    static HashSet<String> stringDict;
    private HashMap<String, Double> pair_score = new HashMap<String, Double>();
    List<String> stopWordsList = new ArrayList<String>();

    public CombinedOntologyMethod(List<String> stopWordsList){

        this.stopWordsList = stopWordsList;
    }
    public HashSet<Word> addSentenceToDictionary(HashSet<Word> dictionary,  Sentence sentence){
         for(Word word:sentence.getWords()){
            if(!stringDict.contains(word.getWord().toLowerCase())  && !stopWordsList.contains(word.getWord().toLowerCase())){
                dictionary.add(word);
                stringDict.add(word.getWord().toLowerCase());
            }
        }
        return dictionary;
    }

    public HashSet<Word> constructDictionary(Sentence sentence1, Sentence sentence2){
        HashSet<Word> dictionary = new HashSet<Word>();
        stringDict = new HashSet<String>();
        dictionary = addSentenceToDictionary(dictionary, sentence1);
        dictionary = addSentenceToDictionary(dictionary, sentence2);

        return dictionary;
    }

      public static Sentence fillSentenceWithMetamapResults(String results, List<String> stopWordsList){
        Sentence mappedSentence = new Sentence(); mappedSentence.setWords(new LinkedList<Word>());
        mappedSentence.setStringWords(new LinkedList<String>());
        boolean metamapping = false;
        boolean newPhrase = false;
        String[] split = results.split("\n");
        //System.out.println(results);
        String preTerm="";
        int wordIndex = 0;
        for(String s: split){
            if(!s.equals("")){
                if(s.contains("Phrase:") ){
                    //SAVE PREVIOUS ONE
                    if(newPhrase && !metamapping) {
                        //System.out.println("Not umls word: " + preTerm);
                        preTerm = replacePunctuations(preTerm);
                        if(!preTerm.equals("")) {
                            Word word = new Word();
                            if(stopWordsList.contains(preTerm))
                                word.setStopWord(true);
                            else word.setStopWord(false);
                            word.setWord(preTerm);
                            word.setInUmls(false);
                            mappedSentence.getWords().add(wordIndex, word);
                            mappedSentence.getStringWords().add(wordIndex, word.getWord());
                            wordIndex++;
                        }
                    }

                    newPhrase = true;
                    metamapping = false;
                    s = s.replace("Phrase: " ,"");
                    s = s.trim();
                    preTerm = s;

                }
                else if(s.contains("Meta Mapping")){
                    if(metamapping && newPhrase)
                        newPhrase = false;
                    metamapping = true;
                }
                else{
                 //   System.out.println(s);
                    if(newPhrase && metamapping){
                        s = s.trim();
                        int index = 0;
                        String[] splitSentence = s.split("\\s+");

                        if(s.contains("(")){
                            index = s.indexOf("(");
                        }
                        else{
                            index = s.indexOf("[");

                        }

                        String definition="";int defBegin=0, defEnd=0;
                        if(s.contains("[")){
                            defBegin = s.indexOf("[");
                            defEnd = s.indexOf("]");
                            definition = s.substring(defBegin+1, defEnd);

                        }
                        s = s.substring(0, index);
                        s = s.replace(splitSentence[0], "").trim();

                        Word newWord = new Word();
                        if(!definition.toLowerCase().contains("concept"))
                            newWord.setInUmls(true);
                        else newWord.setInUmls(false);

                        newWord.setWord(s);
                        if(stopWordsList.contains(s))
                            newWord.setStopWord(true);
                        else newWord.setStopWord(false);
                        mappedSentence.getWords().add(wordIndex, newWord);
                        mappedSentence.getStringWords().add(wordIndex, newWord.getWord());
                     //   System.out.println("UMLS TERM: " +s);
//                        System.out.println(definition);

                    }

                }

            }

        }
        return mappedSentence;
    }

    public static Sentence getMetamapResult(String sentence, List<String> stopWordsList) throws IOException {
        BufferedWriter buffer = new BufferedWriter(new FileWriter(new File("input.txt")));
        buffer.write(sentence);
        buffer.close();

        GenericBatchNew batch = new GenericBatchNew();
        String[] args = new String[5];
        args[0] = "--command";
        args[1] = "metamap";
        args[2] = "--email";
        args[3] = "gizemsogancioglu@gmail.com";
        args[4] = "input.txt";
      //  String results = batch.main(args);

        String results = "";
       // System.out.println(results);
        return fillSentenceWithMetamapResults(results, stopWordsList);
    }

    public HashSet<String> filHash(Sentence sentence, HashSet<String> hash){
        for(Word s: sentence.getWords()){
            if(s.isInUmls() && !hash.contains(s.getWord())){
                hash.add(s.getWord());
            }

        }

        return hash;
    }

    public void writeAllUmlsTerms(String fileName, List<Pair> pairs) throws IOException {
        BufferedWriter buffer = new BufferedWriter(new FileWriter(new File(fileName)));
        String line;HashSet<String> umlsTerms = new HashSet<String>();
        int index = 1;
        for(Pair currentPair: pairs){
           Sentence sentence1 = getMetamapResult(currentPair.getSentence1(), stopWordsList);
           Sentence sentence2 =  getMetamapResult(currentPair.getSentence2(), stopWordsList);

           // filHash(sentence1, umlsTerms);
            //filHash(sentence2, umlsTerms);
            System.out.println(index + ". pair done!");
            index++;

        }

        for(String s:umlsTerms){
            buffer.write(s);
            buffer.newLine();
        }

        buffer.close();
    }


    public double calculateOnlyWordnetScores(Word word1, Word word2) throws IOException {

        double similarityScore = 0.0;
        String txt1 = (word1.getWord());
        String txt2 = (word2.getWord());

        if(txt1.equals(txt2))
            similarityScore = 1;
        else {
            WordNetSimilarity wordNet_similarity_measure = new WordNetSimilarity();
            double wordNet_similarity_score = wordNet_similarity_measure.getSimilarity(txt1, txt2);
            if (wordNet_similarity_score != -1)
                similarityScore = wordNet_similarity_score;
        }
        return similarityScore;
    }

    public static String removeAllNonAsciiLetters(String text){
        text =  text.replaceAll("[^\\x00-\\x7F]", "");
        return text;
    }

    public double calculateOnlyUmlsScores(Word word1, Word word2) throws SLIB_Exception, IOException {

        double umls_similarity_score = 0;
       /* WordNetSimilarity w  = new WordNetSimilarity();
        double wordnetscore = w.getSimilarity(word1.getWord(), word2.getWord());

        if (wordnetscore <= 0 && word1.isInUmls() && word2.isInUmls() && (word1.getWord().equalsIgnoreCase(word2.getWord())
        || word1.getWord().contains(word2.getWord()) || word2.getWord().contains(word1.getWord()))) {
           //
            System.out.println(word1.getWord() +" - " + word2.getWord() +" : " + wordnetscore);
            umls_similarity_score = 1;
        }*/
        // else if(wordnetscore == 0) {
          //  if (word1.isInUmls() && word2.isInUmls()) {

                if(!getPair_score().containsKey(word1.getWord() + "-" + word2.getWord()) &&
                        !getPair_score().containsKey(word2.getWord() +"-" + word1.getWord())) {

                    UmlsSimilarity umls_similarity_measure = new UmlsSimilarity();
                    if(word1.getWord().equalsIgnoreCase(word2.getWord()))
                        umls_similarity_score = 1;
                    else
                        umls_similarity_score = umls_similarity_measure.getSimilarity(word1.getWord(), word2.getWord());

                    getPair_score().put(word1.getWord() + "-" + word2.getWord(), umls_similarity_score);


                    if (umls_similarity_score <= 0)
                        umls_similarity_score = 0;
                }
                else{

                    if(getPair_score().containsKey(word1.getWord() + "-" + word2.getWord()))
                        umls_similarity_score = getPair_score().get(word1.getWord()+"-"+word2.getWord());
                    else umls_similarity_score = getPair_score().get(word2.getWord()+"-"+word1.getWord());
                }

          //  }
       // }
        return umls_similarity_score;
    }

    public double calculateUMLSScoreUsingWordnetInformation(Word word1 , Word word2) throws SLIB_Exception, IOException {
        double similarityScore = 0.0;
        String txt1 = replacePunctuations(word1.getWord());
        String txt2 = replacePunctuations(word2.getWord());

        WordNetSimilarity wordNet_similarity_measure = new WordNetSimilarity();
        similarityScore = wordNet_similarity_measure.getSimilarity(txt1, txt2);

       // System.out.println(txt1 + " - " + txt2 + " : "  +similarityScore);
        if (txt1.equalsIgnoreCase(txt2) && similarityScore == 0) {
            similarityScore = 1;
        }

        else {

            if(similarityScore <= 0){
                UmlsSimilarity umls_similarity_measure = new UmlsSimilarity();
                similarityScore = umls_similarity_measure.getSimilarity(word1.getWord(), word2.getWord());

            }
        }
        if(similarityScore < 0 )
            similarityScore = 0 ;

            return similarityScore;
    }
    public double calculateCombinedSimilarityScore(Word word1 , Word word2) throws SLIB_Exception, IOException {

        double wordnet_similarity_score = 0.0; double umls_similarity_score = 0;
        double result = 0 ;
        /*
        *EGER WORDNETTE ISTENILEN SKOR YOKSA, SIFIR DONDU ISE UMLS-SIM CALISTIRILMALI.
         * WEIGHTED SCORE YAPILABILIR, EGER UMLS'DE MATCH YAPILDIYSA AGIRLIGI DAHA FAZLA OLMALI IKI KAT FALAN
          *
          * IDF SKORLARI HESABA KATILABILIR
          * AYNI ZAMANDA MAKALEDEKI YONTEME BENZER SEKILDE
          * KELIME ORDERLARI DA HESABA KATILABILIR
          * EGER BASARILI CIKARSA,
          * KELIME ORDERLARINI SUPERVISED YAKLASIMIMIZDA DENEYECEGIZ
        *
        * */
        WordNetSimilarity wordNet_similarity_measure = new WordNetSimilarity();
        wordnet_similarity_score = wordNet_similarity_measure.getSimilarity(word1.getWord(), word2.getWord());

        if (word1.getWord().equalsIgnoreCase(word2.getWord()) || word1.getWord().contains(word2.getWord()) ||
                word2.getWord().contains(word1.getWord())) {
            if(wordnet_similarity_score == 0 && word1.isInUmls() && word2.isInUmls())
                result = 1;
            else
                result = 1;
        }
        else {

            if(!getPair_score().containsKey(word1.getWord() + "-" + word2.getWord()) &&
                        !getPair_score().containsKey(word2.getWord() +"-" + word1.getWord())) {


                UmlsSimilarity umls_similarity_measure = new UmlsSimilarity();
                umls_similarity_score = umls_similarity_measure.getSimilarity(word1.getWord(), word2.getWord());

//                System.out.println(word1.getWord() + " - " + word2.getWord() + " : " + umls_similarity_score);
                getPair_score().put(word1.getWord() + "-" + word2.getWord(), umls_similarity_score);


                if (umls_similarity_score <= 0)
                    umls_similarity_score = 0;

            }

            else{
                if(getPair_score().containsKey(word1.getWord() + "-" + word2.getWord()))
                        umls_similarity_score = getPair_score().get(word1.getWord()+"-"+word2.getWord());
                else umls_similarity_score = getPair_score().get(word2.getWord()+"-"+word1.getWord());

            }
            if(wordnet_similarity_score > 0 && umls_similarity_score <= 0)
                result = wordnet_similarity_score;
            else if(umls_similarity_score >0 && wordnet_similarity_score <= 0)
                result = umls_similarity_score;
            else result = (umls_similarity_score + wordnet_similarity_score)/2;

        }



        return result;
    }


    public Vector<Double> constructVectorWordnet(Sentence sentence, HashSet<Word> dictionary){
        Vector<Double> vector = new Vector<Double>();
        int vectorIndex = 0;
        for(Word word: dictionary) {
            List<Double> scoresList = new ArrayList<Double>();
            if (sentence.getStringWords().contains(word.getWord())) {
                vector.add(vectorIndex, 1.0);
                vectorIndex++;

            } else {
                for (Word s : sentence.getWords()) {
                    if(!word.isStopWord()) {
                        double simScore = 0;
                        try {
                            simScore = calculateOnlyWordnetScores(s, word);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        scoresList.add(simScore);
                        if (simScore == 1.0)
                            break;
                    }
                }
                Collections.sort(scoresList);
                vector.add(vectorIndex, scoresList.get(scoresList.size() - 1));
                vectorIndex++;
            }
        }
        return  vector;

    }

    public Vector<Double> constructVectorForSentence(Sentence sentence, HashSet<Word> dictionary) throws SLIB_Exception, IOException {

        /*LISTEYI DOGRU SORT EDEMEDIK DUZELTME VE KONTROL YAP!!
         *  */

        Vector<Double> vector = new Vector<Double>();
        int vectorIndex = 0;
        for(Word word: dictionary) {
            List<Double> scoresList = new ArrayList<Double>();
            if (sentence.getStringWords().contains(word.getWord())) {
                vector.add(vectorIndex, 1.0);
                vectorIndex++;

            } else {
                for (Word s : sentence.getWords()) {
                    if(!word.isStopWord()) {
                        double simScore = calculateCombinedSimilarityScore(s, word);
                        scoresList.add(simScore);
                        if (simScore == 1.0)
                            break;
                    }
                }
                Collections.sort(scoresList);
                vector.add(vectorIndex, scoresList.get(scoresList.size() - 1));
                vectorIndex++;
            }
        }
        return  vector;
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
//        phrase = phrase.replace(" " , "");
        phrase = phrase.replaceAll("\\(", "");
        phrase = phrase.replaceAll("\\)", "");
        phrase = phrase.replaceAll("\\[", "");
        phrase = phrase.replaceAll("\\]", "");
        phrase = phrase.replaceAll("\\*", "");
        phrase = phrase.replaceAll("/", "");
        phrase = phrase.replaceAll("\\?", "");


        return phrase.toLowerCase();
    }

    public static Sentence returnSentence(String sentence, List<String> stopWordsList){
        Sentence res = new Sentence();
        res.setStringWords(new LinkedList<String>());
        res.setWords(new LinkedList<Word>());
        String[] split = sentence.split("\\s+");
        int index = 0;
        for(String s: split){
            s = replacePunctuations(s);
         //   System.out.println("TO BE ADDED WORD: "  + s);
            res.getStringWords().add(index, s.toLowerCase());
            Word newW = new Word();
            if(stopWordsList.contains(s))
                newW.setStopWord(true);
            else newW.setStopWord(false);

            newW.setWord(s.toLowerCase());
            res.getWords().add(index, newW);
        }

        return res;
    }

    public String removeStopWordsFromSentence(String sentence){
        String split[] = sentence.split("\\s+");

        String filteredSentence = "";
        for(String s: split){
            s = replacePunctuations(s);
            if(!stopWordsList.contains(s)){
                if(!filteredSentence.equals(""))
                    filteredSentence = filteredSentence + " " + s;
                else filteredSentence = s;
            }
        }

        return filteredSentence;
    }


    public double getSimilarityForWordnet(String sentence1, String sentence2) throws SLIB_Exception, IOException {

        Sentence mappedSentence1 = returnSentence(sentence1, stopWordsList);
        Sentence mappedSentence2 = returnSentence(sentence2, stopWordsList);
        //  returnSentence dan sonra da noktalama kaldırılmıs ve stop word bilgisi eklenmis oldu
        HashSet<Word> dictionary  = constructDictionary(mappedSentence1, mappedSentence2);

        Vector<Double> vector1 = constructVectorWordnet(mappedSentence1, dictionary);
        Vector<Double> vector2 = constructVectorWordnet(mappedSentence2, dictionary);

        CosineSimilarity similarityMeasure = new CosineSimilarity(vector1, vector2);
        double similarityScore = similarityMeasure.calculateDistanceAmongVectors();
        return similarityScore;


    }

    public double getSimilarity(String sentence1, String sentence2) throws SLIB_Exception, IOException {

//        Sentence mappedSentence1 = getMetamapResult(removeAllNonAsciiLetters(sentence1), stopWordsList);
//        Sentence mappedSentence2 = getMetamapResult(removeAllNonAsciiLetters(sentence2),stopWordsList);
//
//        if(mappedSentence1.getStringWords().size() == 0 || mappedSentence2.getStringWords().size() == 0){
//            System.out.println("METAMAP RESULT");
//            return 0;
//        }
//        //METAMAP i cagirdiktan sonra, eklenen kelimelerde, noktalama işaretleri kaldirilmis ve stop word bilgisi eklenmis oluyor
        Sentence mappedSentence1 = returnSentence(sentence1, stopWordsList);
        Sentence mappedSentence2 = returnSentence(sentence2, stopWordsList);
      //  returnSentence dan sonra da noktalama kaldırılmıs ve stop word bilgisi eklenmis oldu
        HashSet<Word> dictionary  = constructDictionary(mappedSentence1, mappedSentence2);

        Vector<Double> vector1 = constructVectorForSentence(mappedSentence1, dictionary);
        Vector<Double> vector2 = constructVectorForSentence(mappedSentence2, dictionary);

        CosineSimilarity similarityMeasure = new CosineSimilarity(vector1, vector2);
        double similarityScore = similarityMeasure.calculateDistanceAmongVectors();
        return similarityScore;
    }

    public HashMap<String, Double> getPair_score() {
        return pair_score;
    }

    public void setPair_score(HashMap<String, Double> pair_score) {
        this.pair_score = pair_score;
    }

    public double getSimilarityForUMLS(String s1, String s2) {
        Sentence mappedSentence1 = returnSentence(s1, stopWordsList);
        Sentence mappedSentence2 = returnSentence(s2, stopWordsList);
        //  returnSentence dan sonra da noktalama kaldırılmıs ve stop word bilgisi eklenmis oldu
        HashSet<Word> dictionary  = constructDictionary(mappedSentence1, mappedSentence2);

        Vector<Double> vector1 = constructVectorForUMLS(mappedSentence1, dictionary);
        Vector<Double> vector2 = constructVectorForUMLS(mappedSentence2, dictionary);

        CosineSimilarity similarityMeasure = new CosineSimilarity(vector1, vector2);
        double similarityScore = similarityMeasure.calculateDistanceAmongVectors();
        return similarityScore;
    }

    private Vector<Double> constructVectorForUMLS(Sentence mappedSentence1, HashSet<Word> dictionary) {

        Vector<Double> vector = new Vector<Double>();
        int vectorIndex = 0;
        for(Word word: dictionary) {
            List<Double> scoresList = new ArrayList<Double>();
            if (mappedSentence1.getStringWords().contains(word.getWord())) {
                vector.add(vectorIndex, 1.0);
                vectorIndex++;

            } else {
                for (Word s : mappedSentence1.getWords()) {
                    if(!word.isStopWord()) {
                        double simScore = 0;
                        try {
                            simScore = calculateOnlyUmlsScores(s, word);
                        } catch (SLIB_Exception e) {


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        scoresList.add(simScore);
                        if (simScore == 1.0)
                            break;
                    }
                }
                Collections.sort(scoresList);
                vector.add(vectorIndex, scoresList.get(scoresList.size() - 1));
                vectorIndex++;
            }
        }
        return  vector;
    }
}