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

import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

/**
 * Number unary operator
 *
 * @author Cem Ikta
 */
public class LengthUnaryOperator implements UnaryOperator<TextFormatter.Change> {

    private int length;

    public LengthUnaryOperator(int length) {
        this.length = length;
    }

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        if (change.isContentChange()) {
            if (change.getControlNewText().length() > length) {
                return null;
            }
        }
        return change;
    }

}
