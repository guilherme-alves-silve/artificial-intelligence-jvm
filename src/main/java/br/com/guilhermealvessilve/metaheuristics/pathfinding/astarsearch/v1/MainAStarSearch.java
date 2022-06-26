package br.com.guilhermealvessilve.metaheuristics.pathfinding.astarsearch.v1;

public class MainAStarSearch {

    public static void main(String[] args) {
        var a = new Vertex("A",0,0);
        var b = new Vertex("B",10,20);
        var c = new Vertex("C",20,40);
        var d = new Vertex("D",30,10);
        var e = new Vertex("E",40,30);
        var f = new Vertex("F",50,10);
        var g = new Vertex("G",50,40);

        a.addNeighbour(new Edge(b, 10))
          .addNeighbour(new Edge(d, 50));

        b.addNeighbour(new Edge(c, 10))
          .addNeighbour(new Edge(d, 20));

        c.addNeighbour(new Edge(e, 10))
          .addNeighbour(new Edge(g, 30));

        d.addNeighbour(new Edge(f, 80));

        e.addNeighbour(new Edge(f, 50))
          .addNeighbour(new Edge(g, 10));

        g.addNeighbour(new Edge(f, 10));

        AStarSearch search = new AStarSearch(a, f);
        search.run();
        search.printSolutionPath(); // -> [A, B, C, E, G, F]
    }
}
