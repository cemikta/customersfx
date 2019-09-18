/*
 * Copyright (C) 2017 Cem Ikta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

package com.cemikta.customersfx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Resource bundle helper.
 *
 * <p>Each module has its own resource bundle file for i18n strings. Always reads
 * default locale from {@link Locale#getDefault()}.
 *
 * <pre>{@code I18n.MODULE_NAME.getString("key");
 * I18n.MODULE_NAME.getString("key", params);}</pre>
 *
 * @see ResourceBundle
 * @see Locale
 *
 * @author Cem Ikta
 */
public enum I18n {

    CONTROL("control"),
    COMMON("common"),
    DASHBOARD("dashboard"),
    CUSTOMER("customer"),
    COUNTRY("country"),
    CATEGORY("category"),
    USER("user");

    private final Logger logger = LoggerFactory.getLogger(I18n.class);
    private final ResourceBundle resourceBundle;
    private static final String DEFAULT_LOCATION = "i18n.";

    I18n(String bundleFile) {
        resourceBundle = ResourceBundle.getBundle(DEFAULT_LOCATION + bundleFile);
    }

    /**
     * Gets a string for the given key from resource bundle.
     *
     * @param key the key for the desired string
     * @return the string for the given key
     */
    public String getString(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            logger.error("I18n missing resource error {}", e);
            return "err#";
        }
    }
    
    /**
     * Gets a string for the given key from resource bundle and formats 
     * with parameters.
     *
     * @param key the key for the desired string
     * @param params parameters for message formats
     * @return the string for the given key
     */
    public String getString(String key, Object... params) {
        try {
            return MessageFormat.format(resourceBundle.getString(key), params);            
        } catch (MissingResourceException e) {
            logger.error("I18n missing resource error {}", e);
            return "err#";
        }
    }

}
