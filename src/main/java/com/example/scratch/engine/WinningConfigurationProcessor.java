package com.example.scratch.engine;

import com.example.scratch.configuration.GameConfig;
import com.example.scratch.configuration.WhenConditionType;
import com.example.scratch.configuration.WinCombination;
import com.example.scratch.model.GameMatrix;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.scratch.configuration.SymbolType.STANDARD;
import static com.example.scratch.configuration.WhenConditionType.LINEAR_SYMBOLS;
import static com.example.scratch.configuration.WhenConditionType.SAME_SYMBOLS;
import static java.lang.Integer.parseInt;
import static java.util.function.BinaryOperator.maxBy;
import static java.util.function.Function.identity;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

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

    private Map<String, List<WinCombination>> processSameSymbols() {
        Set<String> standardSymbols = getStandardSymbols();
        Map<String, Integer> sameSymbolsMap = new HashMap<>();
        for (int i = 0 ; i < matrix.rows() ; i++) {
            for (int j = 0 ; j < matrix.columns() ; j++) {
                var cell = matrix.get(i, j);
                if (standardSymbols.contains(cell)) {
                    sameSymbolsMap.merge(cell, 1, Integer::sum);
                }
            }
        }
        Map<String, List<WinCombination>> result = new HashMap<>();
        for (Map.Entry<String, Integer> entry : sameSymbolsMap.entrySet()) {
            List<WinCombination> combinations = getSameSymbolCombinations(entry);
            if (!combinations.isEmpty()) {
                result.put(entry.getKey(), combinations);
            }
        }

        return result;
    }

    private Set<String> getStandardSymbols() {
        return config.getSymbols()
            .entrySet()
            .stream()
            .filter(s -> s.getValue().getType() == STANDARD)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    private List<WinCombination> getSameSymbolCombinations(Map.Entry<String, Integer> entry) {
        List<WinCombination> combinations = new ArrayList<>();
        for (Map.Entry<String, WinCombination> combination : getCombinations(SAME_SYMBOLS)) {
            if (entry.getValue() >= combination.getValue().getCount()) {
                combinations.add(combination.getValue());
            }
        }
        return combinations;
    }

    private Map<String, List<WinCombination>> processLinearSymbols() {
        Map<String, List<WinCombination>> result = new HashMap<>();
        for (Map.Entry<String, WinCombination> combination : getCombinations(LINEAR_SYMBOLS)) {
            for (List<String> area : combination.getValue().getCoveredAreas()) {
                String symbol = getAreaSymbol(area);
                if (symbol != null) {
                    List<WinCombination> winCombinations = result.computeIfAbsent(symbol, k -> new ArrayList<>());
                    winCombinations.add(combination.getValue());
                    result.put(symbol, winCombinations);
                }
            }
        }

        return result;
    }

    private String getAreaSymbol(List<String> area) {
        String separator = ":";
        String symbol = null;
        for (String cell : area) {
            int row = parseInt(substringBefore(cell, separator));
            int column = parseInt(substringAfter(cell, separator));
            String cellData = matrix.get(row, column);
            if (symbol == null || symbol.equals(cellData)) {
                symbol = cellData;
            } else {
                return null;
            }
        }

        return symbol;
    }

    private Map<String, List<String>> mergeResults(
        Map<String, List<WinCombination>> m1, Map<String, List<WinCombination>> m2) {

        Set<String> keys1 = m1.keySet();
        Set<String> keys2 = m2.keySet();
        var keys = new HashSet<String>();
        keys.addAll(keys1);
        keys.addAll(keys2);

        return keys.stream()
            .collect(Collectors.toMap(identity(), k -> {
                var values1 = Optional.ofNullable(m1.get(k)).orElseGet(Collections::emptyList);
                var values2 = Optional.ofNullable(m2.get(k)).orElseGet(Collections::emptyList);
                return getBestCombinations(values1, values2);
            }));
    }

    private List<String> getBestCombinations(List<WinCombination> c1, List<WinCombination> c2) {
        Map<String, WinCombination> combinations = Stream.concat(c1.stream(), c2.stream())
            .collect(Collectors.toMap(
                WinCombination::getGroup,
                identity(),
                maxBy(Comparator.comparing(WinCombination::getRewardMultiplier))
            ));
        return combinations.values().stream()
            .map(WinCombination::getName)
            .toList();
    }

    private Set<Map.Entry<String, WinCombination>> getCombinations(WhenConditionType condition) {
        return config.getWinCombinations().entrySet()
            .stream()
            .filter(s -> s.getValue().getWhen() == condition)
            .collect(Collectors.toSet());
    }
}
