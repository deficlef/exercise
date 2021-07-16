package com.domin.exercise.extractor;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Interface for extracting data subsets from list items.
 *
 * @param <T> the list item class type.
 */
public interface Extractor<T> {

    /**
     * Returns a filtered set of objects from the objects list. This is done by applying a filter condition to the values
     * a provided property across all items in the provided list.
     *
     * @param property  the property name
     * @param condition the condition containing the filter condition
     * @param items     the items list
     * @return a set of objects that match the filter condition.
     * @throws ExtractorException on failing to filter.
     */
    Set<T> filterBy(String property, Predicate<List> condition, List<T> items) throws ExtractorException;

    /**
     * Extract all the values for the provided property across all items in the provided list.
     *
     * @param property the property name
     * @param items    the items list
     * @return a set containing all the values for the provided property
     * @throws ExtractorException on failing to return the property values
     */
    Set<String> extractAll(String property, List<T> items) throws ExtractorException;

    /**
     * Extract all the values sizes for the provided property across all items in the provided list.
     *
     * @param property the property name
     * @param items    the items list
     * @return a set containing the provided property's values counts
     * @throws ExtractorException on failing to count all values on all objects
     */
    List<Integer> extractAllSizes(String property, List<T> items) throws ExtractorException;
}
