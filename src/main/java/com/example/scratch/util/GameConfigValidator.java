package com.example.scratch.util;

import com.example.scratch.configuration.properties.GameConfig;
import lombok.experimental.UtilityClass;

import static com.example.scratch.configuration.properties.SymbolImpactType.MISS;
import static com.example.scratch.configuration.properties.SymbolType.BONUS;
import static com.example.scratch.engine.WinningConfigurationProcessor.SEPARATOR;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 */
@UtilityClass
public class GameConfigValidator {

    public static GameConfig validate(GameConfig config) {
        assertParameter(config.getRows() > 0, "matrix cannot have less than 1 row");
        assertParameter(config.getColumns() > 0, "matrix cannot have less than 1 column");
        config.getSymbols().values()
            .forEach(s -> {
                assertParameter(s.getType() != null, "symbol type cannot be null " + s);
                if (s.getType() == BONUS) {
                    assertParameter(
                        s.getImpact() == MISS || s.getRewardMultiplier() != null || s.getExtra() != null,
                        "reward multiplier and extra cannot be null at the same time for bonus symbol " + s
                    );
                    assertParameter(
                        s.getImpact() != null, "impact cannot be null for bonus symbol " + s);
                }
            });
        config.getProbabilities().getStandardSymbols()
                .forEach(s -> {
                    assertParameter(
                        s.getRow() < config.getRows(),
                        "probabilities row cannot be more than " + config.getRows());
                    assertParameter(
                        s.getColumn() < config.getColumns(),
                        "probabilities column cannot be more than " + config.getColumns());
                    s.getSymbols().values()
                        .forEach(v -> assertParameter(
                            v >= 0, "probability must be positive or 0 at " + s));
                });
        config.getProbabilities().getBonusSymbols()
            .getSymbols()
            .values()
            .forEach(v -> assertParameter(
                v >= 0,
                "probability must be positive or 0 at " + config.getProbabilities().getBonusSymbols()));
        config.getWinCombinations().values()
            .forEach(s -> {
                assertParameter(s.getWhen() != null, "when cannot be null in " + s);
                assertParameter(s.getGroup() != null, "group cannot be null in " + s);
                assertParameter(
                    s.getRewardMultiplier() != null, "reward multiplier cannot be null in " + s);
                if (s.getCoveredAreas() != null) {
                    s.getCoveredAreas().forEach(coveredArea -> {
                        coveredArea.forEach(a -> {
                            int row = parseInt(substringBefore(a, SEPARATOR));
                            int column = parseInt(substringAfter(a, SEPARATOR));
                            assertParameter(
                                row < config.getRows(),
                                "covered area is larger than matrix row at " + coveredArea);
                            assertParameter(
                                column < config.getColumns(),
                                "covered area is larger than matrix column at " + coveredArea);
                        });
                    });
                }
            });

        return config;
    }

    public static void assertParameter(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }
}
