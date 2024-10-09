package com.example.scratch.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.experimental.UtilityClass;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@UtilityClass
public class MapperHolder {

    private static volatile ObjectMapper INSTANCE;

    public static ObjectMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (MapperHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = createMapper();
                }
            }
        }
        return INSTANCE;
    }

    private ObjectMapper createMapper() {
        return JsonMapper
            .builder()
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();
    }
}