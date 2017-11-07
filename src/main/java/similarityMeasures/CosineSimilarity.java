/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package similarityMeasures;

import org.apache.log4j.Logger;

import java.util.Vector;

/**
 *
 * @author gizem
 */
public class CosineSimilarity extends Distance {
    final static Logger logger = Logger.getLogger(CosineSimilarity.class);

    public CosineSimilarity(Vector<Double> vec1, Vector<Double> vec2) {
        super(vec1, vec2);
    }

    @Override
    public Double calculateDistanceAmongVectors() {
        return calculateCosineSimilarity(super.getVector1(), super.getVector2());
    }

    public static Double calculateCosineSimilarity(Vector <Double> firstVector, Vector<Double> secondVector) {

        double sum = 0 ; double similarity,fnorm,snorm;
        if(firstVector.size() != secondVector.size()){
            logger.error("Cosine Similarity için parametre olarak verilen vektorlerin boyutlari birbirinden farkli.");
            return -1.0;
        }
        for(int i = 0 ; i < firstVector.size(); i++) {
            sum = sum + firstVector.get(i) * secondVector.get(i);
        }
        fnorm = calculateNorm(firstVector);
        snorm = calculateNorm(secondVector);
        similarity = sum / (fnorm * snorm);
        return similarity;
    }

    private static Double calculateNorm(Vector<Double> vector) {
        Double norm = 0.0;

        for (Double value : vector) {

            norm = norm + Math.pow(value, 2);
        }
        return Math.sqrt(norm);
    }
}
