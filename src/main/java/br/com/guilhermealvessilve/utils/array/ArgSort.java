package br.com.guilhermealvessilve.utils.array;

import org.nd4j.shade.guava.primitives.Doubles;
import org.nd4j.shade.guava.primitives.Floats;
import org.nd4j.shade.guava.primitives.Ints;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class ArgSort {

    private ArgSort() {
        throw new IllegalArgumentException("No ArgSort!");
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final int[] array) {
        return sort(array, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final int[] array, final boolean ascending) {
        final var indexes = new Integer[array.length];
        return sort(indexes, ascending, (idx1, idx2) -> Ints.compare(array[idx1], array[idx2]));
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final double[] array) {
        return sort(array, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final double[] array, final boolean ascending) {
        final var indexes = new Integer[array.length];
        return sort(indexes, ascending, (idx1, idx2) -> Doubles.compare(array[idx1], array[idx2]));
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final float[] array) {
        return sort(array, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final float[] array, final boolean ascending) {
        final var indexes = new Integer[array.length];
        return sort(indexes, ascending, (idx1, idx2) -> Floats.compare(array[idx1], array[idx2]));
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final List<Float> list) {
        return sort(list, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] sort(final List<Float> list, final boolean ascending) {
        final var indexes = new Integer[list.size()];
        return sort(indexes, ascending, (idx1, idx2) -> Floats.compare(list.get(idx1), list.get(idx2)));
    }

    private static int[] sort(final Integer[] indexes, final boolean ascending, IntBinaryOperator function) {
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }

        Arrays.sort(indexes, (idx1, idx2) -> (ascending ? 1 : -1) * function.applyAsInt(idx1, idx2));

        int[] result = new int[indexes.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = indexes[i];
        }

        return result;
    }
}
