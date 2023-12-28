package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

import java.util.List;
import java.util.Random;

public class QLearningTicTacToe {

    private final Player player1;
    private final Player player2;

    public QLearningTicTacToe(Player player1,
                              Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public void play() {
        var random = new Random();
        var firstPlayerTurn = random.nextBoolean();
        var players = getPlayers(firstPlayerTurn);
        var player = players.get(0);
        var otherPlayer = players.get(0);

        System.out.printf("Player 1: %s - %s%n", player.ticker(), player.getClass().getSimpleName());
        System.out.printf("Player 2: %s - %s%n", otherPlayer.ticker(), otherPlayer.getClass().getSimpleName());


    }

    private List<Player> getPlayers(boolean firstPlayerTurn) {
        if (firstPlayerTurn) return List.of(player1, player2);
        return List.of(player2, player1);
    }
}
