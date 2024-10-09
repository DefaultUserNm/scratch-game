package com.example.scratch.engine;

import com.example.scratch.configuration.properties.GameConfig;
import com.example.scratch.configuration.properties.Symbol;
import com.example.scratch.model.GameMatrix;
import com.example.scratch.model.GameResult;

import static com.example.scratch.util.GameConfigValidator.assertParameter;
import static com.example.scratch.util.GameConfigValidator.validate;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
public class GameRunner {

    private final GameConfig config;
    private final int bettingAmount;

    public GameRunner(GameConfig config, int bettingAmount) {
        this.config = validate(config);
        this.bettingAmount = bettingAmount;
        assertParameter(bettingAmount > 0, "betting-amount must be greater than 0");
    }

    public GameResult run() {
        var matrix = new MatrixGenerator(config).generateMatrix();
        return run(matrix);
    }

    private GameResult run(GameMatrix matrix) {
        var winningsConfigurations = new WinningConfigurationProcessor(matrix, config).process();
        Symbol bonusSymbol = winningsConfigurations.isEmpty()
            ? null
            : new BonusSymbolProcessor(matrix, config).process();
        double reward = new RewardCalculator(winningsConfigurations, bonusSymbol, config, bettingAmount)
            .calculate();

        return new GameResult()
            .setMatrix(matrix)
            .setAppliedWinningCombinations(winningsConfigurations)
            .setAppliedBonusSymbol(bonusSymbol == null ? null : bonusSymbol.getName())
            .setReward(reward);
    }
}
