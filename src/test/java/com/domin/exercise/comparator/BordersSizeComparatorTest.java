package com.domin.exercise.comparator;

import com.domin.exercise.model.response.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class BordersSizeComparatorTest {

    private BordersSizeComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new BordersSizeComparator();
    }

    @Test
    public void shouldReturnNegativeIntOnValueGreaterThan() {
        Country country1 = new Country();
        country1.setBorders(Arrays.asList("Hello"));
        Country country2 = new Country();
        country2.setBorders(Arrays.asList("Hello", "World!"));

        assertThat(comparator.compare(country1, country2)).isEqualTo(-1);
    }

    @Test
    public void shouldReturnPositiveIntOnValueGreaterThan() {
        Country country1 = new Country();
        country1.setBorders(Arrays.asList("Hello", "World!"));
        Country country2 = new Country();
        country2.setBorders(Arrays.asList("Hello"));

        assertThat(comparator.compare(country1, country2)).isEqualTo(1);
    }
}