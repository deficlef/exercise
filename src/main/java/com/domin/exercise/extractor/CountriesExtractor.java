package com.domin.exercise.extractor;

import com.domin.exercise.model.response.Country;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toSet;

/**
 * {@link Extractor} for accumulating values from a list of {@link Country}s.
 */
public class CountriesExtractor implements Extractor<Country> {

    @Override
    public Set<Country> filterBy(String property, Predicate<List> condition, List<Country> countries) throws ExtractorException {
        // TODO: Ideally could use reflection here but for the time being let's use something basic.
        if ("borders".equals(property)) {
            Set<Country> result = countries.stream()
                    .filter(c -> condition.test(c.getBorders()))
                    .collect(toSet());
            return Collections.unmodifiableSet(result);
        }
        throw new ExtractorException("Unsupported operation.");
    }

    @Override
    public Set<String> extractAll(String property, List<Country> countries) throws ExtractorException {
        if ("name".equals(property)) {
            Set<String> names = new LinkedHashSet<>();
            countries.forEach(o -> names.add(o.getName()));
            return Collections.unmodifiableSet(names);
        }
        throw new ExtractorException("Unsupported operation.");
    }

    @Override
    public List<Integer> extractAllSizes(String property, List<Country> countries) throws ExtractorException {
        if ("borders".equals(property)) {
            List<Integer> borderCnts = new LinkedList<>();
            countries.forEach(o -> {
                List<String> borders = o.getBorders();
                borderCnts.add(borders.size());
            });
            return Collections.unmodifiableList(borderCnts);
        }
        throw new ExtractorException("Unsupported operation.");
    }
}
