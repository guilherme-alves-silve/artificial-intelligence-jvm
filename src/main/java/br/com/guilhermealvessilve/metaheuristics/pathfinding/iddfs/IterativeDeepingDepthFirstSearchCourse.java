package br.com.guilhermealvessilve.metaheuristics.pathfinding.iddfs;

public class IterativeDeepingDepthFirstSearchCourse implements IterativeDeepingDepthFirstSearch {

    private boolean targetFound;
    private int maxDepth;

    @Override
    public boolean search(final Vertex root,
                          final Vertex query) {
        int depthLevel = 0;

        while (!targetFound) {
            System.out.println("\ndepthLevel: " + depthLevel);
            dfs(root, query, depthLevel);
            if (targetFound) return true;
            if (depthLevel > maxDepth) return false;
            ++depthLevel;
        }

        return false;
    }

    private void dfs(Vertex vertex, Vertex query, int depthLevel) {
        if (vertex.equals(query)) targetFound = true;
        if (vertex.getDepthLevel() > depthLevel) return;
        System.out.print(vertex + " ");
        if (targetFound) return;

        if (!vertex.getAdjacencyList().isEmpty()) maxDepth = vertex.getDepthLevel() + 1;

        for (var neighbour : vertex.getAdjacencyList()) {
            neighbour.setDepthLevel(vertex.getDepthLevel() + 1);
            dfs(neighbour, query, depthLevel);
        }
    }
}
