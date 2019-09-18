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

import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.util.I18n;
import javafx.scene.Cursor;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * Search field
 *
 * <p>Search icon on the left side, Clear icon and action on the right side.
 *
 * <pre>{@code SearchField searchField = new SearchField(300);
 * searchField.textProperty().addListener((observable, oldValue, newValue) -> {
 *      //search action
 * });}</pre>
 *
 * @author Cem Ikta
 */
public class SearchField extends CustomTextField {

    public SearchField(double prefWidth) {
        super();
        setPromptText(I18n.CONTROL.getString("searchField.prompt"));
        setPrefWidth(prefWidth);

        // left icon
        setLeft(FontIconFactory.createIcon(AppFontIcons.SEARCH));

        // right icon with clear action
        Text clearIcon = FontIconFactory.createIcon(AppFontIcons.CLEAR);
        clearIcon.setOnMouseClicked(event -> this.clear());
        clearIcon.visibleProperty().bind(this.textProperty().isNotEmpty());
        clearIcon.setCursor(Cursor.HAND);
        setRight(clearIcon);
    }

}
