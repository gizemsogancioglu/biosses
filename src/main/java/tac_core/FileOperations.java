/*
Project     :  BiomeddSum Shared Task
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  02.09.2014
*/
package tac_core;
import semanticSimilaritySystems.core.StopWords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class FileOperations {

    public static String geniaTaggedCitationFile = "C:\\Users\\orhan\\Desktop\\WorkingProjectFolder\\classificationData\\taggedcitation.txt";
    public static String citationFile = "C:\\Users\\orhan\\Desktop\\WorkingProjectFolder\\classificationData\\citation.txt";
    public static String geniaTaggedReferenceFile = "C:\\Users\\orhan\\Desktop\\WorkingProjectFolder\\classificationData\\referenceTagged.txt";
    //public static String svmInputTrain =  "output/svmInputTrainFile.txt";
    //public static String svmInputTest =  "output/svmInputTestFile.txt";
    //public static String svmoutput  = "output/svmoutput.txt";
    
    public static void reprocess_reference_offset() throws FileNotFoundException, IOException{
    
        Iterator<String> it = Main.citationHash.keySet().iterator();
        BufferedReader splitBuffer;
           
        while(it.hasNext()){
            String key = it.next();
            Citation c = Main.citationHash.get(key);
            splitBuffer = new BufferedReader(new FileReader(new File("taggedFiles/" + c.getReferenceArticle() + "_split")));
           String line;int charOff = 0 ; int preOff = 0 ;
            for(int i = 0 ; i < c.getReferenceText().size(); i++){
                for(int j = 0 ; j <c.getReferenceText().get(i).size(); j++){
                
                    String text = c.getReferenceText().get(i).get(j);
                    boolean find = false;
                     while((line=splitBuffer.readLine())!=null){
                          preOff = charOff;
                          if(line.equals("") || line.equals(" "))
                              charOff += 1;
                          else
                            charOff += line.length();
                  
                          if(!line.equals("") && !line.equals(" ")){
                        //      System.out.println(line + " " + line.length());
                            if(text.toLowerCase().contains(line.toLowerCase())){
                            System.out.println("Line: " +preOff +" "+line.toLowerCase() + "\nText:" + text.toLowerCase() +" " + c.getReferenceOffset().get(i).get(0).getOff1());
                            find = true;
                            //System.out.println("\n\n");
                            break;
                            
                        
                            }
                         
                          }
                         
                     }
                    splitBuffer.close();
                     splitBuffer = new BufferedReader(new FileReader(new File("taggedFiles/" + c.getReferenceArticle() + "_split")));
                     charOff = 0 ;
                  }
            }
        }
    }
   
    public void fillHashTables() throws IOException{
         /*CitationHash is filled, all citation texts, reference file names, offset values are saved... */
        //Geniatagger split the sentences and tag the each word, here we read the file and save the new properties belonging to Citation object 
        //according to verb,protein bla bla...
           PreprocessingOperations operate = new PreprocessingOperations(citationFile);
           Main.citationHash = operate.Preprocess();
      //     reprocess_reference_offset();
           StopWords stop = new StopWords();
          
           TaggedSentences taggedSentences = new TaggedSentences(geniaTaggedCitationFile, Main.citationHash);
           taggedSentences.preprocessTaggedSentences(true);
        
      //     TaggedSentences taggedReferences = new TaggedSentences(geniaTaggedReferenceFile, Main.citationHash);
      //     taggedReferences.preprocessTaggedSentences(false);
         
           
       }
       
         /*public static void writeOutput() throws IOException{
    
          
        BufferedWriter buffwriter = new BufferedWriter(new FileWriter(new File(annotatedOutput2)));
        
        for(int i =  0; i < Main.testKeyWords.size(); i++){
         
            String key = Main.testKeyWords.get(i);
           // System.out.println(key + "\n");
            Citation object = Main.citationHashForTest.get(key);
      
            Offset off = object.getReferenceOffset().get(0);
            buffwriter.write("Topic ID: "+object.getTopicID() + "  | " + "Citance Number: " + object.getCitanceNumber() + " | " +"Reference Offset: ['"+ off.getOff1() + "-" + off.getOff2() + "'] | " + "Reference Text: "+object.getReferenceText()
            +" | " +"Discourse Facet: " + object.getFacet() +"_Citation" + " | " + "Run ID: 1"  );
            buffwriter.newLine();
        }
        
          buffwriter.close();
    }
    
          public static void fillSvmResults() throws IOException{
         BufferedReader buff = new BufferedReader(new FileReader(new File(svmoutput)));
           String line;
           int i = 0 ;
           while((line= buff.readLine())!=null){
               String key = Main.testKeyWords.get(i);
               Citation obj = Main.citationHashForTest.get(key);
               i++;
               if(line.contains("6"))
                   obj.getFeatureLabel()[2]  = 6;
               else if(line.contains("5"))
                   obj.getFeatureLabel()[2]  = 5;
               else if(line.contains("4"))
                   obj.getFeatureLabel()[2]  = 4;
               else if(line.contains("3"))
                  obj.getFeatureLabel()[2]  = 3;
               else if(line.contains("2"))
                   obj.getFeatureLabel()[2]  = 2;
                else if(line.contains("1"))
                   obj.getFeatureLabel()[2]  = 1;
           }
       }
    
       public static int returnMaxProbClass(double[] p){
    
        double max = p[0];
        int index = 0;
        
        for(int i = 1 ; i < p.length; i++){
            
            if(p[i] >= max){
                max = p[i];
                index = i;
            }
        }
        
        return index+1;
    }
       public static int returnMaxVotedLabel(Citation c){
       
           double[] mostProbableLabels = new double[6];
           int maxLabel = 1 ;
           
           mostProbableLabels[c.getFeatureLabel()[0]-1]++;
           mostProbableLabels[c.getFeatureLabel()[1]-1]++;
           mostProbableLabels[c.getFeatureLabel()[2]-1] += 1.5;
            
           maxLabel = returnMaxProbClass(mostProbableLabels);
           return maxLabel;
       
           
       }
       public static int combinedTP = 0 ;
       public static int overallTestSentences = 0 ;
       public static void returnVotingCombiningClassifier(){
           
           Iterator<String> it = Main.citationHashForTest.keySet().iterator();
           while(it.hasNext()){
               String key = it.next();
               Citation test = Main.citationHashForTest.get(key);
               int label = returnMaxVotedLabel(test);
               if(label == test.getFacet().getVal()){
                   combinedTP++;
               }
               overallTestSentences++;
               
           }
           
           System.out.println("Voting Combining Classifier:  TP: " + combinedTP + " NumberOfCitingSentences: " + overallTestSentences + " Accuracy: " + (double)combinedTP / (double)overallTestSentences);
       
       }
    */
}
