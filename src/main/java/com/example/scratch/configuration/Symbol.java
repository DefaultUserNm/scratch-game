package com.example.scratch.configuration;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class Symbol {

    @JsonAlias("reward_multiplier")
    private Double rewardMultiplier;

    private SymbolType type;

    private Double extra;

    private SymbolImpactType impact;

    private String name;
}
