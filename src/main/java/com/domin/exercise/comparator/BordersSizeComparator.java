package com.domin.exercise.comparator;

import com.domin.exercise.model.response.Country;

import java.util.Comparator;

/**
 * {@link Comparator} for comparing countries' borders sizes.
 */
public class BordersSizeComparator implements Comparator<Country> {

    @Override
    public int compare(Country o1, Country o2) {
        Integer borderCount1 = o1.getBorders().size();
        Integer borderCount2 = o2.getBorders().size();
        return borderCount1.compareTo(borderCount2);
    }
}
