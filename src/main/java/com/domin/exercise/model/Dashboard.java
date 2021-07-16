package com.domin.exercise.model;

import com.domin.exercise.model.response.Country;

import java.util.Set;

/**
 * Model representation of data for the Dashboard view.
 */
public class Dashboard {

    private final TopCountries topCountries;
    private final Set<Country> countriesWithChinaBorder;

    public Dashboard(TopCountries topCountries, Set<Country> countriesWithChinaBorder) {
        this.topCountries = topCountries;
        this.countriesWithChinaBorder = countriesWithChinaBorder;
    }

    /**
     * @return the top countries model.
     */
    public TopCountries getTopCountries() {
        return topCountries;
    }

    /**
     * @return list of countries with China border.
     */
    public Set<Country> getCountriesWithChinaBorder() {
        return countriesWithChinaBorder;
    }
}
