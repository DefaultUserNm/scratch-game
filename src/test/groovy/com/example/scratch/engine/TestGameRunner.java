package com.example.scratch.engine;

import com.example.scratch.configuration.GameConfig;
import com.example.scratch.model.GameMatrix;
import com.example.scratch.model.GameResult;

/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 */
public class TestGameRunner extends GameRunner {

    private final GameMatrix matrix;

    public TestGameRunner(GameConfig config, int bettingAmount, GameMatrix matrix) {
        super(config, bettingAmount);
        this.matrix = matrix;
    }

    public GameResult run() {
        return run(matrix);
    }
}
