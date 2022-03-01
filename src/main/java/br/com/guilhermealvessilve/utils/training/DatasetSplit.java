package br.com.guilhermealvessilve.utils.training;

import tech.tablesaw.api.Table;

public class DatasetSplit {

    private DatasetSplit() {
        throw new IllegalArgumentException("No DatasetSplit!");
    }

    public static SplitTrainTest splitXYTrainTest(final double proportion,
                                                  final String yColumn,
                                                  final Table table) {
        var tables = table.sampleSplit(proportion);
        var training = tables[0];

        var yTrain = Table.create(training.column(yColumn));
        var xTrain = training.removeColumns(training.column(yColumn)).copy();

        var test = tables[1];
        var yTest = Table.create(test.column(yColumn));
        var xTest = test.removeColumns(test.column(yColumn)).copy();

        return new SplitTrainTest(yTrain, xTrain, yTest, xTest);
    }

    public static SplitTrainTest splitXYTrainTest(final double proportion,
                                                  final Table table) {
        return splitXYTrainTest(proportion, "class", table);
    }

    public record SplitTrainTest(Table yTrain,
                                 Table xTrain,
                                 Table yTest,
                                 Table xTest) {

    }
}
