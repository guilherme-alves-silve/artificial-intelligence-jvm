package br.com.guilhermealvessilve.machinelearning.scratch.knn;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class KNN {

    private final int k;
    private INDArray trainX;
    private INDArray trainY;

    public KNN(int k) {
        if (k <= 0) throw new IllegalArgumentException("Invalid k value: " + k);
        this.k = k;
    }

    public void fit(INDArray trainX, INDArray trainY) {
        this.trainX = trainX;
        this.trainY = trainY;
    }

    public INDArray predict(INDArray x) {
        return Nd4j.create(-1);
    }
}
