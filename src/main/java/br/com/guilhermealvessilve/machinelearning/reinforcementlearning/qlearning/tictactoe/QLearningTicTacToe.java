package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils.ArrayUtils.join;
import static br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils.ArrayUtils.slice;

public class QLearningTicTacToe {

    private static final String BLANK = " ";
    private final int winReward;
    private final int lossReward;
    private final int tieReward;
    private final Player player1;
    private final Player player2;
    private final Random random;

    public QLearningTicTacToe(final int winReward,
                              final int lossReward,
                              final int tieReward,
                              final Player player1,
                              final Player player2) {
        this.winReward = winReward;
        this.lossReward = lossReward;
        this.tieReward = tieReward;
        this.player1 = player1;
        this.player2 = player2;
        this.random = new Random();
    }

    public void play() {
        var firstPlayerTurn = random.nextBoolean();
        var players = getPlayers(firstPlayerTurn);
        var player = players.get(0);
        var otherPlayer = players.get(1);

        System.out.printf("Player 1: %s - %s%n", player.ticker(), playerType(player));
        System.out.printf("Player 2: %s - %s%n", otherPlayer.ticker(), playerType(otherPlayer));

        var board = createBoard();
        while (true) {
            System.out.printf("Player %s turn!%n", player.ticker());
            showBoard(board);
            var gameResult = gameOver(board, players);
            var terminated = gameResult.terminated();
            if (terminated) {
                var winner = gameResult.winner();
                if (player == winner) {
                    System.out.printf("%s with ticker %s player won!%n", playerType(player), player.ticker());
                    player.reward(winReward, board);
                    otherPlayer.reward(lossReward, board);
                    player.incrementWins();
                } else if (otherPlayer == winner) {
                    System.out.printf("%s with ticker %s player won!%n", playerType(otherPlayer), otherPlayer.ticker());
                    otherPlayer.reward(winReward, board);
                    player.reward(lossReward, board);
                    otherPlayer.incrementWins();
                } else {
                    System.out.println("Tie!");
                    player.reward(tieReward, board);
                    otherPlayer.reward(tieReward, board);
                }
                break;
            }

            int move = player.makeMove(board);
            board.set(move, player.ticker().name());
            firstPlayerTurn = !firstPlayerTurn;
            players = getPlayers(firstPlayerTurn);
            player = players.get(0);
            otherPlayer = players.get(1);
        }
    }

    private GameResult gameOver(List<String> board, List<Player> players) {

        // 0 1 2
        // 3 4 5
        // 6 7 8
        for (var player : players) {

            // Horizontal check
            if (checkState(board, player.ticker(), List.of(0, 1, 2)) ||
                    checkState(board, player.ticker(), List.of(3, 4, 5)) ||
                    checkState(board, player.ticker(), List.of(6, 7, 8))) {
                return new GameResult(true, player);
            }

            // Vertical check
            if (checkState(board, player.ticker(), List.of(0, 3, 6)) ||
                    checkState(board, player.ticker(), List.of(1, 4, 5)) ||
                    checkState(board, player.ticker(), List.of(2, 5, 8))) {
                return new GameResult(true, player);
            }

            // Diagonal check
            if (checkState(board, player.ticker(), List.of(0, 4, 8)) ||
                    checkState(board, player.ticker(), List.of(2, 4, 6))) {
                return new GameResult(true, player);
            }
        }

        if (isAllFilled(board)) {
            return new GameResult(true, null);
        }

        return new GameResult(false, null);
    }

    private static boolean isAllFilled(List<String> board) {
        return board.stream()
                .noneMatch(String::isBlank);
    }

    private boolean checkState(List<String> board, Ticker ticker, List<Integer> positions) {
        return positions.stream()
                .allMatch(i -> board.get(i).equals(ticker.name()));
    }

    private String playerType(Player player) {
        return player.getClass().getSimpleName();
    }

    private List<Player> getPlayers(boolean firstPlayerTurn) {
        if (firstPlayerTurn) return List.of(player1, player2);
        return List.of(player2, player1);
    }

    protected final List<String> createBoard() {
        return Arrays.asList(BLANK.repeat(3*3).split(""));
    }

    private void showBoard(List<String> board) {
        System.out.println(join(slice(board, 0, 3), "|"));
        System.out.println(join(slice(board, 3, 6), "|"));
        System.out.println(join(slice(board, 6, 9), "|"));
    }

    private record GameResult(boolean terminated, Player winner) {

    }
}
