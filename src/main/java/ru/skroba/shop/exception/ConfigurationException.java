package ru.skroba.shop.exception;

import java.io.IOException;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(final String message) {
        super(message);
    }
}
