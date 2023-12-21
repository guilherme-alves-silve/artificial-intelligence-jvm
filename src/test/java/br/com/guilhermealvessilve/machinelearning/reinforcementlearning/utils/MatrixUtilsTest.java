package br.com.guilhermealvessilve.machinelearning.reinforcementlearning.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixUtilsTest {

    @Test
    void shouldReturnMaxIndex() {
        assertEquals(2, MatrixUtils.argmax(new float[] {1, 2, 3}));
        assertEquals(0, MatrixUtils.argmax(new float[] {3, 2, 1}));
    }
}