package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.Collections.reverse;

public class AStarSearch {

    private final Vertex source;
    private final Vertex destination;
    private final PriorityQueue<Vertex> queue;
    private final Set<Vertex> visited;

    public AStarSearch(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
        this.queue = new PriorityQueue<>(new VertexComparator());
        this.visited = new HashSet<>();
    }

    public void run() {

        queue.add(source);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            visited.add(current);
            if (current.equals(destination)) return;

            for (var edge : current.getAdjacencyList()) {
                var neighbour = edge.target();
                var g = edge.weight() + neighbour.getG();
                var h = heuristic(current, destination);
                var f = g + h;

                if (!visited.contains(neighbour) || f < neighbour.getF()) {
                    queue.remove(neighbour);
                    neighbour.setPredecessor(current)
                          .setG(g)
                          .setF(f);
                    queue.add(neighbour);
                }
            }
        }
    }

    private double heuristic(Vertex node1, Vertex node2) {
        return sqrt(pow(node1.x() - node2.x(), 2) + pow(node1.y() - node2.y(), 2));
    }

    public void printSolutionPath() {
        var path = new ArrayList<Vertex>();

        for (var vertex = destination; vertex != null; vertex = vertex.getPredecessor()) {
            path.add(vertex);
        }

        reverse(path);
        System.out.println(path);
    }
}
