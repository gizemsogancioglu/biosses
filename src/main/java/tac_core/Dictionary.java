/*
Project     :  Biomedd Sum Shared Task
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  02.09.2014
*/
//Naive Bayes Probability'i feature olarak kullanmak! 
package tac_core;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 *
 * @author gizem
 */
public class Dictionary {
    HashMap<String, Double> significiantdictionary;
    HashMap< String,Double> candidateWords;
    LinkedHashMap <String, Citation> hash;
    Double[] priorityClass;public static double allCitationsCount = 0 ;
    Double[] numberOfWordsInClass;
    ArrayList<String> vocabulary;
    public HashMap <String, Double> unigramProbabilities;
    
    /*
    6 classes priorities: Method, result, hypothesis, dataset, implication,discussion
    */
    
    public Dictionary(LinkedHashMap<String, Citation> hash){
        this.hash = hash;
        this.priorityClass = assignZeroProbabilities();
        this.numberOfWordsInClass = assignZeroProbabilities();
        significiantdictionary = new HashMap<String, Double>();
        candidateWords = new HashMap<String, Double>();
        unigramProbabilities = new HashMap<String, Double>();
        vocabulary = new ArrayList<String>();
    }
    public Double[] assignZeroProbabilities(){
    
        Double[] priority = new Double[6];
        for(int i = 0 ; i < priority.length; i++)
            priority[i] = 0.0;
        return priority;
    }
     
    public Double[] returnNumberOfWordsArray(){
    
        return numberOfWordsInClass;
    }
    
    public Double[] returnClassPriorities(){
        
        for(int i = 0 ; i < priorityClass.length; i++){
            priorityClass[i] = (priorityClass[i]+1)/allCitationsCount;
        }
        return this.priorityClass;
    }
    
    public HashMap<String, Double> extractSignificiantFeatures(){
     
        if(candidateWords.isEmpty())
            extractWordFeatures();
        double indexDictionary = 17;
        Iterator<String> keysetIterator = candidateWords.keySet().iterator();
     
        while(keysetIterator.hasNext()){
            String key = keysetIterator.next();
            double val = candidateWords.get(key);
            if(val > 20 && val < 100){
                //System.out.println(val);
                String[] split = key.split("\\s+");
                if(!significiantdictionary.containsKey(split[0])){
                    significiantdictionary.put(split[0], indexDictionary);
                  //  vocabulary.add(split[0]);
                   // System.out.println(split[0]);
                  
                }
                indexDictionary++;
            }
        }
       return this.significiantdictionary;
    
    }
    public ArrayList<String> returnVocabulary(){
        return this.vocabulary;
    }
    
    public HashMap<String, Double> returnCandidateWords(){
    
        if(candidateWords.isEmpty())
            candidateWords = extractWordFeatures();
    
        return candidateWords;
                    
    
    }
    public HashMap<String, Double> extractAllWordFeatures(Citation val,ArrayList<Word> list){
    
        for (Word wordList : list) {
                if(true){
                    
                  //  System.out.println(wordList.getBaseWord() + " " + wordList.getPosTag());
                    numberOfWordsInClass[val.getFacet().get(0).getVal()-1] ++;
                    String key = wordList.getBaseWord() + " " + val.getMaxProbableFacet().getVal();
                    if(candidateWords.containsKey(key)){
                        Double number = candidateWords.get(key);
                        number++;
                        candidateWords.remove(key);
                        candidateWords.put(key, number);
                    }
                    else{
                        candidateWords.put(key, 1.0);
                        vocabulary.add(wordList.getBaseWord());
                      //  System.out.println(key);
                    }
                }
        }
        return candidateWords;
       
    }

    public HashMap<String, Double> extractWordFeatures(){
       
        Iterator<String> keysetIterator = hash.keySet().iterator();
        while(keysetIterator.hasNext()){
            Citation val = hash.get(keysetIterator.next());
          //  priorityClass[val.getFacet().get(0).getVal() - 1] ++;
          //  allCitationsCount++;
           candidateWords = extractAllWordFeatures(val, val.getWordList());
           candidateWords = extractAllWordFeatures(val, val.getReferenceWordList().get(0));
        }
        return candidateWords;
    }
}
