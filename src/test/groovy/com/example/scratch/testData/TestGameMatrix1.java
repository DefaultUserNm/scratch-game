package com.example.scratch.testData;

import com.example.scratch.model.GameMatrix;

/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 * Matrix from problem description #1
 */
public class TestGameMatrix1 extends GameMatrix {

    public TestGameMatrix1() {
        super(3, 3);

        set(0, 0, "A");
        set(0, 1, "B");
        set(0, 2, "C");

        set(1, 0, "E");
        set(1, 1, "B");
        set(1, 2, "10x");

        set(2, 0, "F");
        set(2, 1, "D");
        set(2, 2, "B");
    }
}
