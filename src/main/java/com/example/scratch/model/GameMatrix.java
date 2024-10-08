package com.example.scratch.model;

import lombok.Getter;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Getter
public class GameMatrix {

    private final String[][] matrix;

    public GameMatrix(int rows, int columns) {
        this.matrix = new String[rows][columns];
    }

    public String get(int row, int column) {
        return matrix[row][column];
    }

    public void set(int row, int column, String value) {
        matrix[row][column] = value;
    }

    public int rows() {
        return matrix.length;
    }

    public int columns() {
        return matrix[0].length;
    }
}
