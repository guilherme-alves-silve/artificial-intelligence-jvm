package br.com.guilhermealvessilve.metaheuristics.pathfinding.iddfs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class IterativeDeepingDepthFirstSearchWithVisited implements IterativeDeepingDepthFirstSearch {

    @Override
    public boolean search(final Vertex root,
                          final Vertex query) {
        int depthLevel = 0;
        var queue = new ArrayDeque<Vertex>();
        var visited = new HashSet<Vertex>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            System.out.println("\ndepthLevel: " + depthLevel);
            if (depthLevel > visited.size()) break;
            var vertex = queue.pollFirst();
            if (dfs(vertex, query, visited, depthLevel)) return true;
            ++depthLevel;
            queue.addLast(root);
        }

        return false;
    }

    private boolean dfs(Vertex vertex, Vertex query, Set<Vertex> visited, int depthLevel) {
        System.out.print(vertex + " ");
        if (vertex.equals(query)) return true;
        visited.add(vertex);
        for (var neighbour : vertex.getAdjacencyList()) {
            neighbour.setDepthLevel(vertex.getDepthLevel() + 1);
            if (vertex.getDepthLevel() == 0) vertex.setDepthLevel(vertex.getDepthLevel() + 1);
            if (vertex.getDepthLevel() > depthLevel) return false;
            if (dfs(neighbour, query, visited, depthLevel)) return true;
        }

        return false;
    }
}
