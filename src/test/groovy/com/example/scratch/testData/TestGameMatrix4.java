package com.example.scratch.testData;

import com.example.scratch.model.GameMatrix;

/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 * 4x4 matrix with 4 "C" & 7 "D"
 */
public class TestGameMatrix4 extends GameMatrix {

    public TestGameMatrix4() {
        super(4, 4);

        set(0, 0, "F");
        set(0, 1, "C");
        set(0, 2, "B");
        set(0, 3, "D");

        set(1, 0, "D");
        set(1, 1, "E");
        set(1, 2, "C");
        set(1, 3, "D");

        set(2, 0, "C");
        set(2, 1, "D");
        set(2, 2, "MISS");
        set(2, 3, "D");

        set(3, 0, "C");
        set(3, 1, "D");
        set(3, 2, "D");
        set(3, 3, "E");
    }
}
