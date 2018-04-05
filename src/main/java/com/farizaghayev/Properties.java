package com.farizaghayev;

import java.text.MessageFormat;
import java.util.*;


public class Properties {


    private static final Map<String, String> properties = new HashMap<>();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            properties.clear();
            Locale defaultLocale = Locale.getDefault();
            ResourceBundle props = ResourceBundle.getBundle("com.farizaghayev.application", defaultLocale);

            for (Object key : props.keySet()) {
                String keyStr = key.toString();
                properties.put(keyStr, props.getString(keyStr));
            }

        } catch (MissingResourceException e) {
            throw new IllegalArgumentException("properties file not found");
        }
    }


    private Properties() {
    }


    public static Map<String, String> getProperties() {
        return properties;
    }


    public static String propertyWithArgs(String property, Object... arguments) {
        String propertyValue = getProperties().get(property);

        if (propertyValue == null) {
            throw new IllegalArgumentException("Property '" + property + "' not found");
        } else {
            MessageFormat formatter = new MessageFormat("");
            formatter.applyPattern(propertyValue);

            String output = formatter.format(arguments);
            return output;
        }
    }
}
