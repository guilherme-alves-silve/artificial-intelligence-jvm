package br.com.guilhermealvessilve.utils.training;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import tech.tablesaw.api.Table;

import java.util.Arrays;

public class DatasetNd4j {

    private DatasetNd4j() {
        throw new IllegalArgumentException("No DatasetNd4j!");
    }

    public static SplitTrainTestArrays to2DINDArray(final double proportion, final Table table) {
        final var splitTrainTest = DatasetSplit.splitXYTrainTest(proportion, table);
        var xTrain = toINDArray(splitTrainTest.xTrain());
        var yTrain = toINDArray(splitTrainTest.yTrain());
        var xTest = toINDArray(splitTrainTest.xTest());
        var yTest = toINDArray(splitTrainTest.yTest());
        return new SplitTrainTestArrays(xTrain, yTrain, xTest, yTest);
    }

    private static INDArray toINDArray(final Table table) {
        int rowCount = table.rowCount();
        int columnCount = table.columnCount();
        final var shape = new int[]{ rowCount, columnCount };
        final var array = Nd4j.zeros(rowCount, columnCount, 'c');
        for (int rowIdx = 0; rowIdx < rowCount; ++rowIdx) {
            var row = table.row(rowIdx);
            for (int colIdx = 0; colIdx < columnCount; ++colIdx) {
                array.putScalar(rowIdx, colIdx, row.getDouble(colIdx));
            }
        }

        return array;
    }

    public record SplitTrainTestArrays(INDArray xTrain,
                                       INDArray yTrain,
                                       INDArray xTest,
                                       INDArray yTest) {

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                    .append("xTrain", Arrays.toString(xTrain.shape()))
                    .append("yTrain", Arrays.toString(yTrain.shape()))
                    .append("xTest", Arrays.toString(xTest.shape()))
                    .append("yTest", Arrays.toString(yTest.shape()))
                    .toString();
        }
    }
}
