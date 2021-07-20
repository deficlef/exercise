package com.domin.exercise.extractor;

import com.domin.exercise.model.response.Country;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.domin.exercise.extractor.filter.Contains;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CountriesExtractorTest {

    private CountriesExtractor extractor;
    private List<Country> countries;

    @BeforeEach
    void setUp() throws Exception {
        extractor = new CountriesExtractor();
        ObjectMapper mapper = new ObjectMapper();
        countries = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("countries.json"),
                new TypeReference<List<Country>>() {
                });
    }

    @Test
    void filterByForbidsUnsupportedOperations() {
        Assertions.assertThrows(ExtractorException.class, () -> {
            extractor.filterBy("invalidProp", new Contains("value"), countries);
        });
    }

    @Test
    void filterByReturnsObjectsMatchingTheFilterCondition() throws ExtractorException {
        Predicate doesNotContain = (Predicate<List>) list -> !new Contains("IRN").test(list);
        Set<Country> actual = extractor.filterBy("borders", doesNotContain, countries);
        assertThat(actual.size()).isEqualTo(3);
    }

    @Test
    void extractAllForbidsUnsupportedOperations() {
        Assertions.assertThrows(ExtractorException.class, () -> {
            extractor.extractAll("invalidProp", countries);
        });
    }

    @Test
    void extractAllReturnsValuesForProvidedProperty() throws ExtractorException {
        Set<String> values = extractor.extractAll("name", countries);
        assertThat(values.size()).isEqualTo(4);
    }

    @Test
    void extractAllSizesForbidsUnsupportedOperations() {
        Assertions.assertThrows(ExtractorException.class, () -> {
            extractor.extractAllSizes("invalidProp", countries);
        });
    }

    @Test
    void extractAllSizesReturnsValuesForProvidedProperty() throws ExtractorException {
        List<Integer> actual = extractor.extractAllSizes("borders", countries);
        assertThat(actual.size()).isEqualTo(4);
        assertThat(actual.get(0)).isEqualTo(6);
        assertThat(actual.get(1)).isEqualTo(0);
    }
}