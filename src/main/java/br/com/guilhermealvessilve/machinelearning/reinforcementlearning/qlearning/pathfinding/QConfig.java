package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.pathfinding;

public record QConfig(float standardReward,
                      float exitReward,
                      float alpha,
                      float gamma,
                      float minValue,
                      int numStates,
                      int epochs
) {

}
