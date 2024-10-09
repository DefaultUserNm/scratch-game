package com.example.scratch.util;

import com.example.scratch.configuration.properties.GameConfig;
import lombok.experimental.UtilityClass;

import java.io.IOException;

import static java.lang.Thread.currentThread;

/*
 * @created 09.10.2024
 * @author Alexander Kabakov
 */
@UtilityClass
public class GameConfigUtil {

    public static GameConfig getConfig(String location) throws IOException {
        return MapperHolder.getInstance().readValue(
            currentThread().getContextClassLoader().getResource(location),
            GameConfig.class
        );
    }
}
