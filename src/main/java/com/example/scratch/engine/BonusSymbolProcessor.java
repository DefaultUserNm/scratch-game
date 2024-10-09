package com.example.scratch.engine;

import com.example.scratch.configuration.properties.GameConfig;
import com.example.scratch.configuration.properties.Symbol;
import com.example.scratch.model.GameMatrix;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

import static com.example.scratch.configuration.properties.SymbolType.BONUS;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@RequiredArgsConstructor
public class BonusSymbolProcessor {

    private final GameMatrix matrix;
    private final GameConfig config;

    public Symbol process() {
        Map<String, Symbol> bonusSymbols = config.getSymbols()
            .entrySet()
            .stream()
            .filter(s -> s.getValue().getType() == BONUS)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for (int i = 0 ; i < matrix.rows() ; i++) {
            for (int j = 0 ; j < matrix.columns() ; j++) {
                var element = matrix.get(i, j);
                Symbol bonus = bonusSymbols.get(element);
                if (bonus != null) {
                    return bonus;
                }
            }
        }

        return null;
    }
}
