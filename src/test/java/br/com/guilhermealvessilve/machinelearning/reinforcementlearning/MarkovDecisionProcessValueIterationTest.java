package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import br.com.guilhermealvessilve.machinelearning.reinforcementlearning.mdp.MDPConfig;
import br.com.guilhermealvessilve.machinelearning.reinforcementlearning.mdp.MarkovDecisionProcessValueIteration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarkovDecisionProcessValueIterationTest {

    @Test
    void shouldCheckCorrectPolicyAfter100Iterations() {
        var algorithm = new MarkovDecisionProcessValueIteration(new MDPConfig(-0.1f, 0.99f, 1e-7f,0.8f,
                                                        0.1f, 100, 3, 4));
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
