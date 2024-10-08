package com.example.scratch.configuration;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.List;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Data
public class WinCombination {

    @JsonAlias("reward_multiplier")
    private Integer rewardMultiplier;

    private WhenConditionType when;

    private Integer count;

    private String group;

    @JsonAlias("covered_areas")
    private List<List<String>> coveredAreas;
}
