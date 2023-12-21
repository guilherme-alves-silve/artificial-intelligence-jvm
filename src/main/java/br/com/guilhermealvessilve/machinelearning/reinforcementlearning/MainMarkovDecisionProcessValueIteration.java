package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import java.util.Map;

public class MainMarkovDecisionProcessValueIteration {

    public static void main(String[] args) {
        var algorithm = new MarkovDecisionProcessValueIteration(Map.of(
            "stateReward", -0.1f,
            "gamma", 0.99f,
            "epsilon", 1e-7f,
            "actionProb", 0.8f,
            "actionMissProb", 0.1f,
            "epochs", 100,
            "rows", 3,
            "cols", 4
        ));
        algorithm.run();
    }
}
