package com.example.scratch.engine;

import com.example.scratch.configuration.GameConfig;
import com.example.scratch.configuration.Symbol;
import com.example.scratch.configuration.WinCombination;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.example.scratch.configuration.SymbolImpactType.EXTRA_BONUS;
import static com.example.scratch.configuration.SymbolImpactType.MISS;
import static com.example.scratch.configuration.SymbolImpactType.MULTIPLY_REWARD;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@RequiredArgsConstructor
public class RewardCalculator {

    private final Map<String, List<String>> winningsConfigurations;
    private final Symbol bonus;
    private final GameConfig config;
    private final int bettingAmount;

    public float calculate() {
        float baseReward = calculateBaseReward();
        return applyBonus(baseReward);
    }

    private float calculateBaseReward() {
        int sum = 0;
        for (Map.Entry<String, List<String>> entry : winningsConfigurations.entrySet()) {
            int symbolMultiplier = config.getSymbols().get(entry.getKey()).getRewardMultiplier();
            int winningMultiplier = entry.getValue().stream()
                .map(s -> config.getWinCombinations().get(s))
                .map(WinCombination::getRewardMultiplier)
                .reduce(1, (a, b) -> a * b);
            sum += bettingAmount * symbolMultiplier * winningMultiplier;
        }
        return sum;
    }

    private float applyBonus(float baseReward) {
        if (bonus == null || bonus.getImpact() == MISS) {
            return baseReward;
        } else if (bonus.getImpact() == EXTRA_BONUS) {
            return baseReward + bonus.getExtra();
        } else if (bonus.getImpact() == MULTIPLY_REWARD) {
            return baseReward * bonus.getRewardMultiplier();
        } else {
            throw new RuntimeException("Impact not recognized: " + bonus.getImpact());
        }
    }
}
