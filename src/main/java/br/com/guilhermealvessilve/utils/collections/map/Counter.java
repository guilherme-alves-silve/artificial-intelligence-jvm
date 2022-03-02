package br.com.guilhermealvessilve.utils.collections.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Python Counter alternative for Java
 * Reference:
 *  https://stackoverflow.com/questions/32348453/python-counter-alternative-for-java
 * @param <T>
 */
public class Counter<T> {

    private final Map<T, Integer> counts;

    public Counter() {
        this.counts = new HashMap<>();
    }

    public Counter(T[] elements) {
        this();
        Arrays.stream(elements)
                .forEach(this::put);
    }

    public void put(T key) {
        add(key, 1);
    }

    public void add(T key, int value) {
        counts.merge(key, value, Integer::sum);
    }

    public List<Map.Entry<T, Integer>> mostCommon(int n) {
        return counts.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(n)
                .collect(toList());
    }

    @Override
    public String toString() {
        return counts.toString();
    }
}
