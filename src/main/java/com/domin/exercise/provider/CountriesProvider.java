package com.domin.exercise.provider;

import com.domin.exercise.model.response.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.thymeleaf.util.Validate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for providing a list of {@link Country}s from a JSON string.
 * The JSON string has to be made up of an array of country objects.
 */
public class CountriesProvider implements Provider<Country> {

    private final List<Country> countries;

    private CountriesProvider(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public List<Country> elements() {
        return countries;
    }

    public enum Order {
        ASCENDING,
        DESCENDING
    }

    public static class Builder {

        /**
         * A JSON array of country objects.
         */
        private String jsonArray;
        /**
         * A sort {@link Comparator} to apply to the result.
         */
        private Comparator<? super Country> sortBy;
        /**
         * The order to sort the results in.
         */
        private Order sortOrder;
        /**
         * The size of results to return.
         */
        private Integer size;

        public Builder jsonArray(String jsonArray) {
            this.jsonArray = jsonArray;
            return this;
        }

        public Builder sortBy(Comparator<? super Country> comparator) {
            this.sortBy = comparator;
            return this;
        }

        public Builder sortOrder(Order order) {
            Validate.notNull(sortBy, "Please set a sort by.");
            this.sortOrder = order;
            return this;
        }

        public Builder withSize(int size) {
            Validate.isTrue(size > 0, "Size must be greater than 0.");
            this.size = size;
            return this;
        }

        public CountriesProvider build() throws ProviderException {
            Validate.notNull(jsonArray, "JSON is missing.");

            ObjectMapper mapper = new ObjectMapper();
            List<Country> countries;
            try {
                countries = mapper.readValue(jsonArray, new TypeReference<List<Country>>() {
                });
            } catch (JsonProcessingException e) {
                // throw a RunTime exception for now.
                throw new ProviderException("Error processing countries from response.", e);
            }

            if (sortBy != null) {
                countries.sort(sortBy);

                switch (sortOrder) {
                    case DESCENDING:
                        Collections.reverse(countries);
                    case ASCENDING:
                        // do nothing as countries.sort(sortBy) is ascending.
                    default:
                        // do nothing
                }
            }

            if (size != null) {
                countries = countries.stream()
                        .limit(size)
                        .collect(Collectors.toList());
            }

            return new CountriesProvider(Collections.unmodifiableList(countries));
        }
    }
}
