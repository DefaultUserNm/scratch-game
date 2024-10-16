package com.example.scratch.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class GameResult {

    private GameMatrix matrix;

    private Double reward;

    private Map<String, List<String>> appliedWinningCombinations;

    private String appliedBonusSymbol;
}
