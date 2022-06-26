package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v1;

import static java.util.Objects.requireNonNull;

public record Edge(Vertex target,
                   double weight) {
    public Edge {
        requireNonNull(target);
    }
}
