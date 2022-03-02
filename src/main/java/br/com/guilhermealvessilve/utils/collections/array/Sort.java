package br.com.guilhermealvessilve.utils.collections.array;

import org.nd4j.shade.guava.primitives.Doubles;
import org.nd4j.shade.guava.primitives.Floats;
import org.nd4j.shade.guava.primitives.Ints;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class Sort {

    private Sort() {
        throw new IllegalArgumentException("No Sort!");
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final int[] array) {
        return argsort(array, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final int[] array, final boolean ascending) {
        final var indexes = new Integer[array.length];
        return argsort(indexes, ascending, (idx1, idx2) -> Ints.compare(array[idx1], array[idx2]));
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final double[] array) {
        return argsort(array, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final double[] array, final boolean ascending) {
        final var indexes = new Integer[array.length];
        return argsort(indexes, ascending, (idx1, idx2) -> Doubles.compare(array[idx1], array[idx2]));
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final float[] array) {
        return argsort(array, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final float[] array, final boolean ascending) {
        final var indexes = new Integer[array.length];
        return argsort(indexes, ascending, (idx1, idx2) -> Floats.compare(array[idx1], array[idx2]));
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final List<Float> list) {
        return argsort(list, true);
    }

    /**
     * Original credit: https://github.com/alberts/array4j/blob/master/src/main/java/net/lunglet/util/ArrayUtils
     */
    public static int[] argsort(final List<Float> list, final boolean ascending) {
        final var indexes = new Integer[list.size()];
        return argsort(indexes, ascending, (idx1, idx2) -> Floats.compare(list.get(idx1), list.get(idx2)));
    }

    private static int[] argsort(final Integer[] indexes, final boolean ascending, IntBinaryOperator function) {
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
