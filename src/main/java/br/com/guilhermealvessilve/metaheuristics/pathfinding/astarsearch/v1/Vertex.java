package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Vertex {

    private final double x;
    private final double y;

    private double f;
    private double g;
    private double h;

    private final String name;
    private Vertex predecessor;
    private final List<Edge> adjacencyList;

    public Vertex(String name, double x, double y) {
        this.name = requireNonNull(name);
        this.x = x;
        this.y = y;
        this.adjacencyList = new ArrayList<>();
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double getF() {
        return f;
    }

    public Vertex setF(double f) {
        this.f = f;
        return this;
    }

    public double getG() {
        return g;
    }

    public Vertex setG(double g) {
        this.g = g;
        return this;
    }

    public String getName() {
        return name;
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public Vertex setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
        return this;
    }

    public List<Edge> getAdjacencyList() {
        return Collections.unmodifiableList(adjacencyList);
    }

    public Vertex addNeighbour(Edge edge) {
        this.adjacencyList.add(requireNonNull(edge));
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex vertex)) return false;
        return Objects.equals(name, vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
