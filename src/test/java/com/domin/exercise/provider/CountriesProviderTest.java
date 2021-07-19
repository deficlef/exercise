package com.domin.exercise.provider;

import com.domin.exercise.comparator.BordersSizeComparator;
import com.domin.exercise.model.response.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CountriesProviderTest {

    private List<Country> expectedCountries;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() throws IOException {
        mapper = new ObjectMapper();
        expectedCountries = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("countries.json"),
                new TypeReference<List<Country>>() {
                });
    }

    @Test
    void shouldReturnExpectedElements() throws JsonProcessingException, ProviderException {
        String json = mapper.writeValueAsString(expectedCountries);
        CountriesProvider provider = new CountriesProvider.Builder()
                .jsonArray(json)
                .build();
        List<Country> actual = provider.elements();

        assertThat(actual.size()).isEqualTo(expectedCountries.size());
        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i).getName()).isEqualTo(expectedCountries.get(i).getName());
            assertThat(actual.get(i).getFlag()).isEqualTo(expectedCountries.get(i).getFlag());
            // Can be further extended...
        }
    }

    @Test
    void shouldRejectWhenNoResponseProvided() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CountriesProvider.Builder().build();
        });
    }

    @Test
    void shouldRejectWhenInvalidJsonIsProvided() {
        Assertions.assertThrows(ProviderException.class, () -> {
            new CountriesProvider.Builder()
                    .jsonArray("invalid json")
                    .build();
        });
    }

    @Test
    void shouldRejectWhenSortOrderIsSelectedWithoutProvidingASortBy() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CountriesProvider.Builder()
                    .sortOrder(CountriesProvider.Order.ASCENDING)
                    .build();
        });
    }

    @Test
    void shouldRejectWhenSizeIsNotGreaterThanZero() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CountriesProvider.Builder()
                    .sortBy(new BordersSizeComparator())
                    .sortOrder(CountriesProvider.Order.ASCENDING)
                    .withSize(-1)
                    .build();
        });
    }

    @Test
    void shouldSortByProvidedSortParameters() throws ProviderException, JsonProcessingException {
        String json = mapper.writeValueAsString(expectedCountries);

        CountriesProvider provider = new CountriesProvider.Builder()
                .jsonArray(json)
                .sortBy(new BordersSizeComparator())
                .sortOrder(CountriesProvider.Order.ASCENDING)
                .withSize(2)
                .build();
        List<Country> actual = provider.elements();

        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0).getName()).isEqualTo("Åland Islands");
    }

    @Test
    void shouldReturnSpecifiedSizeOfResults() throws ProviderException, JsonProcessingException {
        String json = mapper.writeValueAsString(expectedCountries);

        CountriesProvider provider = new CountriesProvider.Builder()
                .jsonArray(json)
                .withSize(1)
                .build();
        List<Country> actual = provider.elements();

        assertThat(actual.size()).isEqualTo(1);
    }
}