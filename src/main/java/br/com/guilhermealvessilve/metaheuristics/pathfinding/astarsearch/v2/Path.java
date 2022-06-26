package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v2;

import java.util.Objects;

public class Path {
    private double distanceFromStart;
    private double totalDistance;
    private final Vertex node;
    private Path parent;

    Path(Vertex node) {
        this.node = node;
    }

    public double getDistanceFromStart() {
        return distanceFromStart;
    }

    public Path setDistanceFromStart(double distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
        return this;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Vertex getVertex() {
        return node;
    }

    public Path getParent() {
        return parent;
    }

    Path setParent(Path parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path path)) return false;
        return Objects.equals(node, path.node);
    }

    @Override
    public String toString() {
        return node.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(node);
    }
}
