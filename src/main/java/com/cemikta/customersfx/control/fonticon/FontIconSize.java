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
 * Font icon size
 *
 * @see FontIconFactory
 *
 * @author Cem Ikta
 */
public enum FontIconSize {

    XS("18px"),
    SM("24px"),
    MD("36px"),
    LG("48px"),
    XL("64px"),
    XXL("128px"),
    XXXL("256px"),
    XXXXL("512px");

    private final String size;

    FontIconSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
