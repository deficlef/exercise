package com.domin.exercise.model;

import java.util.List;
import java.util.Set;

/**
 * Data model for top countries details.
 */
public class TopCountries {

    private final Set<String> names;
    private final List<Integer> borderCounts;
    private final Integer size;

    public TopCountries(Set<String> names, List<Integer> propertyCounts, int size) {
        this.names = names;
        this.borderCounts = propertyCounts;
        this.size = size;
    }

    public Set<String> getNames() {
        return names;
    }

    public List<Integer> getBorderCounts() {
        return borderCounts;
    }

    public Integer getSize() {
        return size;
    }
}
