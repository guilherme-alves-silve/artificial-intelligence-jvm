package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class ArrayUtils {

    private static final String[] EMPTY = new String[]{};
    private ArrayUtils() {
        throw new IllegalArgumentException("No ArrayUtils!");
    }

    public static Stream<String> slice(List<String> list, int start, int end) {
        return Arrays.stream(list.toArray(EMPTY), start, end);
    }

    public static String join(Stream<String> stream, String separator) {
        return stream.collect(joining(separator));
    }
}
