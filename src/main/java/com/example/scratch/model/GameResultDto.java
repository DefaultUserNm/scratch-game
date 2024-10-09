package com.example.scratch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class GameResultDto {

    private String[][] matrix;

    private Integer reward;

    @JsonProperty("applied_winning_combinations")
    private Map<String, List<String>> appliedWinningCombinations;

    @JsonProperty("applied_bonus_symbol")
    private String appliedBonusSymbol;
}
