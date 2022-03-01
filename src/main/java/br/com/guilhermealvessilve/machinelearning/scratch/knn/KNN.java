package br.com.guilhermealvessilve.machinelearning.scratch.knn;

import br.com.guilhermealvessilve.utils.array.ArgSort;
import org.nd4j.common.util.ArrayUtil;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.nd4j.linalg.ops.transforms.Transforms.*;

public class KNN {

    private final int k;
    private INDArray xTrain;
    private INDArray yTrain;

    public KNN(int k) {
        if (k <= 0) throw new IllegalArgumentException("Invalid k value: " + k);
        this.k = k;
    }

    public void fit(final INDArray xTrain,
                    final INDArray yTrain) {
        this.xTrain = xTrain;
        this.yTrain = yTrain;
    }

    public INDArray predict(INDArray x) {

        var distances = IntStream.range(0, xTrain.rows())
                .mapToObj(i -> xTrain.getRow(i))
                .map(xRowTrain -> euclideanDistance(x, xRowTrain))
                .toList();

        var kIndices = Arrays.stream(ArgSort.sort(distances))
                .limit(k)
                .toArray();
        //var kNearestLabels =

        System.out.println("distances: " + distances);

        return Nd4j.create(-1);
    }

    private float euclideanDistance(final INDArray x, final INDArray xRowTrain) {
        return sqrt(pow(x.sub(xRowTrain), 2).sum()).getFloat(0);
    }
}
