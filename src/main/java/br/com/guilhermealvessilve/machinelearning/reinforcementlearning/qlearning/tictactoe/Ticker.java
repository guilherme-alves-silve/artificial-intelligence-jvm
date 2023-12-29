package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

public enum Ticker {
    X, O;

    public Ticker reverse() {
        return this == X ? O : X;
    }
}
