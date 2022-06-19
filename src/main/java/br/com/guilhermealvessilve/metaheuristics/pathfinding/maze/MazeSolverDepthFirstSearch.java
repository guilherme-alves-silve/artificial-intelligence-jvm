package br.com.guilhermealvessilve.metaheuristics.pathfinding.maze;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class MazeSolverDepthFirstSearch {

    private static final int WALL = 1;
    private static final int STARTING_POINT = 2;
    private static final int DESTINATION = 3;

    public Point findDestination(int[][] maze) {
        var startingPoint = getStartingPoint(maze);
        return reachDestination(maze, startingPoint);
    }

    private Point reachDestination(int[][] maze, Point startingPoint) {
        return search(maze, startingPoint.x, startingPoint.y, new HashSet<>());
    }

    private Point search(int[][] maze, int x, int y, Set<String> visited) {
        if (x < 0 || x >= maze.length) return null;
        if (y < 0 || y >= maze[x].length) return null;
        if (maze[x][y] == DESTINATION) return new Point(x, y);
        var key = x + "," + y;
        if (visited.contains(key)) return null;
        visited.add(key);

        if (maze[x][y] == WALL) return null;

        return Stream.of(
            search(maze, x + 1, y, visited),
            search(maze, x - 1, y, visited),
            search(maze, x, y + 1, visited),
            search(maze, x, y - 1, visited)
        )
        .dropWhile(Objects::isNull)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Destination was not found!"));
    }

    private Point getStartingPoint(int[][] maze) {
        for (int x = 0; x < maze.length; ++x) {
            for (int y = 0; y < maze.length; ++y) {
                if (maze[x][y] == STARTING_POINT) return new Point(x, y);
            }
        }

        throw new IllegalStateException("Starting point was not found!");
    }

    public record Point (int x, int y) {

    }
}
