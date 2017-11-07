package similarityMeasures;

import java.util.Vector;

/**
 * Created by orhan on 03.02.2016.
 */
public abstract class Distance {

    private double distance;
    private Vector<Double> vector1;
    private Vector<Double> vector2;

    public Distance(Vector<Double> vec1, Vector<Double> vec2){

        this.setVector1(vec1);
        this.setVector2(vec2);
    }

    public abstract Double calculateDistanceAmongVectors();

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vector<Double> getVector1() {
        return vector1;
    }

    public void setVector1(Vector<Double> vector1) {
        this.vector1 = vector1;
    }

    public Vector<Double> getVector2() {
        return vector2;
    }

    public void setVector2(Vector<Double> vector2) {
        this.vector2 = vector2;
    }
}
