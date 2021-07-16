package com.domin.exercise.extractor.filter;

import java.util.List;
import java.util.function.Predicate;

public class Contains implements Predicate<List> {

    private final String value;

    public Contains(String value) {
        this.value = value;
    }

    @Override
    public boolean test(List list) {
        return list.contains(value);
    }
}