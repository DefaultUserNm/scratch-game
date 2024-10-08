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
    private Integer rewardMultiplier;

    private SymbolType type;

    private Float extra;

    private SymbolImpactType impact;
}
