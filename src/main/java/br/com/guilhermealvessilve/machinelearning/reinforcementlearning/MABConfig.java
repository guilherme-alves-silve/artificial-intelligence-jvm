package br.com.guilhermealvessilve.machinelearning.reinforcementlearning;

public record MABConfig(float epsilon, float[] probabilitiesOfBandits, int epochs) {

}
