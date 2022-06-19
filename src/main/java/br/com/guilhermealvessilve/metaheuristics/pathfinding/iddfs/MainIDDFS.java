package br.com.guilhermealvessilve.metaheuristics.pathfinding.iddfs;

public class MainIDDFS {

    public static void main(String[] args) {
        var vertexA = new Vertex("A");
        var vertexB = new Vertex("B");
        var vertexC = new Vertex("C");
        var vertexD = new Vertex("D");
        var vertexE = new Vertex("E");
        var vertexF = new Vertex("F");

        vertexA.addNeighbour(vertexB)
               .addNeighbour(vertexC);
        vertexB.addNeighbour(vertexD)
                .addNeighbour(vertexE);

        final var root = vertexA;
        final var search = vertexF;
        final var iddfs = new IterativeDeepingDepthFirstSearchWithVisited();
        if (iddfs.search(root, search)) {
            System.out.println("\nFound the vertex: " + search);
        } else {
            System.out.println("\nThe vertex " + search + " is not connected to " + root);
        }
    }
}
