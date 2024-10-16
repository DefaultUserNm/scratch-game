package com.example.scratch.configuration.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class GameConfig {

    private Integer columns;

    private Integer rows;

    private Map<String, Symbol> symbols = new HashMap<>();

    private ProbabilityConfig probabilities;

    @JsonProperty("win_combinations")
    private Map<String, WinCombination> winCombinations = new HashMap<>();

    public void afterPropertiesSet() {
        symbols.forEach((key, value) -> value.setName(key));
        winCombinations.forEach((key, value) -> value.setName(key));
    }
}
