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

import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.control.fonticon.FontIconSize;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;

/**
 * Ribbon command
 *
 * @author Cem Ikta
 */
public class RibbonCommand extends Button {

    private static final String DEFAULT_STYLE_CLASS = "ribbon-command";

    public RibbonCommand(String text, FontIcon fontIcon) {
        this(text, fontIcon, ContentDisplay.TOP);
    }

    public RibbonCommand(String text, FontIcon fontIcon, ContentDisplay contentDisplay) {
        super(text, FontIconFactory.createIcon(fontIcon, FontIconSize.SM));
        setContentDisplay(contentDisplay);
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

}
