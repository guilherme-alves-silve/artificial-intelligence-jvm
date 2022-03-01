package br.com.guilhermealvessilve.machinelearning.scratch.knn;

import br.com.guilhermealvessilve.utils.training.DatasetNd4j;
import br.com.guilhermealvessilve.utils.plot.MultiScatterPlot;
import br.com.guilhermealvessilve.utils.Resources;
import tech.tablesaw.api.Table;

import java.io.IOException;

import static java.util.Map.entry;

public class MainKNN {

    public static void main(String[] args) throws IOException {
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
            System.out.println(table.print());

            MultiScatterPlot.plot("Iris Dataset", table,
                    "length",
                    "width",
                    entry("sepal_length", "sepal_width"),
                    entry("petal_length", "petal_width"));

            final var splitTrainTest = DatasetNd4j.to2DINDArray(0.8, table);
            System.out.println(splitTrainTest);
            var xTrain = splitTrainTest.xTrain();
            var yTrain = splitTrainTest.yTrain();
            var xTest = splitTrainTest.xTest();
            var yTest = splitTrainTest.yTest();

            final var knn = new KNN(3);
            //knn.fit();
        }
    }
}
