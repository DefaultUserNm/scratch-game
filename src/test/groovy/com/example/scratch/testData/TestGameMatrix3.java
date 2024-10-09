package com.example.scratch.testData;

import com.example.scratch.model.GameMatrix;

/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 * Matrix with 2 diagonal win combinations & 5 same symbols
 */
public class TestGameMatrix3 extends GameMatrix {

    public TestGameMatrix3() {
        super(3, 3);

        set(0, 0, "A");
        set(0, 1, "B");
        set(0, 2, "A");

        set(1, 0, "E");
        set(1, 1, "A");
        set(1, 2, "5x");

        set(2, 0, "A");
        set(2, 1, "D");
        set(2, 2, "A");
    }
}
