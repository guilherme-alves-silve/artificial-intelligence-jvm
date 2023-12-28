package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.mab;

public class MainMultiArmedBanditEpsilonGreedy {

    public static void main(String[] args) {
        var mab = new MultiArmedBanditEpsilonGreedy(new MABConfig(0.1f, new float[]{0.3f, 0.6f, 0.5f}, 10_000));
        mab.run();
        mab.showStatistics();
    }
}
