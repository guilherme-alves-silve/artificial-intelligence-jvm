package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player {

    private final Ticker ticker;

    public HumanPlayer(final Ticker ticker) {
        this.ticker = ticker;
    }

    @Override
    public int makeMove(List<String> board) {
        while (true) {
            var scanner = new Scanner(System.in);
            System.out.println("Your next move (cell index 1-9):");
            if (scanner.hasNext("[1-9]")) {
                int move = scanner.nextInt();
                return move - 1;
            }
            System.out.println("Invalid input, please try again.");
        }
    }

    @Override
    public void reward(int reward, List<String> board) {
        // Do nothing
    }

    @Override
    public Ticker ticker() {
        return ticker;
    }
}
