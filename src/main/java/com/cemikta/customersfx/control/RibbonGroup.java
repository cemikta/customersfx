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

package com.cemikta.customersfx.control;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;

/**
 * Ribbon group
 *
 * @author Cem Ikta
 */
public class RibbonGroup extends HBox {

    private static final String DEFAULT_STYLE_CLASS = "ribbon-group";

    public RibbonGroup() {
        super();
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    public RibbonGroup(Node... children) {
        super();
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        getChildren().addAll(children);
    }

    public static Separator addSeparator() {
        return new Separator(Orientation.VERTICAL);
    }

}
