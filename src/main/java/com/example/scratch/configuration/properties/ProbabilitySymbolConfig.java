package com.example.scratch.configuration.properties;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class ProbabilitySymbolConfig {

    private Integer column;

    private Integer row;

    private Map<String, Integer> symbols = new HashMap<>();
}
