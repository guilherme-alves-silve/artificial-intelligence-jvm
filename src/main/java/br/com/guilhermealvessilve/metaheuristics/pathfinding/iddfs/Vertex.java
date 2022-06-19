package br.com.guilhermealvessilve.metaheuristics.pathfinding.iddfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Vertex {

    private int depthLevel;
    private final String name;
    private final List<Vertex> adjacencyList;

    public Vertex(final String name) {
        this.name = requireNonNull(name);
        this.adjacencyList = new ArrayList<>();
    }

    public Vertex addNeighbour(Vertex neighbor) {
        adjacencyList.add(requireNonNull(neighbor));
        return this;
    }

    public Vertex setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
        return this;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public String getName() {
        return name;
    }

    public List<Vertex> getAdjacencyList() {
        return Collections.unmodifiableList(adjacencyList);
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

    @Override
    public String toString() {
        return name;
    }
}
