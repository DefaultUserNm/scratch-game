package com.example.scratch.configuration;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class ProbabilityConfig {

    @JsonAlias("standard_symbols")
    private List<ProbabilitySymbolConfig> standardSymbols;

    @JsonAlias("bonus_symbols")
    private ProbabilitySymbolConfig bonusSymbols;
}
