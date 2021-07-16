package com.domin.exercise.provider;

/**
 * Exception thrown for issues experienced in {@link Provider}s.
 */
public class ProviderException extends Exception {

    /**
     * Create a {@link ProviderException} with a message.
     *
     * @param message a message describing why the error occurred.
     */
    public ProviderException(String message) {
        super(message);
    }

    /**
     * Create a {@link ProviderException} with a message.
     *
     * @param message a message describing why the error occurred.
     * @param cause   the underlying cause of the exception.
     */
    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
