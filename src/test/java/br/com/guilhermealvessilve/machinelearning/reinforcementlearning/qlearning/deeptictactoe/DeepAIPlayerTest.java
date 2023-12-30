package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.deeptictactoe;

import br.com.guilhermealvessilve.machinelearning.reinforcementlearning.qlearning.tictactoe.Ticker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DeepAIPlayerTest {

    @Test
    void testOneHotEncodeInput() {
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

        var aiPlayer = new DeepAIPlayer(0f, 0f, 0f, Ticker.O);
        try (var oneHot = aiPlayer.oneHotEncodeInput(winBoard, 4)) {
            assertArrayEquals(new int[] {
                    0, 1, 0, 0, 1, 0, 1, 0, 0,
                    0, 1, 0, 0, 0, 1, 0, 0, 1,
                    1, 0, 0, 0, 1, 0, 1, 0, 0,
                    0, 0, 0, 0, 1, 0, 0, 0, 0
            }, oneHot.toIntVector());
        }

        try (var oneHot = aiPlayer.oneHotEncodeInput(winBoard, 5)) {
            assertArrayEquals(new int[] {
                    0, 1, 0, 0, 1, 0, 1, 0, 0,
                    0, 1, 0, 0, 0, 1, 0, 0, 1,
                    1, 0, 0, 0, 1, 0, 1, 0, 0,
                    0, 0, 0, 0, 0, 1, 0, 0, 0
            }, oneHot.toIntVector());
        }

        try (var oneHot = aiPlayer.oneHotEncodeInput(loseBoard, 1)) {
            assertArrayEquals(new int[] {
                    0, 1, 0, 0, 0, 1, 0, 0, 1,
                    0, 0, 1, 0, 1, 0, 0, 0, 1,
                    1, 0, 0, 1, 0, 0, 0, 0, 1,
                    0, 1, 0, 0, 0, 0, 0, 0, 0
            }, oneHot.toIntVector());
        }

        try (var oneHot = aiPlayer.oneHotEncodeInput(tieBoard, 1)) {
            assertArrayEquals(new int[] {
                    1, 0, 0, 0, 0, 1, 0, 1, 0,
                    0, 1, 0, 1, 0, 0, 1, 0, 0,
                    1, 0, 0, 0, 1, 0, 0, 1, 0,
                    0, 1, 0, 0, 0, 0, 0, 0, 0
            }, oneHot.toIntVector());
        }
    }
}
