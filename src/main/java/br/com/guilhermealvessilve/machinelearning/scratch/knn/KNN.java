package br.com.guilhermealvessilve.machinelearning.scratch.knn;

import br.com.guilhermealvessilve.utils.collections.map.Counter;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.shade.guava.primitives.Floats;

import java.util.stream.IntStream;

import static br.com.guilhermealvessilve.utils.collections.array.Sort.argsort;
import static java.util.Arrays.stream;
import static org.nd4j.linalg.ops.transforms.Transforms.pow;
import static org.nd4j.linalg.ops.transforms.Transforms.sqrt;

/**
 * Reference:
 *  https://github.com/python-engineer/MLfromscratch/blob/master/mlfromscratch/knn.py
 */
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

    public INDArray predict(INDArray multiX) {
        final var predictions = IntStream.range(0, multiX.rows())
                .mapToObj(multiX::getRow)
                .map(this::predictSingle)
                .toList();
        final var shape = new int[] {predictions.size(), 1};
        return Nd4j.create(Floats.toArray(predictions), shape);
    }

    private float predictSingle(INDArray x) {

        final var distances = IntStream.range(0, xTrain.rows())
                .mapToObj(i -> xTrain.getRow(i))
                .map(xRowTrain -> euclideanDistance(x, xRowTrain))
                .toList();

        final var kIndices = stream(argsort(distances))
                .limit(k)
                .toArray();

        final var kNearestLabels = getKNearestLabels(kIndices);
        final var counter = new Counter<>(kNearestLabels);

        return counter.mostCommon(1).get(0).getKey();
    }

    private Float[] getKNearestLabels(int[] kIndices) {
        var array = yTrain.data().asFloat();
        var output = new Float[kIndices.length];
        for (int i = 0; i < kIndices.length; ++i) {
            output[i] = array[kIndices[i]];
        }

        return output;
    }

    private float euclideanDistance(final INDArray x, final INDArray xRowTrain) {
        return sqrt(pow(x.sub(xRowTrain), 2).sum()).getFloat(0);
    }
}
