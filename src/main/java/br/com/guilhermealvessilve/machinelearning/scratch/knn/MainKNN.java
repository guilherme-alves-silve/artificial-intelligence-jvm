package br.com.guilhermealvessilve.machinelearning.scratch.knn;

import br.com.guilhermealvessilve.utils.Resources;
import br.com.guilhermealvessilve.utils.plot.MultiScatterPlot;
import br.com.guilhermealvessilve.utils.training.DatasetNd4j;
import org.nd4j.linalg.api.buffer.DataType;
import org.nd4j.linalg.api.ndarray.INDArray;
import tech.tablesaw.api.Table;

import java.io.IOException;

import static java.util.Map.entry;
import static org.nd4j.linalg.factory.Nd4j.sum;

public class MainKNN {

    public static void main(String[] args) throws IOException {

        boolean showGraph = args.length > 0 && Boolean.parseBoolean(args[0]);
        try (final var dataset = Resources.fromResources("/datasets/iris/iris.data")) {

            final var table = Table.read().csv(dataset);
            table.replaceColumn(table.stringColumn(table.columnIndex("class"))
                    .map(irisClass -> switch (irisClass) {
                        case "Iris-setosa" -> "0";
                        case "Iris-versicolor" -> "1";
                        case "Iris-virginica" -> "2";
                        default -> throw new IllegalArgumentException("Class " + irisClass + " not supported");
                    })
                    .asDoubleColumn());

            System.out.println(table.structure());
            System.out.println(table.first(3));

            if (showGraph) {
                MultiScatterPlot.plot("Iris Dataset", table,
                        "length",
                        "width",
                        entry("sepal_length", "sepal_width"),
                        entry("petal_length", "petal_width"));
            }

            final var splitTrainTest = DatasetNd4j.splitXYTrainTestINDArray(0.7, table);
            var xTrain = splitTrainTest.xTrain();
            var yTrain = splitTrainTest.yTrain();
            var xTest = splitTrainTest.xTest();
            var yTest = splitTrainTest.yTest();

            final var knn = new KNN(3);
            knn.fit(xTrain, yTrain);
            System.out.println("Test accuracy: " + accuracy(yTest, knn.predict(xTest)));
            System.out.println("Training accuracy: " + accuracy(yTrain, knn.predict(xTrain)));
        }
    }

    private static float accuracy(INDArray yTest, INDArray predictions) {
        return sum(yTest.eq(predictions).castTo(DataType.FLOAT)).div(predictions.size(0)).getFloat(0);
    }
}
