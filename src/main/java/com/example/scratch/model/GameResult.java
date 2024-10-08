package com.example.scratch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
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

    private Float reward;

    @JsonAlias("applied_winning_combinations")
    private Map<String, List<String>> appliedWinningCombinations;

    @JsonAlias("applied_bonus_symbol")
    private String appliedBonusSymbol;
}
