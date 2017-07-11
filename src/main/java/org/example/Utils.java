package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getSimpleName());

    /**
     * Load from the file and return initial properties.
     *
     * @return initial properties
     */
    public static Properties getInitialProperties(String propertyPath) {
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyPath);

        Properties properties = new Properties();

        try {
            properties.load(input);
        } catch (IOException e) {
            LOGGER.severe("Unable to load " + propertyPath + " initial properties");

            throw new RuntimeException(e);
        }

        return properties;
    }
}