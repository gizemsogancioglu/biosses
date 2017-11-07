/*
Project     :  Biomedd Sum Shared Task
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  02.09.2014
*/

package tac_core;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.tartarus.martin.Stemmer;
import semanticSimilaritySystems.core.StopWords;

/**
 *
 * @author gizem
 */
public class TaggedSentences {
    
    HashMap<String, Citation> hash;
    String fileName;ArrayList<String> keyword;
    public TaggedSentences(String fileName,  HashMap<String, Citation> hash
     ) throws FileNotFoundException, IOException{
    
        this.hash = hash;
        this.fileName = fileName;
        this.keyword = PreprocessingOperations.keywords;
        
    }
    
     public static String preprocess(String s){
        
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
        s = s.replaceAll("\\.", "");
        s = s.replaceAll("/", "");
        s = s.replaceAll("\"", "");
        s = s.replaceAll("\'", "");
        s = s.replaceAll("~", "");
        s = s.replaceAll("=", "");
        s = s.replaceAll("<", "");
        s = s.replaceAll(">", "");
        s = s.replaceAll("<", "");
        s = s.replaceAll("|", "");
        s = s.replaceAll("\\{", "");
        s = s.replaceAll("\\}", "");
     
return s;

}
    
    public Word fillTheWord(String line){
     
        Word newWord = new Word();
        String split[] = line.split("\\s+");
       
        newWord.setWord(split[0].toLowerCase());
        String preprocessed_word = preprocess(newWord.getWord());
        newWord.setProcessedWord(preprocessed_word);
        String baseWord = split[1].toLowerCase();
        
        
        Stemmer stem = new Stemmer();
        stem.add(preprocessed_word.toCharArray(), preprocessed_word.length());
        stem.stem();
        newWord.setStemmedWord(stem.toString());
        
        newWord.setBaseWord(baseWord);
        newWord.setPosTag(split[2]);
        newWord.setChunkTag(split[3]);
        newWord.setNETag(split[4]);
        
        return newWord;
        
    }
    
     public boolean stopWordsCheck( Word w){
         
         return !(StopWords.stopWords.contains(w.getBaseWord()) || StopWords.stopWords.contains(w.getWord()));
     
     }
     public static boolean isalphanumeric(String s){
    
        return s.matches("[^a-zA-Z0-9]");
    
    }
    public static boolean isNumeric(String s) {  
    
         return s.matches("[-+]?\\d*\\.?\\d+");  

     }  
    
    public void preprocessTaggedSentences(boolean isCitation) throws FileNotFoundException, IOException{
    
        
        BufferedReader taggedBuffer = new BufferedReader(new FileReader(new File(fileName)));
      
        String line;Citation citationobject;
        Word newWord =null ;ArrayList<Word> wordList ;
        String preWord = "";
        String key = keyword.get(0);
        citationobject = hash.get(key);
        int index = 0;
        wordList = new ArrayList<Word>();
        while((line=taggedBuffer.readLine()) != null){
        
            if(line.equals("")){
              
                //wordList = new ArrayList<>();
               // if(citationobject.getProcessed_citation_text().toLowerCase().endsWith(preWord  +newWord.getWord().toLowerCase()) || 
               //         citationobject.getProcessed_citation_text().toLowerCase().endsWith(preWord + " " +newWord.getWord().toLowerCase())){
                 //   System.out.println(index + " OK " +preWord +newWord.getWord());
                     index++;
                key = keyword.get(index);
             
                citationobject = hash.get(key);
             
             //   System.out.println(citationobject.getCitationText());
               
            }
         
            else{
                
                if(newWord!=null)
                    preWord = newWord.getWord();
                 
                newWord = fillTheWord(line);
                
                if(isCitation == true && stopWordsCheck(newWord) && newWord.getProcessedWord().length() > 1 
                        && !isNumeric(newWord.getProcessedWord()) && !isalphanumeric(newWord.getProcessedWord())){
                 
                    citationobject.getWordList().add(newWord);
                }
             
            }
        }
       
    }
    
}
