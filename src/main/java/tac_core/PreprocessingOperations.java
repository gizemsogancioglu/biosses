/*
Project     :  BiomedSum Shared Task
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  02.09.2014
*/
package tac_core;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
/*
 *
 * @author gizem
 */
public class PreprocessingOperations {
    public String citationFile;
    public LinkedHashMap<String, Citation> citationHash ;
  //  public static String annotatedOutput = "output/data/annotatedOutput.txt";
    public static ArrayList<String> keywords;

    public PreprocessingOperations(String fileName){
        citationFile = fileName;
        citationHash = new LinkedHashMap<String, Citation>();
        keywords = new ArrayList<String>();
    }
   
    public static String removeFirstLastSpaceChars(String marker){
            
        int i;
         for(i = 0; marker.charAt(i)== ' '; i++){}
         marker = marker.substring(i);
        
         for(i = marker.length()-1; i >= 0 && marker.charAt(i) == ' ' ; i--){}
         marker = marker.substring(0, i+1);
         
         return marker;
     }
     
     public static boolean isNumeric(String s) {  
    
         return s.matches("[-+]?\\d*\\.?\\d+");  

     }  
     
     public static String recursiveremoving(String citance, String marker, String ch1, String ch2){
         int beginIndex,endIndex=0;
         if(citance.contains(ch1)){
             beginIndex = citance.indexOf(ch1);
             if(citance.substring(beginIndex).contains(ch2)){
                 
                 endIndex = citance.substring(beginIndex).indexOf(ch2);
                 endIndex  = endIndex + beginIndex;
                 if(citance.substring(beginIndex, endIndex).contains(marker) && (!isNumeric(marker) || checkAlphabet(citance.charAt((citance.substring(beginIndex,endIndex).indexOf(marker)+beginIndex)-1)) == false && checkAlphabet(citance.charAt((citance.substring(beginIndex,endIndex).indexOf(marker)+beginIndex)-2)) == false)){
                     
                     String first = citance.substring(0, beginIndex);
                     String last = citance.substring(endIndex+1);
                     String reference = "REF";
                     citance = first  + reference  + last;
               
                     if(citance.contains(ch1))
                         beginIndex = citance.indexOf(ch1)-1;
                     
                     else return citance;
                     }
                 
                 else {
                     if(citance.substring(endIndex).contains(ch1)){
                         beginIndex = citance.substring(endIndex).indexOf(ch1)+ endIndex-1;
                     }
                     else return citance;
                 
                 }
         
             }
         }
         else return citance;

       
        return citance.substring(0,beginIndex) + recursiveremoving(citance.substring(beginIndex), marker, ch1, ch2);
     }
     
     public static boolean checkAlphabet(char ch){
     
         if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
              //System.out.println(ch + "\n");
             return true;}
         else{
            
             return false;
             
         }
     }
     
     public static String removeCitationMarkerFromCitationText(String marker, String citation){
         marker = removeFirstLastSpaceChars(marker);
         citation = recursiveremoving(citation, marker, "(", ")");
        
         citation = recursiveremoving(citation, marker, "[", "]");
         
         return citation;
     }
     
     public void setCitationFacet(String offset, Citation newcitation){
     
         if(offset.toLowerCase().contains("result"))
             newcitation.getFacet().add(FacetEnum.Result);
         else if(offset.toLowerCase().contains("discussion"))
             newcitation.getFacet().add(FacetEnum.Discussion);
         else if (offset.toLowerCase().contains("method"))
             newcitation.getFacet().add(FacetEnum.Method);
         else if(offset.toLowerCase().contains("hypothesis"))
             newcitation.getFacet().add(FacetEnum.Hypothesis);
         else if(offset.toLowerCase().contains("implication"))
             newcitation.getFacet().add(FacetEnum.Implication);
        
     }
     
    
    /* public Citation fillReferenceOffset(Citation newCitation) throws FileNotFoundException, IOException{
         
         BufferedReader buffer = new BufferedReader(new FileReader(new File(annotatedOutput)));
         
         String line;
         
         while((line = buffer.readLine())!=null){
     
             if(line.contains("Reference Offset")){
                 
                 int begin = line.indexOf("Reference Offset");
                 line = line.substring(begin);
                 begin = line.indexOf(":");
                 int end = line.indexOf("|");
                 line = line.substring(begin+2, end);
                 
                 int middle = line.indexOf("-");
                 int firstOff = Integer.parseInt(line.substring(0, middle));
                 int lastOff = Integer.parseInt(line.substring(middle+1));
                 
                 Offset newOff = new Offset(firstOff, lastOff);
                 ArrayList<Offset> list = new ArrayList<>();
                 list.add(newOff);
                 newCitation.getReferenceOffset().add(list);
                 
               //  System.out.println(firstOff +" " + lastOff + "\n");
                 
             }
     }
         
     return newCitation;
     }*/
     
     
  /*  public static String returnNonExplicitCitingSentences(Citation newcitation, String text) throws FileNotFoundException, IOException{
    
       File citationFile = new File(newcitation.getCitingArticle());String result = "";
        try (BufferedReader readCitingBuffer = new BufferedReader(new FileReader("output/splittedtext/"+ citationFile + "_split"))) {
            String line;String preLine=""; String nextLine="";
            while((line=readCitingBuffer.readLine())!=null){
                System.out.println(line);
                
                if(line.contains(text)){
                    line = readCitingBuffer.readLine();
                    nextLine = line;
                    break;
                }
                preLine = line;
            }
            
            result = preLine + text + nextLine;
        }
        catch(Exception e){
            
        return text;
        }
        
       return result;
    }*/
    public void fillAllFeatures(BufferedReader buffer, int currentAnnotationIndex ,String key) throws IOException{
    
        String line;
        String citingArticleName;
        String citationMarker = ""; 
    
        while((line=buffer.readLine()) != null){
            if(!(line.equals("\n") || line.equals("") || line.equals(" "))){
              
                Citation newcitation ;
                if(currentAnnotationIndex == 0)
                    newcitation = new Citation();
                else {
                    int index = line.indexOf("Citance Number: ");
                    int lastIndex = line.indexOf("|", index);
                    String citanceIndex = line.substring(index + "Citance Number: ".length(), lastIndex);
                    newcitation = citationHash.get(key + "_" + citanceIndex.replaceAll(" ", ""));
                }
                String citationKey = "";
                
                while(line.contains("|")){
                    int index = line.indexOf("|");
                    String firstPart = line.substring(0,index);
                    String lastPart = line.substring(index+1);
                    line = lastPart;
            
                    int i = firstPart.indexOf(":");
                    String offset = firstPart.substring(i+1);
         
                    if(firstPart.contains("Topic ID")){
                        
                        offset = removeFirstLastSpaceChars(offset);
                        newcitation.setTopicID(offset);
                        citationKey = offset;
                    }
                  
                    else if(firstPart.contains("Citance Number")){
                    
                        newcitation.setCitanceNumber(offset);
                        citationKey = citationKey + "_" + offset.replace(" ", "");
                        
                    }
                  
                     else if(firstPart.contains("Reference Article")){
                         offset = removeFirstLastSpaceChars(offset);
                         newcitation.setReferenceArticle(offset);
                    }
                     
                    else if(firstPart.contains("Citing Article")){
                
                        offset = offset.replaceAll("\\s+", "");
                        citingArticleName = offset;
                        newcitation.setCitingArticle(citingArticleName);
                       
                    }
                
                     else if(firstPart.contains("Citation Marker Offset")){
                
                        offset = offset.replaceAll("\\s+", "");
                        int offset_1 = Integer.parseInt(offset.substring(0,offset.indexOf("-")));
                        int offset_2 = Integer.parseInt(offset.substring((offset.indexOf("-"))+1));
                        Offset newOffset = new Offset(offset_1, offset_2);
                        newcitation.setCitationMarkerOffset(newOffset);
                    }
                    else if(firstPart.contains("Citation Marker:")){
                    
                        offset = removeFirstLastSpaceChars(offset);
                        citationMarker = offset;
                        newcitation.setCitationMarker(citationMarker);
                    }
                 else if(firstPart.contains("Citation Offset")){
          
                        offset = offset.replaceAll("\\s+", "");
                        int offset_1 = Integer.parseInt(offset.substring(0,offset.indexOf("-")));
                        int offset_2 = Integer.parseInt(offset.substring((offset.indexOf("-"))+1));
                        Offset newOffset = new Offset(offset_1, offset_2);
                        newcitation.setCitationOffset(newOffset);
                 }
                 
                 else if(firstPart.contains("Citation Text")){
                 
                     offset=removeFirstLastSpaceChars(offset);
                     if(offset.charAt(offset.length()-1)!= '.'){
                        offset = offset + ".";
                     } 
                        newcitation.getCitationText().add(offset);
                       
                    }
                    
                    
                   else if(firstPart.contains("Reference Offset")){
                    
                         String split[] = offset.split(",");
                         for (String split1 : split) {
                             int in1 = split1.indexOf("'");
                             int in2 = split1.indexOf("'", in1+1);
                             split1 = split1.substring(in1+1, in2);
                             split1 = split1.replaceAll("\\s+", "");
                             int offset_1 = Integer.parseInt(split1.substring(0,split1.indexOf("-")));
                             int offset_2 = Integer.parseInt(split1.substring((split1.indexOf("-"))+1));
                             Offset newOffset = new Offset(offset_1, offset_2);
                             ArrayList<Offset> newList = new ArrayList<Offset>();
                             newList.add(newOffset);
                             newcitation.getReferenceOffset().add(newList);
                             //System.out.println(citationKey + " " + currentAnnotationIndex + " Offset:" + offset_1 + " " + offset_2);
                        
                        }
                    }
                     
                     else if (firstPart.contains("Reference Text")){
                     
                         offset=removeFirstLastSpaceChars(offset);
                         if(offset.charAt(offset.length()-1)!= '.'){
                            offset = offset + ".";
                         } 
                        // System.out.println(offset +"\n\n");
                         ArrayList<String> list = new ArrayList<String>();
                         while(offset.contains("...")){
                            int indexofnewtext = offset.indexOf("...");
                            String sub1 = offset.substring(0, indexofnewtext);
                            sub1 = removeFirstLastSpaceChars(sub1);
                             sub1 = sub1 +".";
                           
                            offset = offset.substring(indexofnewtext+3);
                          
                            list.add(sub1);
                        //     System.out.println(sub1);
                         }
                         offset = removeFirstLastSpaceChars(offset);
                         list.add(offset);
                   //      System.out.println(offset + "\n\n");
                         newcitation.getReferenceText().add(list);
                      }
                     
                    else if(firstPart.contains("Facet")){
                       setCitationFacet(offset, newcitation);
                        
                    }
                    
                }
                
                citationHash.put(citationKey, newcitation);
                if(currentAnnotationIndex == 0){
                    keywords.add(citationKey);
                }
            }     
        }
    }
    
    public boolean isAlphabetic(char ch){
    
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')){
        
            return true;
        }
        else
            return false;
        
    
    }
    public String removeCitationMarker(String text, String marker){
    
        String processed_text = "";
       
        if(isNumeric(marker)){
             int index = text.indexOf(marker);
             if(isAlphabetic(text.charAt(index-1)) || isAlphabetic(text.charAt(index+marker.length()))){
            
                 index = text.indexOf(marker, index+1);
            
             }
            
             if(index != -1 && index != 0 ){
          
              while(index > 2 && text.charAt(index-1) == ' '){
               index--;
           }
          }
             
          if(text.charAt(index-1) == '(' ){
              int paranthesisIndex = text.indexOf(")", index-1);
              if(paranthesisIndex != -1){
                String sub = text.substring(index-1, paranthesisIndex+1);
                processed_text = text.replace(sub, "");
              }
            //  System.out.println(processed_text);
          }
          else if(text.charAt(index-1)== '['){
              int paranthesisIndex = text.indexOf("]", index-1);
             // System.out.println(text);
              if(paranthesisIndex != -1){
                String sub = text.substring(index-2, paranthesisIndex+1);
                processed_text = text.replace(sub, "");
              }
              
          }
          else
              processed_text = text.replace(marker, "");
          
          }
        
       
        else{
           //System.out.println(text);
           int index = text.indexOf(marker);
           if(index == -1 || index == 0 ){
               processed_text = text.replace(marker, "");
           }
               
           else{
           while(index > 2 && text.charAt(index-1) == ' '){
               index--;
           }
              
           if(text.charAt(index-1) == '('){
       
               int paranthesis = text.indexOf(")",index);
               String substring = text.substring(index-1, paranthesis+1);
               processed_text = text.replace(substring, "");
       
              
           }
           else
               processed_text = text.replace(marker, "");
       }
        }
        
       return processed_text;
    }
    
    public void findMaxFacet(Citation obj){
        
        ArrayList<FacetEnum> facetList = obj.getFacet();
        int[] countArray = new int[7];int max = 0;FacetEnum maxF = FacetEnum.Method;
        for(FacetEnum f:facetList){
          //  System.out.println(f);
           countArray[f.getVal()-1] = (++countArray[f.getVal()-1]);
           int c = (countArray[f.getVal()-1]);
            if(c > max)
            {
                max = c;
                maxF = f;
                
            }
            
        }
        obj.setMaxProbableFacet(maxF);
    }
    
    public void fillMaxProbableFacet(){
    
        Iterator<String> it = citationHash.keySet().iterator();
        while(it.hasNext()){
            Citation obj = citationHash.get(it.next());
            findMaxFacet(obj);
        }
    
    }
    
    /*
    Annotated edilmi? ayn? citation i?in olan dosyalarda: 
    Citation textlerin hepsinin ayn? oldu?unu varsayarak sadece ilk annotated file-?n citation text'ini tutuyorum
    Reference Text Arraylist olarak tutuluyor, ka? tane annotation varsa, annotate eden ki?ilerin ismi tutulmadan saklan?yor.
    Reference offset: Arraylist Stringleri Arraylist olarak tutuyor.
    Facet: Facet Arraylist <Enum> olarak tutuluyor.
    Preprocess methodunda, ama?, istenilen ?ekilde her citation a y?nelik t?m bilgilerin tutuldu?u bir hash elde etmek.
    fillAllFeatures methodunda, o annotated file i?in olan ?zellikler dolduruluyor.
    E?er, ayn? citation i?in bilgiler daha ?nce girilmi?se, o citation nesnesini ba?tan olu?turmak yerine, ?ncekini get methodu ile
    elde edip, ?zellikleri eklemek ?nemli!
    */
    public LinkedHashMap<String, Citation> Preprocess() throws IOException{
         /*
         /This method read the data from the annotation files, and save them in a new text file... 
         /citationTextFile ----> save the all citations in a new text file, we give  this file to the GENIASENTENCE splitter as an input.
         /Then, The output of a geniass tool is given the geniatagger as an input. Finally, we have tokenized and tagged words..
         */
           
        BufferedReader buffer = null;
        int startIndex = 1;  int endIndex = 20; String folder_name = "_TRAIN";
        for(int iFile = startIndex; iFile <= endIndex; iFile++){
             String indexF = String.valueOf(iFile);
             if(iFile < 10)
                 indexF = "0" + indexF;
             String annotatedFile = "C:\\Users\\orhan\\Desktop\\WorkingProjectFolder\\TAC\\data\\D14" + indexF + folder_name +"\\Annotation";
             File mainFolder =  new File(annotatedFile);
             File[] annotatedList = mainFolder.listFiles();
             int currAnnotationIndex = 0;
             String key = "D14" + indexF + folder_name;
             for(File currentAnnotatedFile : annotatedList){
                 if(currentAnnotatedFile.isFile()){
                     buffer = new BufferedReader(new FileReader(new File(currentAnnotatedFile.getAbsolutePath())));
                     if(currentAnnotatedFile.getName().contains("D14" + indexF)
                              && !currentAnnotatedFile.getName().contains("~")){
                        fillAllFeatures(buffer,currAnnotationIndex , key);
                        currAnnotationIndex++;
                     }
                     buffer.close();
                 }
             }
         }
        fillPreprocessedText();
        fillMaxProbableFacet();
       return citationHash;

    }
    public void fillPreprocessedText() throws IOException{
    
        Iterator<String> it = citationHash.keySet().iterator();
        BufferedReader buff = new BufferedReader(new FileReader(new File(citationFile)));
        String line;
        while((line=buff.readLine())!=null){
            Citation c = citationHash.get(it.next());
            c.setProcessed_citation_text(line);
        }
        buff.close();
       /* while(it.hasNext()){
            Citation c = citationHash.get(it.next());
            int max = 0; String preprocessedText = "";
            for(int i =  0; i < c.getCitationText().size(); i++){
                if(c.getCitationText().get(i).length() > max){
                    max = c.getCitationText().get(i).length();
                    preprocessedText = c.getCitationText().get(i);
                }
            }
            preprocessedText = removeCitationMarker(preprocessedText, c.getCitationMarker());
        
            c.setProcessed_citation_text(preprocessedText);
         //   buff.write(preprocessedText);
           // buff.newLine();
        }*/
        //buff.close();
       
        it = citationHash.keySet().iterator();
        //buff = new BufferedWriter(new FileWriter(new File("classificationData/reference.txt")));
        while(it.hasNext()){
            Citation c = citationHash.get(it.next());
            int max = 0;  String preprocessedText = "";
            for(int i =  0; i < c.getReferenceText().size(); i++){
                for(int j =  0; j < c.getReferenceText().get(i).size(); j++){
                
                    if(!preprocessedText.contains(c.getReferenceText().get(i).get(j)))
                        preprocessedText = preprocessedText + " " + c.getReferenceText().get(i).get(j);
                
                }
                
            }
             c.setProcessed_reference_text(preprocessedText);
          //  buff.write(preprocessedText);
            //buff.newLine();
        }
         //buff.close();
        
        
       
    
        
    }
}
   