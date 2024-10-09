package com.example.scratch.engine;

import com.example.scratch.configuration.GameConfig;
import com.example.scratch.configuration.Symbol;
import com.example.scratch.configuration.WinCombination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import static com.example.scratch.configuration.SymbolImpactType.EXTRA_BONUS;
import static com.example.scratch.configuration.SymbolImpactType.MISS;
import static com.example.scratch.configuration.SymbolImpactType.MULTIPLY_REWARD;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Slf4j
@RequiredArgsConstructor
public class RewardCalculator {

    private final Map<String, List<String>> winningsConfigurations;
    private final Symbol bonus;
    private final GameConfig config;
    private final int bettingAmount;

    public double calculate() {
        double baseReward = calculateBaseReward();
        return applyBonus(baseReward);
    }

    private double calculateBaseReward() {
        double sum = 0;
        for (Map.Entry<String, List<String>> entry : winningsConfigurations.entrySet()) {
            double symbolMultiplier = config.getSymbols().get(entry.getKey()).getRewardMultiplier();
            double winningMultiplier = entry.getValue().stream()
                .map(s -> config.getWinCombinations().get(s))
                .map(WinCombination::getRewardMultiplier)
                .reduce((double) 1, (a, b) -> a * b);
            double increment = bettingAmount * symbolMultiplier * winningMultiplier;
            sum += increment;
            log.info("Reward for symbol [{}] is {}", entry.getKey(), increment);
        }
        return sum;
    }

    private double applyBonus(double baseReward) {
        double bonusValue;
        if (bonus == null || bonus.getImpact() == MISS) {
            bonusValue = baseReward;
        } else if (bonus.getImpact() == EXTRA_BONUS) {
            bonusValue = baseReward + bonus.getExtra();
        } else if (bonus.getImpact() == MULTIPLY_REWARD) {
            bonusValue = baseReward * bonus.getRewardMultiplier();
        } else {
            throw new RuntimeException("Impact not recognized: " + bonus.getImpact());
        }

        return bonusValue;
    }
}
