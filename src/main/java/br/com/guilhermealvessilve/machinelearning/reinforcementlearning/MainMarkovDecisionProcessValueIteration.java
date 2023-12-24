package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import java.util.Map;

public class MainMarkovDecisionProcessValueIteration {

    public static void main(String[] args) {
        var algorithm = new MarkovDecisionProcessValueIteration(
                new MDPConfig(-0.1f, 0.99f, 1e-7f, 0.8f,
                        0.1f,100,3, 4));
        algorithm.run();
    }
}
