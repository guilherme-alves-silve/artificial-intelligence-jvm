package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

public class MainTrain {

    public static void main(String[] args) {
        int epochs = 10_000;
        int winReward = +10;
        int lossReward = -10;
        int tieReward = 0;
        float alpha = 0.3f;
        float gamma = 0.9f;
        float epsilon = 0.4f;

        var aiPlayer1 = new AIPlayer(alpha, gamma, epsilon, Ticker.X);
        var aiPlayer2 = new AIPlayer(alpha, gamma, epsilon, Ticker.O);
        var trainQLearningTicTacToe = new QLearningTicTacToe(
            winReward, lossReward, tieReward,
            aiPlayer1, aiPlayer2
        );

        for (int epoch = 0; epoch < epochs; ++epoch) {
            trainQLearningTicTacToe.play();
        }

        System.out.println(STR."aiPlayer1 wins: \{aiPlayer1.getWins()}");
        System.out.println(STR."aiPlayer2 wins: \{aiPlayer2.getWins()}");

        var bestAIPlayer = aiPlayer1.getWins() >= aiPlayer2.getWins()? aiPlayer1 : aiPlayer2;
        var humanPlayer = new HumanPlayer(bestAIPlayer.ticker().reverse());
        var playQLearningTicTacToe = new QLearningTicTacToe(
                winReward, lossReward, tieReward,
                humanPlayer, bestAIPlayer
        );
        playQLearningTicTacToe.play();
    }
}
