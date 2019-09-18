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

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Titled separator control
 *
 * @author Cem Ikta
 */
public class TitledSeparator extends HBox {

    private static final String DEFAULT_STYLE_CLASS = "titled-separator";
    private Label lblTitle;

    /**
     * Creates titled separator.
     * 
     * @param title control's title
     */
    public TitledSeparator(String title) {
        this.getStyleClass().add(DEFAULT_STYLE_CLASS);
        setAlignment(Pos.CENTER_LEFT);
        
        lblTitle = new Label(title);
        lblTitle.getStyleClass().add("label");

        Separator separator = new Separator();
        separator.getStyleClass().add("line");
        HBox.setHgrow(separator, Priority.ALWAYS);
        
        getChildren().addAll(lblTitle, separator);
    }

    public StringProperty textProperty() {
        return lblTitle.textProperty();
    }
    
    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }
    
}