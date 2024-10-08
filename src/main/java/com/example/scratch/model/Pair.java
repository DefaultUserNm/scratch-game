package com.example.scratch.model;

import lombok.Getter;

/*
 * @created 08.10.2024
 * @author Alexander Kabakov
 */
@Getter
public class Pair<L, R> {

    private final L left;
    private final R right;

    private Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Pair<L, R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }
}
