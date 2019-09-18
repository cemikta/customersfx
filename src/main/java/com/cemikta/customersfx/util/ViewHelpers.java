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

import com.cemikta.customersfx.control.IntegerField;
import com.cemikta.customersfx.control.PasswordFieldExt;
import com.cemikta.customersfx.control.ShortField;
import com.cemikta.customersfx.control.TextFieldExt;
import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.control.fonticon.FontIconSize;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import org.apache.commons.lang3.StringUtils;

/**
 * View helpers
 *
 * @author Cem Ikta
 */
public class ViewHelpers {

    public static Label createFontIconLabel(FontIcon fontIcon, String tooltip) {
        return createFontIconLabel(null, fontIcon, tooltip);
    }

    public static Label createFontIconLabel(String text, FontIcon fontIcon, String tooltip) {
        Label label = new Label();
        label.setText(text);
        label.setGraphic(FontIconFactory.createIcon(fontIcon));
        if (StringUtils.isNotEmpty(tooltip)) {
            label.setTooltip(new Tooltip(tooltip));
        }
        return label;
    }

    public static Button createFontIconButton(String title, FontIcon fontIcon, ContentDisplay contentDisplay) {
        return createFontIconButton(title, fontIcon, FontIconSize.SM, contentDisplay);
    }

    public static Button createFontIconButton(String title, FontIcon fontIcon, FontIconSize fontIconSize,
                                              ContentDisplay contentDisplay) {
        Button btn = new Button(title, FontIconFactory.createIcon(fontIcon, fontIconSize));
        btn.setContentDisplay(contentDisplay);
        return btn;
    }

    public static TextField createTextField(double prefWidth) {
        TextField textField = new TextField();
        textField.setPrefWidth(prefWidth);
        textField.setMinWidth(prefWidth);
        textField.setMaxWidth(prefWidth);
        return textField;
    }

    public static TextFieldExt createTextFieldExt(int length, double prefWidth) {
        TextFieldExt textFieldExt = new TextFieldExt(length);
        textFieldExt.setPrefWidth(prefWidth);
        textFieldExt.setMinWidth(prefWidth);
        textFieldExt.setMaxWidth(prefWidth);
        return textFieldExt;
    }

    public static PasswordField createPasswordField(double prefWidth) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(prefWidth);
        passwordField.setMinWidth(prefWidth);
        passwordField.setMaxWidth(prefWidth);
        return passwordField;
    }

    public static PasswordFieldExt createPasswordFieldExt(int length, double prefWidth) {
        PasswordFieldExt passwordFieldExt = new PasswordFieldExt(length);
        passwordFieldExt.setPrefWidth(prefWidth);
        passwordFieldExt.setMinWidth(prefWidth);
        passwordFieldExt.setMaxWidth(prefWidth);
        return passwordFieldExt;
    }

    public static ShortField createShortField(double prefWidth) {
        ShortField shortField = new ShortField();
        shortField.setPrefWidth(prefWidth);
        shortField.setMinWidth(prefWidth);
        shortField.setMaxWidth(prefWidth);
        return shortField;
    }

    public static IntegerField createIntegerField(double prefWidth) {
        IntegerField integerField = new IntegerField();
        integerField.setPrefWidth(prefWidth);
        integerField.setMinWidth(prefWidth);
        integerField.setMaxWidth(prefWidth);
        return integerField;
    }

    public static TextArea createTextArea(double prefWidth, int prefRowCount) {
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(prefWidth);
        textArea.setMinWidth(prefWidth);
        textArea.setMaxWidth(prefWidth);
        textArea.setPrefRowCount(prefRowCount);
        return textArea;
    }

}
