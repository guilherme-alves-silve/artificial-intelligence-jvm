package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.nd4j.common.primitives.Pair.pairOf;

class AIPlayerTest {

    @Test
    void shouldGetBestMoves() {

        // With actions 4 or 5
        var winBoard = List.of("O", "O", "X",
                               "O", " ", " ",
                               "X", "O", "X");
        // With actions 1, 2, 3, 5
        var loseBoard = List.of("O", " ", " ",
                                " ", "O", " ",
                                "X", "X", " ");
        // With action 1
        var tieBoard = List.of("X", " ", "O",
                               "O", "X", "X",
                               "X", "O", "O");

        var aiPlayer = new AIPlayer(0, 0, 0, Ticker.X, new LinkedHashMap<>() {{
            put(pairOf(winBoard, 4), +10f);
            put(pairOf(winBoard, 5), +10f);
            put(pairOf(loseBoard, 1), -10f);
            put(pairOf(loseBoard, 2), -10f);
            put(pairOf(loseBoard, 3), -10f);
            put(pairOf(loseBoard, 5), -10f);
            put(pairOf(tieBoard, 1), 0f);
        }});

        var bestMoves = aiPlayer.bestMoves(List.of("O", "O", "X", "O", " ", " ", "X", "O", "X"), List.of(4, 5, 6, 7, 8));

        assertAll(
                () -> assertEquals(2, bestMoves.size(), "size"),
                () -> assertEquals(4, bestMoves.get(0).getFirst(), "action 4"),
                () -> assertEquals(+10, bestMoves.get(0).getSecond().intValue(), "action 4 -> reward"),
                () -> assertEquals(5, bestMoves.get(1).getFirst(), "action 5"),
                () -> assertEquals(+10, bestMoves.get(1).getSecond().intValue(), "action 5 -> reward")
        );
    }
}
