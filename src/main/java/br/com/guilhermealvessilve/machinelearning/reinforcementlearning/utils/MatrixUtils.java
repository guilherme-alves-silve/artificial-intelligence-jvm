package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils;

public class MatrixUtils {

    private MatrixUtils() {
        throw new IllegalArgumentException("No Matrix!");
    }

    public static int argmax(float[] actions) {
        int maxIdx = 0;
        for (int i = 1; i < actions.length; ++i) {
            if (actions[i] > actions[maxIdx]) maxIdx = i;
        }

        return maxIdx;
    }
}
