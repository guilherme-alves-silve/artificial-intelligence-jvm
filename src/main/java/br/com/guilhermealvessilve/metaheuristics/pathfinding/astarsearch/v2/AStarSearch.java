package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v2;

import java.util.*;
import java.util.function.Function;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toMap;

public class AStarSearch {

    public static void main(String[] args) {

        var a = vertex(0,0, "A");
        var b = vertex(10,20, "B");
        var c = vertex(20,40, "C");
        var d = vertex(30,10, "D");
        var e = vertex(40,30, "E");
        var f = vertex(50,10, "F");
        var g = vertex(50,40, "G");
        
        Map<Vertex, List<Edge>> graph1 = Map.of(
            a, List.of(edge(10, b), edge(50, d)),
            b, List.of(edge(10, c), edge(20, d)),
            c, List.of(edge(10, e), edge(30, g)),
            d, List.of(edge(80, f)),
            e, List.of(edge(50, f), edge(10, g)),
            f, List.of(),
            g, List.of(edge(10, f))
        );

        Map<Vertex, List<Edge>> graph2 = Map.of(
                a, List.of(edge(10, b), edge(50, c)),
                b, List.of(edge(10, c), edge(20, a)),
                c, List.of(edge(10, a), edge(30, b))
        );

        System.out.println("getApproximatedShortestPath(A, F): " + getApproximatedShortestPath(graph1, a, f)); // -> [A, B, C, E, G, F]
        System.out.println("getApproximatedShortestPath(A, G): " + getApproximatedShortestPath(graph1, a, g)); // -> [A, B, C, E, G]
        System.out.println("getApproximatedShortestPath(A, G): " + getApproximatedShortestPath(graph2, a, c)); // -> [A, B, C]
    }

    public static List<Path> getApproximatedShortestPath(Map<Vertex, List<Edge>> graph, Vertex src, Vertex dest) {
        
        var queue = new PriorityQueue<>(Comparator.comparingDouble(Path::getTotalDistance));
        var visited = new HashSet<Path>();

        var nodeAndPath = buildNodeAndPath(graph);
        var srcPath = nodeAndPath.get(src);
        queue.add(srcPath);
        
        while(!queue.isEmpty()) {
            var current = queue.poll();
            visited.add(current);
            if (current.getVertex().equals(dest)) break;

            for (var edge : graph.get(current.getVertex())) {
                var neighbor = nodeAndPath.get(edge.target());
                var distanceFromStart = edge.weight() + neighbor.getDistanceFromStart(); // g
                var distanceFromDestination = heuristic(current.getVertex(), dest); // h
                var totalDistance = distanceFromStart + distanceFromDestination; // f(x) = g(x) + h(x)

                if (!visited.contains(neighbor) || totalDistance < neighbor.getTotalDistance()) {
                    queue.remove(neighbor);
                    neighbor.setParent(current)
                            .setDistanceFromStart(distanceFromStart)
                            .setTotalDistance(totalDistance);
                    queue.add(neighbor);
                }
            }
        }
        
        return buildDestinationPath(dest, nodeAndPath);
    }

    private static Map<Vertex, Path> buildNodeAndPath(Map<Vertex, List<Edge>> graph) {
        return graph.keySet()
                .stream()
                .map(Path::new)
                .collect(toMap(Path::getVertex, Function.identity()));
    }

    private static List<Path> buildDestinationPath(final Vertex dest, final Map<Vertex, Path> graphPath) {
        var path = new ArrayList<Path>();
        var destPath = graphPath.get(dest);
        for (var node = destPath; node != null; node = node.getParent()) {
            path.add(node);
        }

        Collections.reverse(path);
        return path;
    }

    private static double heuristic(Vertex node1, Vertex node2) {
        return sqrt(pow(node1.x() - node2.x(), 2) + pow(node1.y() - node2.y(), 2));
    }

    private static Vertex vertex(double x, double y, String target) {
        return new Vertex(x, y, target);
    }

    private static Edge edge(int weight, Vertex target) {
        return new Edge(weight, target);
    }
}
