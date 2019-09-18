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

package com.cemikta.customersfx.app;

/**
 * Application themes
 *
 * @author Cem Ikta
 */
public enum AppTheme {

    LIGHT("Light", "/styles/light-theme.css"),
    DARK("Dark", "/styles/dark-theme.css"),
    BLUE("Blue", "/styles/blue-theme.css"),
    GREEN("Green", "/styles/green-theme.css"),
    ORANGE("Orange", "/styles/orange-theme.css"),
    LILA("Lila", "/styles/lila-theme.css");

    private final String theme;
    private final String themeStyle;

    AppTheme(String theme, String themeStyle) {
        this.theme = theme;
        this.themeStyle = themeStyle;
    }

    public String getTheme() {
        return theme;
    }

    public String getThemeStyle() {
        return themeStyle;
    }

    @Override
    public String toString() {
        return this.theme;
    }

}
