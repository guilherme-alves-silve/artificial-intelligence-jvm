package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QLearningPathFinding {

    private static final int TERMINAL_STATE = 5;
    private final float standardReward;
    private final float exitReward;
    private final float alpha;
    private final float gamma;
    private final float minValue;
    private final int numStates;
    private final int epochs;
    private final float[][] R;
    private final float[][] QTable;
    private final Random random;

    public QLearningPathFinding(QConfig config) {
        this.standardReward = config.standardReward();
        this.exitReward = config.exitReward();
        this.alpha = config.alpha();
        this.gamma = config.gamma();
        this.minValue = config.minValue();
        this.numStates = config.numStates();
        this.epochs = config.epochs();
        this.R = new float[][]{
                {minValue,       minValue,       minValue,       minValue,       standardReward, minValue},
                {minValue,       minValue,       minValue,       standardReward, minValue,       exitReward},
                {minValue,       minValue,       minValue,       standardReward, minValue,       minValue},
                {minValue,       standardReward, standardReward, minValue,       standardReward, minValue},
                {standardReward, minValue,       minValue,       standardReward, minValue,       exitReward},
                {minValue,       standardReward, minValue,       minValue,       standardReward, exitReward}
        };
        this.QTable = new float[numStates][numStates];
        this.random = new Random();
    }

    public void run() {
        for (int epoch = 0; epoch < epochs; ++epoch) {
            int state = random.nextInt(numStates);
            if (TERMINAL_STATE == state) continue;
            simulate(state);
        }
    }

    public void showResults() {
        for (int i = 0; i < QTable.length; ++i) {
            for (int j = 0; j < QTable[i].length; ++j) {
                System.out.printf("%.1f ", QTable[i][j]);
            }

            System.out.println();
        }
    }

    public void showPolicy() {
        for (int i = 0; i < numStates; ++i) {
            int state = i;
            System.out.print("Policy: " + state);

            while (state != TERMINAL_STATE) {
                int maxQState = 0;
                float maxQ = 0f;

                for (int j = 0; j < numStates; ++j) {
                    if (QTable[state][j] > maxQ) {
                        maxQ = QTable[state][j];
                        maxQState = j;
                    }
                }

                System.out.print(" -> " + maxQState);
                state = maxQState;
            }

            System.out.println();
        }
    }

    private void simulate(int state) {
        do {
            var possibleNextStates = availableStates(state);
            int nextState = possibleNextStates.get(random.nextInt(possibleNextStates.size()));
            float maxQ = maxQ(nextState);

            // Formula: Q(s, a) = Q(s, a) + alpha[R(s, a) + gamma max Q(s', a') - Q(s, a)]

            QTable[state][nextState] = QTable[state][nextState] + alpha*(R[state][nextState] + gamma*maxQ - QTable[state][nextState]);

            state = nextState;
        } while (state != TERMINAL_STATE);
    }

    private float maxQ(int nextState) {
        float maxQ = minValue;
        for (int i = 0; i < QTable[nextState].length; ++i) {
            if (QTable[nextState][i] > maxQ) {
                maxQ = QTable[nextState][i];
            }
        }

        return maxQ;
    }

    private List<Integer> availableStates(int state) {
        var states = new ArrayList<Integer>(numStates);
        for (int i = 0; i < R[state].length; ++i) {
            if (R[state][i] > minValue) {
                states.add(i);
            }
        }

        return states;
    }
}
