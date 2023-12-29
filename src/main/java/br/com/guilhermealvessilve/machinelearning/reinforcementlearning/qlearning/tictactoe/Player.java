package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

import java.util.List;

import static br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils.ArrayUtils.join;
import static br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils.ArrayUtils.slice;

public abstract class Player {

    protected int wins;

    public abstract int makeMove(List<String> board);

    public abstract void reward(int reward, List<String> board);

    public abstract Ticker ticker();

    public int getWins() {
        return wins;
    }

    public void incrementWins() {
        ++wins;
    }
}
