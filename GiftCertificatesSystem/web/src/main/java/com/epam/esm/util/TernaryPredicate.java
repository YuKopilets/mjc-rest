package com.epam.esm.util;

@FunctionalInterface
public interface TernaryPredicate<T, R, U> {
    boolean test(T t, R u, U r);
}
