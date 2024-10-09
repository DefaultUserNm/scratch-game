package com.example.scratch.configuration.properties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class WinCombination {

    @JsonProperty("reward_multiplier")
    private Double rewardMultiplier;

    private WhenConditionType when;

    private Integer count;

    private String group;

    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;

    @JsonIgnore
    private String name;
}
