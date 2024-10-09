package com.example.scratch.configuration.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class Symbol {

    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;

    private SymbolType type;

    private Double extra;

    private SymbolImpactType impact;

    @JsonIgnore
    private String name;
}
