package br.com.guilhermealvessilve.metaheuristics.pathfinding;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Course challenge #1 - maze problem
 * In the previous lecture we have considered the maze problem itself. Your task is to implement an algorithm that can find the way out of a maze.
 *
 * [
 *  [1,1,1,1,1],
 *  [1,2,0,1,1],
 *  [1,1,0,1,1],
 *  [1,1,0,0,0],
 *  [1,1,1,1,3],
 * ]
 * So we have a map like this
 *
 * integer 1 represents walls
 *
 * integer 2 is the starting point
 *
 * integer 3 is the destination (so this is what we are looking for)
 *
 * integer 0 represents the states we can consider (so the solution contains one or more 0 states)
 *
 * So the solution should be something like this (S represents the states in the solution set):
 *
 * [
 *  [-,-,-,-,-],
 *  [-,2,S,-,-],
 *  [-,-,S,-,-],
 *  [-,-,S,S,S],
 *  [-,-,-,-,3],
 * ]
 * Good luck!
 */
public class MazeProblemDepthFirstSearch {

    private static final int PATH = 0;
    private static final int WALL = 1;
    private static final int STARTING_POINT = 2;
    private static final int DESTINATION = 3;

    public static void main(String[] args) {
        int[][] maze = {
            {1, 1, 1, 1, 1},
            {1, 2, 0, 1, 1},
            {1, 1, 0, 1, 1},
            {1, 1, 0, 0, 0},
            {1, 1, 1, 1, 3},
        };

        var startingPoint = getStartingPoint(maze);
        var destinationPoint = reachDestination(maze, startingPoint);
        System.out.println("The destination is " + destinationPoint);
    }

    private static Point reachDestination(int[][] maze, Point startingPoint) {
        return search(maze, startingPoint.x, startingPoint.y, new HashSet<>());
    }

    private static Point search(int[][] maze, int x, int y, Set<String> visited) {
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

    private static Point getStartingPoint(int[][] maze) {
        for (int x = 0; x < maze.length; ++x) {
            for (int y = 0; y < maze.length; ++y) {
                if (maze[x][y] == STARTING_POINT) return new Point(x, y);
            }
        }

        throw new IllegalStateException("Starting point was not found!");
    }

    private record Point (int x, int y) {

    }
}
