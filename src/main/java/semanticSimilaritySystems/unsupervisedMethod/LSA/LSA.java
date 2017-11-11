package semanticSimilaritySystems.unsupervisedMethod.LSA;

import edu.ucla.sspace.mains.LSAMain;

/**
 * Created by gizem on 31.01.2016.
 */
public class LSA {

    public void runLSAOnCorpus(String fileName) throws Exception {

        /*LSA method is run on given corpus and produce the result according to option -binary or -text file.
        *This method learns the vectors from the corpus by using LSA S:Space implementation.
        * */

        String lsa_args[] = new String[5];
        lsa_args[0] = "-o";
        lsa_args[1] = "text";
        lsa_args[2] = "-d";
        lsa_args[3] = "C:\\Users\\orhan\\Documents\\intellijProjects\\resources\\corpus.txt";
        lsa_args[4] = "C:\\Users\\orhan\\Documents\\intellijProjects\\resources\\results.sspace";

        LSAMain.main(lsa_args);



    }
}
