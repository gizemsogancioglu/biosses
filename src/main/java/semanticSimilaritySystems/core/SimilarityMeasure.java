package semanticSimilaritySystems.core;

import slib.utils.ex.SLIB_Exception;

import java.io.IOException;

/**
 * Created by gizem on 31.01.2016.
 */
public interface SimilarityMeasure {
    double getSimilarity(String sentence1, String sentence2) throws SLIB_Exception, IOException;
}
