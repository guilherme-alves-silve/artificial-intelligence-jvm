package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

public class QLearningTrainer {

    private final Player aiPlayer1;
    private final Player aiPlayer2;

    public QLearningTrainer(Player aiPlayer1, Player aiPlayer2) {
        this.aiPlayer1 = aiPlayer1;
        this.aiPlayer2 = aiPlayer2;
    }

    public void train(int epochs, int winReward, int lossReward, int tieReward) {

        var trainTicTacToe = new QLearningTicTacToe(
                winReward, lossReward, tieReward,
                aiPlayer1, aiPlayer2
        );

        for (int epoch = 0; epoch < epochs; ++epoch) {
            trainTicTacToe.play();
        }

        System.out.println("*".repeat(10));
        System.out.println(STR."aiPlayer1 wins: \{aiPlayer1.getWins()}");
        System.out.println(STR."aiPlayer2 wins: \{aiPlayer2.getWins()}");
        System.out.println("Starting Human vs AI game...");
        System.out.println("*".repeat(10));

        var bestAIPlayer = aiPlayer1.getWins() >= aiPlayer2.getWins()? aiPlayer1 : aiPlayer2;
        var humanPlayer = new HumanPlayer(bestAIPlayer.ticker().reverse());
        var playTicTacToe = new QLearningTicTacToe(
                winReward, lossReward, tieReward,
                humanPlayer, bestAIPlayer
        );
        playTicTacToe.play();
    }
}
