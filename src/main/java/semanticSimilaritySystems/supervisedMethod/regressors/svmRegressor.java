package semanticSimilaritySystems.supervisedMethod.regressors;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.core.Instances;
import weka.core.WekaPackageManager;

import java.io.*;
import java.util.Random;

/**
 * Created by gizem on 10.04.2016.
 */
public class svmRegressor {


    public static void runSVMRegression() throws Exception {
        BufferedReader br = null;
        int numFolds = 10;
        br = new BufferedReader(new FileReader("rawData.arff"));
        Instances trainData = new Instances(br);
        trainData.setClassIndex(trainData.numAttributes() - 1);
        br.close();

        WekaPackageManager.loadPackages(false, true, false);
        AbstractClassifier classifier = (AbstractClassifier) Class.forName(
                "weka.classifiers.functions.supportVector").newInstance();
        String options = ("-S 3 -V 10 -T 0");
        String[] optionsArray = options.split(" ");
        classifier.setOptions(optionsArray);
        classifier.buildClassifier(trainData);

        Evaluation evaluation = new Evaluation(trainData);
        /*******************CROSS VALIDATION*************************/
        evaluation.crossValidateModel(classifier, trainData, numFolds, new Random(1));
        /***********************************************************/

        evaluateResults(evaluation);




    }
    public static void main(String[] args) throws Exception {

        runSVMRegression();
    }
    public static void evaluateResults(Evaluation evaluation){

        for(Prediction p: evaluation.predictions()){
            System.out.println(p.actual() + " " + p.predicted() );
        }


        System.out.println(evaluation.toSummaryString("\nResults\n======\n", true));
        //  System.out.println(evaluation.toSummaryString(evaluation.correlationCoefficient() + " " + evaluation.errorRate() + " " + evaluation.meanAbsoluteError() + " ");


    }

}
