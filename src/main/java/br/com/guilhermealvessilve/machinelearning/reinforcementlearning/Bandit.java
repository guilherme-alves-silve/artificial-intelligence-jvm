package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import java.util.Random;

/**
 * Bandit from MAB (Multi-Armed Bandit algorithm)
 * that simulates (pseudo-random) a reward.
 */
public class Bandit {
    private float q;
    private int k;
    private final float probability;
    private final Random random;

    public Bandit(float probabilty) {
        this.probability = probabilty;
        this.random = new Random();
    }

    public int getReward() {
        float randomProbability = random.nextFloat();
        // reward +1 (win) or 0 (lose)
        if (randomProbability < this.probability) return +1;
        else return 0;
    }

    public float getQ() {
        return q;
    }

    public Bandit setQ(float q) {
        this.q = q;
        return this;
    }

    public int getK() {
        return k;
    }

    public Bandit setK(int k) {
        this.k = k;
        return this;
    }

    @Override
    public String toString() {
        return "bandit probability = " + probability;
    }
}
