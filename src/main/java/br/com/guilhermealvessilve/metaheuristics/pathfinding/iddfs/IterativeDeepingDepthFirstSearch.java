package br.com.guilhermealvessilve.metaheuristics.pathfinding.iddfs;

public interface IterativeDeepingDepthFirstSearch {

    boolean search(final Vertex root,
                   final Vertex query);
}
