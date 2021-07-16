package com.domin.exercise.extractor;

import com.domin.exercise.provider.Provider;

/**
 * Exception thrown for issues experienced in {@link Provider}s.
 */
public class ExtractorException extends Exception {

    /**
     * Create a {@link ExtractorException} with a message.
     *
     * @param message a message describing why the error occurred.
     */
    public ExtractorException(String message) {
        super(message);
    }

    /**
     * Create a {@link ExtractorException} with a message.
     *
     * @param message a message describing why the error occurred.
     * @param cause   the underlying cause of the exception.
     */
    public ExtractorException(String message, Throwable cause) {
        super(message, cause);
    }
}
