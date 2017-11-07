/*
Project     :  Master Thesis
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  22.05.2015
********Main Class************
*/
package tac_core;
import java.io.IOException;
import java.util.LinkedHashMap;


public class Main {
    //GENIA TAGGER splitted sentences and tagged the words in a sentence. This file is a file which is produced after running genia tagger.
    //File is saved in the output folder. It can be changed according to programmer preferences.   
    public static LinkedHashMap<String, Citation> citationHash;


 /*   public static void Adw_comparision_test(){

        //the two lexical items to be compared
        String text1 = "i love you";
        String text2 = "i like you";

//types of the two lexical items
        ItemType srcTextType = ItemType.SURFACE;
        ItemType trgTextType = ItemType.SURFACE;

//if lexical items has to be disambiguated
        DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

//measure for comparing semantic signatures
        SignatureComparison measure =  new WeightedOverlap();


        ADW pipeLine = new ADW();

        double similarity = pipeLine.getPairSimilarity(text1, text2,
                disMethod, measure,
                srcTextType, trgTextType);
        System.out.println(similarity);

    }*/
    private static void main(String[] args) throws IOException{
        
        /**************READ ANNOTATION FILES(PREPROCESSING)********************/
        
        FileOperations file = new FileOperations();
    //    file.fillHashTables();

    //  Adw_comparision_test();
        
    
    /******************MATCHING REFERENCE SPAN******************************/
        //MatchingReferenceSpanOperations matchingOp  = new MatchingReferenceSpanOperations();
       // matchingOp.matchReferenceText("baseline");
         /***********************************************************************/
        
       
        /**********************FACET CLASSIFICATION**************0*************/
       // SvmClassifier svmClassfy = new SvmClassifier();
        //svmClassfy.run_k_fold_svm(10);
        
        /********************REFERENCE SPAN DETECTION**************************/
      
        
        /*******MeSH XML -  SEMANTIC SIMILARITY AND TOOLKIT**********************/
       /*  URIFactory factory = URIFactoryMemory.getSingleton();
            org.openrdf.model.URI meshURI = factory.getURI("http://www.nlm.nih.gov/mesh/");
 
            G meshGraph = new GraphMemory(meshURI);
 
            GDataConf dataMeshXML = new GDataConf(GFormat.MESH_XML, "/home/gizem/WorkingProjectFolder/MeSH-KnowledgeBased/desc2014.xml"); // the DTD must be located in the same directory
            GraphLoaderGeneric.populate(dataMeshXML, meshGraph);
 
            System.out.println(meshGraph);
 
             // we first configure a pairwise measure
            ICconf icConf = new IC_Conf_Topo(SMConstants.FLAG_ICI_SANCHEZ_2011);
            SMconf measureConf = new SMconf(SMConstants.FLAG_SIM_PAIRWISE_DAG_NODE_LIN_1998, icConf);
 
            // We define the semantic measure engine to use 
            SM_Engine engine = new SM_Engine(meshGraph);
 
            // We compute semantic similarities between concepts
            // e.g. between Paranoid Disorders (D010259) and Schizophrenia, Paranoid (D012563)
            org.openrdf.model.URI c1 = factory.getURI("http://www.nlm.nih.gov/mesh/D010259"); // Paranoid Disorders
            org.openrdf.model.URI c2 = factory.getURI("http://www.nlm.nih.gov/mesh/D012563"); // Schizophrenia, Paranoid
 
            // We compute the similarity
            double sim = engine.compare(measureConf, c1, c2);
            System.out.println("Sim " + c1 + "\t" + c2 + "\t" + sim);
            System.out.println(meshGraph.toString());*/
        
        /*********************************************************************/
        
     }
}
        
