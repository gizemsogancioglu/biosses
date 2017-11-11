package semanticSimilaritySystems.baseline;
import org.openrdf.query.algebra.Str;
import org.simmetrics.StringMetric;
import org.simmetrics.metrics.StringMetrics;
import semanticSimilaritySystems.core.SimilarityMeasure;
import java.io.IOException;


/**
 * Created by gizem on 27.02.2016.
 */

public class SimMetricFunctions implements SimilarityMeasure{

    public double getSimilarity(String sentence1, String sentence2) throws IOException {

        StringMetric metric = StringMetrics.needlemanWunch();
        float result = metric.compare(sentence1, sentence2); //0.4767
        return result;
    }

    public static float jaccardSimilarity(String sentence1, String sentence2){

        StringMetric metric = StringMetrics.jaccard();
        float result = metric.compare(sentence1, sentence2); //0.4767
        return result;

    }
}
