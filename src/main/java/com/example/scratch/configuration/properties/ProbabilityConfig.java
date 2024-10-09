package com.example.scratch.configuration.properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class ProbabilityConfig {

    @JsonProperty("standard_symbols")
    private List<ProbabilitySymbolConfig> standardSymbols;

    @JsonProperty("bonus_symbols")
    private ProbabilitySymbolConfig bonusSymbols;
}
