package semanticSimilaritySystems.supervisedMethod.regressors;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.Utils;

import java.io.FileReader;
import java.util.Random;

/**
 * Created by gizem on 31.01.2016.
 */
public class MultiLayerPerceptron {
    public static void main(String[] args) throws Exception {
        MultiLayerPerceptron m  = new MultiLayerPerceptron();
        m.classify();

    }
    public  void classify() throws Exception {



        FileReader trainreader = new FileReader("rawData_biomedical.arff");


        Instances train = new Instances(trainreader);

        train.setClassIndex(train.numAttributes() - 1);

        double accuracy = 0 ;

            for (int i = 0; i < 10; i++) {
                MultilayerPerceptron mlp = new MultilayerPerceptron();
                mlp.setOptions(Utils.splitOptions("-L 0.3 -M 0.2 -N 500 -V 0 -S 0 -E 20 -H 4"));


                mlp.buildClassifier(train);

                Evaluation eval = new Evaluation(train);
                //evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1));
                eval.crossValidateModel(mlp, train, 10, new Random(1));
                // eval.evaluateModel(mlp, train);
                System.out.println(eval.toSummaryString("\nResults\n======\n", false));
                trainreader.close();
                accuracy += eval.correlationCoefficient();

            }

        System.out.println("Avg Correlation: " + accuracy/10);

    }
}
