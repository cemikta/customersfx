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

import javafx.scene.Node;

import java.util.List;

/**
 * Css helpers
 *
 * @author Cem Ikta
 */
public class CssHelpers {

    public static void setFontBold(Node node) {
        node.setStyle("-fx-font-weight: bold;");
    }

    public static void setFontSize(Node node, float size) {
        node.setStyle(String.format("-fx-font-size: %s;", size));
    }

    public static void setFontSizeColor(Node node, float size, String color) {
        node.setStyle(String.format("-fx-font-size: %s; -fx-text-fill: %s;", size, color));
    }

    public static void setFontBoldSize(Node node, float size) {
        node.setStyle(String.format("-fx-font-weight: bold; -fx-font-size: %s;", size));
    }

    public static void setFontBoldSizeColor(Node node, float size, String color) {
        node.setStyle(String.format("-fx-font-weight: bold; -fx-font-size: %s; -fx-text-fill: %s;", size, color));
    }

}
