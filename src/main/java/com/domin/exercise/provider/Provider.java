package com.domin.exercise.provider;

import java.util.List;

/**
 * Interface for providing a gathered list of elements.
 *
 * @param <T> the class type for objects in the list.
 */
public interface Provider<T> {

    /**
     * Returns all elements held on the provider.
     *
     * @param <T> the element type
     * @return a list of provider elements
     */
    <T> List<T> elements();
}
