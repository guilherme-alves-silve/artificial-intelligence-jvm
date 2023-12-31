package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.deeptictactoe;

import br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe.QLearningTrainer;
import br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe.Ticker;

public class DeepMainTrain {

    public static void main(String[] args) {
        int epochs = 100;
        int winReward = +10;
        int lossReward = -10;
        int tieReward = 0;
        float alpha = 0.3f;
        float gamma = 0.9f;
        float epsilon = 0.4f;

        var aiPlayer1 = new DeepAIPlayer(alpha, gamma, epsilon, Ticker.X);
        var aiPlayer2 = new DeepAIPlayer(alpha, gamma, epsilon, Ticker.O);
        var trainer = new QLearningTrainer(aiPlayer1, aiPlayer2);
        trainer.train(epochs, winReward, lossReward, tieReward);
    }
}
