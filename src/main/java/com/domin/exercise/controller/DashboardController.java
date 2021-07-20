package com.domin.exercise.controller;

import com.domin.exercise.comparator.BordersSizeComparator;
import com.domin.exercise.extractor.CountriesDataExtractor;
import com.domin.exercise.extractor.filter.Contains;
import com.domin.exercise.extractor.ExtractorException;
import com.domin.exercise.model.Dashboard;
import com.domin.exercise.model.TopCountries;
import com.domin.exercise.model.response.Country;
import com.domin.exercise.provider.CountriesProvider;
import com.domin.exercise.provider.Provider;
import com.domin.exercise.provider.ProviderException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class DashboardController {

    private static final String API_URL = "https://restcountries.eu/rest/v2/all";
    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private static final String PROPERTY_NAME = "name";
    private static final String PROPERTY_BORDERS = "borders";
    private static final int MAX_SIZE = 10;
    private static final String CHINA_BORDER = "CHN";
    private final Comparator<? super Country> BORDER_COUNT_COMPARATOR = new BordersSizeComparator();

    @GetMapping("/")
    public String load(Model model) {

        try {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(API_URL);
                String response = EntityUtils.toString(client.execute(request).getEntity());

                Provider<Country> allCountriesProvider = new CountriesProvider.Builder()
                        .jsonArray(response)
                        .build();
                Provider<Country> topCountriesProvider = new CountriesProvider.Builder()
                        .jsonArray(response)
                        .sortBy(BORDER_COUNT_COMPARATOR)
                        .sortOrder(CountriesProvider.Order.DESCENDING)
                        .withSize(MAX_SIZE)
                        .build();

                CountriesDataExtractor extractor = new CountriesDataExtractor();
                List<Country> topCountriesList = topCountriesProvider.elements();
                Set<String> names = extractor.extractAll(PROPERTY_NAME, topCountriesList);
                List<Integer> borderCounts = extractor.extractAllSizes(PROPERTY_BORDERS, topCountriesList);
                Set<Country> withChinaBorder = extractor.filterBy(PROPERTY_BORDERS, new Contains(CHINA_BORDER), allCountriesProvider.elements());

                TopCountries topCountries = new TopCountries(names, borderCounts, MAX_SIZE);
                Dashboard dashboard = new Dashboard(topCountries, withChinaBorder);

                model.addAttribute("dashboard", dashboard);
            }
        } catch (ProviderException | ExtractorException | IOException e) {
            // TODO: Implement error handling. For now log the exception.
            logger.error("Error loading dashboard", e);
        }

        return "dashboard";
    }

}
