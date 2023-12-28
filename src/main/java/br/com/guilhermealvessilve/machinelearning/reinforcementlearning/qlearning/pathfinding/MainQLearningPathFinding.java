package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.pathfinding;

public class MainQLearningPathFinding {

    public static void main(String[] args) {
        var algorithm = new QLearningPathFinding(new QConfig(0, 100, 0.1f, 0.9f,
                -1e5f, 6, 1_000_000));
        algorithm.run();
        algorithm.showResults();
        algorithm.showPolicy();
    }
}
