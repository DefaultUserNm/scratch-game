package com.example.scratch.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Slf4j
public class RandomCollection<T> {

    private final NavigableMap<Double, T> map = new TreeMap<>();
    private double total = 0;

    public RandomCollection(Map<T, Integer> elements) {
        for (Map.Entry<T, Integer> entry : elements.entrySet()) {
            add(entry.getValue(), entry.getKey());
        }
    }

    private void add(double weight, T result) {
        if (weight <= 0 || map.containsValue(result)) {
            return;
        }
        total += weight;
        map.put(total, result);
    }

    public T next() {
        double value = ThreadLocalRandom.current().nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
}
