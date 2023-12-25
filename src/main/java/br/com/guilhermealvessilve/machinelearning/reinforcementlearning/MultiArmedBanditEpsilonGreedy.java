package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

import java.util.Random;

public class MultiArmedBanditEpsilonGreedy {

    private final float epsilon;
    private final int epochs;
    private final Bandit[] bandits;
    private final Random random;

    public MultiArmedBanditEpsilonGreedy(final MABConfig config) {
        this.epsilon = config.epsilon();
        this.epochs = config.epochs();
        this.bandits = getBandits(config.probabilitiesOfBandits());
        this.random = new Random();
    }

    private Bandit[] getBandits(float[] probabilities) {
        var bandits = new Bandit[probabilities.length];
        for (int i = 0; i < probabilities.length; ++i) {
            bandits[i] = new Bandit(probabilities[i]);
        }

        return bandits;
    }

    public void run() {
        for (int epoch = 0; epoch < epochs; ++epoch) {
            var bandit = bandits[chooseBandit()];
            int reward = bandit.getReward();
            updateQ(bandit, reward);
            System.out.println("Iteration #" + epoch + ": " + bandit + " with Q value: " + bandit.getQ());
        }
    }

    public void showStatistics() {
        for (var bandit : bandits) {
            System.out.println("Result:\n\t" + bandit + " - " + bandit.getK());
        }
    }

    private int chooseBandit() {
        float randomNumber = random.nextFloat();

        // epsilon-greedy strategy
        if (randomNumber < epsilon) {
            return random.nextInt(bandits.length);
        }

        return getBanditIndexMaxQ();
    }

    private int getBanditIndexMaxQ() {
        int maxIdx = 0;
        for (int i = 1; i < bandits.length; ++i) {
            if (bandits[i].getQ() > bandits[maxIdx].getQ()) {
                maxIdx = i;
            }
        }

        return maxIdx;
    }

    private void updateQ(Bandit bandit, int reward) {
        bandit.setK(bandit.getK() + 1);
        bandit.setQ(bandit.getQ() + (1.0f)/(1 + bandit.getK()) * (reward-bandit.getQ()));
    }
}
