package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.mab;

public record MABConfig(float epsilon, float[] probabilitiesOfBandits, int epochs) {

}
