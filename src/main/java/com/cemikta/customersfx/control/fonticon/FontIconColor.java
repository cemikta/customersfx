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

/**
 * Font icon colors
 *
 * @see FontIconFactory
 *
 * @author Cem Ikta
 */
public enum FontIconColor {

    BLACK("#000000"),
    WHITE("#ffffff"),
    BLUE("#2095F2"),
    GREEN("#F6F6F6"),
    YELLOW("#FEC005"),
    RED("#F34235");

    private final String value;

    FontIconColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
