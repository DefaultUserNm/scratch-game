package com.example.scratch.engine;

import com.example.scratch.configuration.properties.GameConfig;
import com.example.scratch.model.GameMatrix;
import com.example.scratch.util.RandomCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Slf4j
@RequiredArgsConstructor
public class MatrixGenerator {

    private final GameConfig config;

    public GameMatrix generateMatrix() {
        GameMatrix matrix = new GameMatrix(config.getRows(), config.getColumns());
        for (int i = 0 ; i < config.getRows() ; i++) {
            for (int j = 0 ; j < config.getColumns() ; j++) {
                var symbol = getSymbolForCell(i, j);
                matrix.set(i, j, symbol);
                log.debug("Symbol for cell row: {}, column: {} = {}", i, j, symbol);
            }
        }
        putBonusSymbol(matrix);

        return matrix;
    }

    private String getSymbolForCell(int row, int col) {
        var standardMap = config.getProbabilities()
            .getStandardSymbols()
            .stream()
            .filter(s -> s.getRow() == row && s.getColumn() == col)
            .findFirst()
            .orElseGet(() -> config.getProbabilities().getStandardSymbols().get(0))
            .getSymbols();

        return new RandomCollection<>(standardMap).next();
    }

    /**
     * Puts 1 bonus symbol in a random cell
     * P.S. the problem description does not clearly say how many bonus symbol should be generated or how applied bonus
     * should be determined in case there is more than 1, so I've implemented it the same way as in 2 examples
     * (1 bonus per matrix)
     * @param matrix - game matrix
     */
    private void putBonusSymbol(GameMatrix matrix) {
        Random random = new Random();
        int row = random.nextInt(config.getRows());
        int column = random.nextInt(config.getColumns());
        matrix.set(row, column, getBonusSymbol());
    }

    private String getBonusSymbol() {
        var bonusMap = config.getProbabilities()
            .getBonusSymbols()
            .getSymbols();

        return new RandomCollection<>(bonusMap).next();
    }

}
