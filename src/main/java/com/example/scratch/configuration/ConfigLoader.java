package com.example.scratch.configuration;

import com.example.scratch.util.MapperHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Slf4j
@RequiredArgsConstructor
public class ConfigLoader {

    private static final ObjectMapper MAPPER = MapperHolder.getInstance();

    private final String location;

    public GameConfig load() throws IOException {
        var result = MAPPER.readValue(new File(location), GameConfig.class);
        log.debug("Loaded game config: {}", result);

        return result;
    }
}
