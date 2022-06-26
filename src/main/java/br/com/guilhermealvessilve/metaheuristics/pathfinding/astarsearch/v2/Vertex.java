package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v2;

public record Vertex(double x, double y, String name) {

    @Override
    public String toString() {
        return name;
    }
}
