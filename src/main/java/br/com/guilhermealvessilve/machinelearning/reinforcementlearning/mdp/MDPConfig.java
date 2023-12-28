package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.mdp;

public record MDPConfig(float stateReward,
                        float gamma,
                        float epsilon,
                        float actionProb,
                        float actionMissProb,
                        int epochs,
                        int rows,
                        int cols
) {

}
