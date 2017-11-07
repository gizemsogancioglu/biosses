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

/**
 *
 * @author gizem
 */
public class MethodSynonyms {
    
    String fileName;
    ArrayList<String> methodSynonyms = new ArrayList<String>();
    
    public MethodSynonyms(String fileName){
    
        this.fileName = fileName;
        
    
    }
    
    public ArrayList<String> createList() throws FileNotFoundException, IOException{
    
        BufferedReader buff = new BufferedReader(new FileReader(new File(fileName)));
        String line;
        
        while((line = buff.readLine()) != null){
        
              
                //System.out.println(line + "");
                String split[] = line.split("\\s+");
                
                String word = "";
                for(int i = 0; i < split.length; i++){
                    if(i!=0){
                      split[i] = split[i].replaceAll("\\s", "");
                        word = word + split[i] + " ";
                      
                    }
                }
                word= word.substring(0, word.length()-1);
                methodSynonyms.add(word);
        }
        
        
        return this.methodSynonyms;
    }
    
}
