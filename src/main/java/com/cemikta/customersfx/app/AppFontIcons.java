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

package com.cemikta.customersfx.app;

import com.cemikta.customersfx.control.fonticon.FontIcon;

/**
 * Application font icon enum
 *
 * @author Cem Ikta
 */
public enum AppFontIcons implements FontIcon {

    // application,
    APP('\ue03f'),
    APP_USER('\ue7ff'),
    APP_DATE('\ue916'),
    APP_PASSWORD('\ue899'),

    // menu
    DASHBOARD('\ue88a'),
    CUSTOMER('\ue8a3'),
    CATEGORY('\ue2c8'),
    COUNTRY('\ue55b'),
    USER('\ue7fb'),
    EXIT('\ue8ac'),
    STATUS_BAR('\ue900'),
    THEMES('\ue23a'),
    PREFERENCES('\ue8b8'),
    HELP('\ue8fd'),
    KEYBOARD_SHORTCUTS('\ue312'),
    TIP_OF_THE_DAY('\ue90f'),
    ABOUT('\ue88f'),

    // actions
    ADD_NEW('\ue145'),
    EDIT('\ue254'),
    DELETE('\ue872'),
    PRINT_PREVIEW('\ue880'),
    PRINT('\ue8ad'),
    PDF('\ue884'),
    SAVE('\ue161'),
    CLOSE('\ue879'),
    SEARCH('\ue8b6'),
    CLEAR('\ue5c9'),
    CHOOSE('\ue5d3'),
    LOGIN('\ue409'),

    // pager
    FIRST_PAGE('\ue5dc'),
    PREVIOUS_PAGE('\ue408'),
    NEXT_PAGE('\ue409'),
    LAST_PAGE('\ue5dd'),
    REFRESH('\ue627'),

    // preview
    DATA_PAGE_PREVIEW('\ue069'),
    LOCATION('\ue8b4'),
    EMAIL('\ue0e1'),
    HOMEPAGE('\ue894'),
    PHONE('\ue61d'),
    MOBILE('\ue325'),
    SKYPE('\ue901'),

    // preferences
    PREFERENCES_GENERAL('\ue904'),
    PREFERENCES_REPORTING('\ue2c8'),

    // tip of the day
    DID_YOU_KNOW('\ue90f'),
    PREVIOUS_TIP('\ue408'),
    NEXT_TIP('\ue409'),

    // help dialog
    PREVIOUS('\ue408'),
    NEXT('\ue409');

    private final char character;

    AppFontIcons(char character) {
        this.character = character;
    }

    @Override
    public String charToString() {
        return Character.toString(character);
    }

}
