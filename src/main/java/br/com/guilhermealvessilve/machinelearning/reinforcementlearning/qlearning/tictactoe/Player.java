package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

import java.util.List;

import static br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils.ArrayUtils.join;
import static br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils.ArrayUtils.slice;

public abstract class Player {

    public abstract int makeMove(List<String> board);

    public abstract void reward(int reward, List<String> board);

    public abstract Ticker ticker();

    protected final void showBoard(List<String> board) {
        System.out.println(join(slice(board, 0, 3), "|"));
        System.out.println(join(slice(board, 3, 6), "|"));
        System.out.println(join(slice(board, 6, 9), "|"));
    }
}
