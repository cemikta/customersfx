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

package com.cemikta.customersfx.control.fonticon;

import javafx.scene.text.Text;

/**
 * Font icon factory
 *
 * @see FontIcon
 * @see FontIconSize
 * @see FontIconColor
 *
 * @author Cem Ikta
 */
public class FontIconFactory {

    private static final String DEFAULT_STYLE_CLASS = "font-icon";

    public static Text createIcon(FontIcon icon) {
        return createIcon(icon, FontIconSize.XS);
    }

    public static Text createIcon(FontIcon icon, FontIconSize iconSize) {
        Text text = new Text(icon.charToString());
        text.getStyleClass().add(DEFAULT_STYLE_CLASS);
        text.setStyle(String.format("-fx-font-size: %s;", iconSize.getSize()));
        return text;
    }

    public static Text createIcon(FontIcon icon, FontIconSize iconSize, FontIconColor color) {
        Text text = new Text(icon.charToString());
        text.getStyleClass().add(DEFAULT_STYLE_CLASS);
        text.setStyle(String.format("-fx-font-size: %s; -fx-fill: %s;", iconSize.getSize(), color.getValue()));
        return text;
    }

}
