package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MarkovDecisionProcessValueIterationTest {

    @Test
    void shouldCheckCorrectPolicyAfter100Iterations() {
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
        var pi = algorithm.run();
        var expectedPi = new char[][]{{'R', 'R', 'R', '+'},
                                      {'U', '@', 'U', '-'},
                                      {'U', 'R', 'U', 'L'}};
        for (int i = 0; i < pi.length; i++) {
            for (int j = 0; j < pi[i].length; j++) {
                assertEquals(expectedPi[i][j], pi[i][j]);
            }
        }
    }
}
