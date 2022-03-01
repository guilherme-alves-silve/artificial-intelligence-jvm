package br.com.guilhermealvessilve.machinelearning.scratch.knn;

import br.com.guilhermealvessilve.utils.DatasetSplit;
import br.com.guilhermealvessilve.utils.Resources;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;

import java.io.IOException;

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

            Plot.show(
                    ScatterPlot.create("Sepal", table, "sepal_length", "sepal_width"));

            Plot.show(
                    ScatterPlot.create("Petal", table, "petal_length", "petal_width"));

            final var splitTrainTest = DatasetSplit.splitXYTrainTest(0.8, table);
            var xTrain = splitTrainTest.xTrain();
            var yTrain = splitTrainTest.yTrain();
            var xTest = splitTrainTest.xTest();
            var yTest = splitTrainTest.yTest();

            final var knn = new KNN(3);
            //knn.fit();
        }
    }
}
