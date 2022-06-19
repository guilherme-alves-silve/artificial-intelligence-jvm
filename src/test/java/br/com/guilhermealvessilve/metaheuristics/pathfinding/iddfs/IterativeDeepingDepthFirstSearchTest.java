package br.com.guilhermealvessilve.metaheuristics.pathfinding.iddfs;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IterativeDeepingDepthFirstSearchTest {

    @ParameterizedTest
    @MethodSource("provideIDDFSImplementations")
    void shouldFindNodeE(final IterativeDeepingDepthFirstSearch iddfs) {
        var vertexA = new Vertex("A");
        var vertexB = new Vertex("B");
        var vertexC = new Vertex("C");
        var vertexD = new Vertex("D");
        var vertexE = new Vertex("E");

        vertexA.addNeighbour(vertexB)
                .addNeighbour(vertexC);
        vertexB.addNeighbour(vertexD)
                .addNeighbour(vertexE);

        assertTrue(iddfs.search(vertexA, vertexE));
    }

    @ParameterizedTest
    @MethodSource("provideIDDFSImplementations")
    void shouldNotFindNodeF(final IterativeDeepingDepthFirstSearch iddfs) {
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

        assertFalse(iddfs.search(vertexA, vertexF));
    }

    private static Stream<Arguments> provideIDDFSImplementations() {
        return Stream.of(
            Arguments.of(new IterativeDeepingDepthFirstSearchWithVisited()),
            Arguments.of(new IterativeDeepingDepthFirstSearchCourse())
        );
    }
}
