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

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.ShortStringConverter;

import java.util.function.UnaryOperator;

/**
 * Short field control
 *
 * @see TextField
 * @see IntegerStringConverter
 * @see TextFormatter
 * @see UnaryOperator
 *
 * @author Cem Ikta
 */
public class ShortField extends TextField {

    public ShortField() {
        super();
        StringConverter<Short> shortStringConverter = new ShortStringConverter();
        setTextFormatter(new TextFormatter<>(shortStringConverter, null, new NumberUnaryOperator()));
        setOnAction(event -> shortStringConverter.fromString(this.getText()));
    }

    public Short getValue() {
        return Short.valueOf(this.getText());
    }

}
