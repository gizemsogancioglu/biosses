/*
Project     :  Biomedd Sum Shared Task
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  02.09.2014
*/

package similarityMeasures;


import tac_core.Word;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author gizem
 */
public class Vector {
    

    public  double[] vec;
    public ArrayList<Word> text;
    public int[] neTags;

    
    public Vector(ArrayList<Word> text){
    
        this.text = text;
       
    }
  
    public Vector(double[] vec){
        this.vec = vec;
    
    }
    
    
    public void returnVectorRepresentationOfText(ArrayList<String> dictionary, HashMap<String, Double> idf){
      
            double idf_val;
            vec = new double[dictionary.size()];
            neTags = new int[dictionary.size()];
            int count;
            for(int i = 0; i < dictionary.size(); i++){
      
                count = 0 ; 
                for(int k = 0 ; k < text.size(); k++){
                
                    if(text.get(k).getStemmedWord().equals(dictionary.get(i))){
                        count++;
                        if(!text.get(k).getNETag().equals("O")){
                            neTags[i] = 1;
                        //   System.out.println(text.get(k).getNETag());
                        }
                        else
                            neTags[i] = 0 ;
                        
                    }
                }
                if(idf.containsKey(dictionary.get(i).toLowerCase())){
                    idf_val = idf.get(dictionary.get(i).toLowerCase());
                 //   System.out.println(dictionary.get(i) +" "+ idf.get(dictionary.get(i)));
                }
                else
                    idf_val = Math.log10(1 + 491);
               
                
               // idf_val = 1;
                
                this.vec[i] = (Math.log10(count+1)) * idf_val ;
               // System.out.println(this.vec[i] +" " +count);
               
            }
        
     }
     

       
     
}
