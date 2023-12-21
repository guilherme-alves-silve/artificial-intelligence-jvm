package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import java.util.Map;

import static br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils.MatrixUtils.argmax;

public class MarkovDecisionProcessValueIteration {

    private final int epochs;
    private final int rows;
    private final int cols;
    private final float stateReward;
    private final float gamma;
    private final float epsilon;
    private final float actionProb;
    private final float actionMissProb;
    private int lastIter;
    private float delta;
    private final float[][] VNext;
    private float[][] V;
    private final float[][] R;
    private final char[][] pi;

    public MarkovDecisionProcessValueIteration(Map<String, Number> config) {
        this.stateReward = config.get("stateReward").floatValue();
        this.gamma = config.get("gamma").floatValue();
        this.epsilon = config.get("epsilon").floatValue();
        this.actionProb = config.get("actionProb").floatValue();
        this.actionMissProb = config.get("actionMissProb").floatValue();
        this.epochs = config.get("epochs").intValue();
        this.rows = config.get("rows").intValue();
        this.cols = config.get("cols").intValue();
        this.V = new float[rows][cols];
        this.VNext = new float[rows][cols];
        this.R = new float[rows][cols];
        this.pi = new char[rows][cols];
        initializeVariables();
    }

    public char[][] run() {
        int n = 0;
        do {
            ++n;
            delta = 1;
            V = VNext.clone();

            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    update(i, j);
                    float error = Math.abs(VNext[i][j] - V[i][j]);
                    if (error > delta) delta = error;
                }
            }

        } while (n < epochs && delta > epsilon);

        lastIter = n;
        printResults();
        return pi;
    }

    private void update(int i, int j) {
        if ('+' == pi[i][j] || '-' == pi[i][j] || '@' == pi[i][j]) {
            VNext[i][j] = R[i][j];
            return;
        }

        //sum_{s'}(P(s' | s, a) * V(s'))
        //sum for each s'(P(pos, all possible actions))
        float[] actions = getPossibleNextActions(i, j);

        int best = argmax(actions);
        // R(s, a) + gamma * sum(P(s' | s, a)) * V(s')
        VNext[i][j] = R[i][j] + gamma * actions[best];

        switch (best) {
            case 0 -> pi[i][j] = 'U';
            case 1 -> pi[i][j] = 'D';
            case 2 -> pi[i][j] = 'L';
            case 3 -> pi[i][j] = 'R';
            default -> throw new IllegalStateException("Invalid action " + best);
        }
    }

    private float[] getPossibleNextActions(int i, int j) {
        var actions = new float[4];
        actions[0] = goUp(i, j)*actionProb + goLeft(i, j)*actionMissProb + goRight(i, j)*actionMissProb;
        actions[1] = goDown(i, j)*actionProb + goLeft(i, j)*actionMissProb + goRight(i, j)*actionMissProb;
        actions[2] = goLeft(i, j)*actionProb + goUp(i, j)*actionMissProb + goDown(i, j)*actionMissProb;
        actions[3] = goRight(i, j)*actionProb + goUp(i, j)*actionMissProb + goDown(i, j)*actionMissProb;
        return actions;
    }

    private float goUp(int i, int j) {
        if (wayBlocked(i-1, j)) return V[i][j];
        return V[i-1][j];
    }

    private float goDown(int i, int j) {
        if (wayBlocked(i+1, j)) return V[i][j];
        return V[i+1][j];
    }

    private float goLeft(int i, int j) {
        if (wayBlocked(i, j-1)) return V[i][j];
        return V[i][j-1];
    }

    private float goRight(int i, int j) {
        if (wayBlocked(i, j+1)) return V[i][j];
        return V[i][j+1];
    }

    private boolean wayBlocked(int i, int j) {
        if (i < 0 || i >= pi.length) return true;
        if (j < 0 || j >= pi[i].length) return true;
        return '@' == pi[i][j];
    }

    private void printResults() {
        System.out.println("The V(s) values after " + lastIter + " iterations, with delta " + delta + ":");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                System.out.printf("%6.5f\t", V[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nBest policy after " + lastIter + " iterations:");
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                System.out.printf(pi[i][j] + "   ");
            }
            System.out.println();
        }
    }

    private void initializeVariables() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                R[i][j] = stateReward;
                pi[i][j] = 'o';
            }
        }

        R[0][3] = +1;
        R[1][3] = -1;
        R[1][1] = 0;

        pi[0][3] = '+';
        pi[1][3] = '-';
        pi[1][1] = '@';
    }
}
