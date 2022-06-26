package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v1;

import java.util.Comparator;

public class VertexComparator implements Comparator<Vertex> {

    @Override
    public int compare(Vertex o1, Vertex o2) {
        return Double.compare(o1.getF(), o2.getF());
    }
}
