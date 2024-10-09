package com.example.scratch.engine;

import com.example.scratch.configuration.GameConfig;
import com.example.scratch.configuration.Symbol;
import com.example.scratch.model.GameResult;
import com.example.scratch.model.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Slf4j
@RequiredArgsConstructor
public class GameRunner {

    private final GameConfig config;
    private final int bettingAmount;

    public GameResult run() {
        var matrix = new MatrixGenerator(config).generateMatrix();
        var winningsConfigurations = new WinningConfigurationProcessor(matrix, config).process();
        Pair<String, Symbol> bonusSymbol = winningsConfigurations.isEmpty()
            ? Pair.of(null, null)
            : new BonusSymbolProcessor(matrix, config).process();
        double reward = new RewardCalculator(winningsConfigurations, bonusSymbol.getRight(), config, bettingAmount)
            .calculate();

        return new GameResult()
            .setMatrix(matrix)
            .setAppliedWinningCombinations(winningsConfigurations)
            .setAppliedBonusSymbol(bonusSymbol.getLeft())
            .setReward(reward);
    }
}
