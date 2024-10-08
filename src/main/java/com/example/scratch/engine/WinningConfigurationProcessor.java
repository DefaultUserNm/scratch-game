package com.example.scratch.engine;

import com.example.scratch.configuration.GameConfig;
import com.example.scratch.configuration.WinCombination;
import com.example.scratch.model.GameMatrix;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.scratch.configuration.SymbolType.STANDARD;
import static com.example.scratch.configuration.WhenConditionType.SAME_SYMBOLS;
import static java.util.function.Function.identity;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@RequiredArgsConstructor
public class WinningConfigurationProcessor {

    private final GameMatrix matrix;
    private final GameConfig config;

    public Map<String, List<String>> process() {
        return mergeResults(processSameSymbols(), processLinearSymbols());
    }

    private Map<String, List<String>> processSameSymbols() {
        Set<String> standardSymbols = config.getSymbols()
            .entrySet()
            .stream()
            .filter(s -> s.getValue().getType() == STANDARD)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
        Map<String, Integer> sameSymbolsMap = new HashMap<>();
        for (int i = 0 ; i < matrix.rows() ; i++) {
            for (int j = 0 ; j < matrix.columns() ; j++) {
                var cell = matrix.get(i, j);
                if (standardSymbols.contains(cell)) {
                    sameSymbolsMap.merge(cell, 1, Integer::sum);
                }
            }
        }
        // TODO group
        Map<String, List<String>> result = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sameSymbolsMap.entrySet()) {
            String combinationKey = getBestSameSymbolCombinationKey(entry);
            if (combinationKey != null) {
                result.put(entry.getKey(), List.of(combinationKey));
            }
        }

        return result;
    }

    private String getBestSameSymbolCombinationKey(Map.Entry<String, Integer> entry) {
        String combinationKey = null;
        Integer rewardMultiplier = null;
        for (Map.Entry<String, WinCombination> combination : getSameSymbols()) {
            if (entry.getValue() >= combination.getValue().getCount()
                && (rewardMultiplier == null ||  combination.getValue().getRewardMultiplier() > rewardMultiplier)) {
                combinationKey = combination.getKey();
                rewardMultiplier = combination.getValue().getRewardMultiplier();
            }
        }
        return combinationKey;
    }

    private Set<Map.Entry<String, WinCombination>> getSameSymbols() {
        return config.getWinCombinations().entrySet()
            .stream()
            .filter(s -> s.getValue().getWhen() == SAME_SYMBOLS)
            .collect(Collectors.toSet());
    }

    private Map<String, List<String>> processLinearSymbols() {
        // TODO
        return Map.of();
    }

    private Map<String, List<String>> mergeResults(Map<String, List<String>> m1, Map<String, List<String>> m2) {
        Set<String> keys1 = m1.keySet();
        Set<String> keys2 = m2.keySet();
        var keys = new HashSet<String>();
        keys.addAll(keys1);
        keys.addAll(keys2);
        return keys.stream()
            .collect(Collectors.toMap(identity(), k -> {
                var values1 = Optional.ofNullable(m1.get(k)).orElseGet(Collections::emptyList).stream();
                var values2 = Optional.ofNullable(m2.get(k)).orElseGet(Collections::emptyList).stream();
                return Stream.concat(values1, values2).toList();
            }));
    }
}
