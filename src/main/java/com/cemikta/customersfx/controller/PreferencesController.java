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

package com.cemikta.customersfx.controller;

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.app.AppTheme;
import com.cemikta.customersfx.control.MessageBox;
import com.cemikta.customersfx.mvc.Controller;
import com.cemikta.customersfx.model.Preferences;
import com.cemikta.customersfx.service.PreferencesService;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.view.PreferencesDialog;
import com.cemikta.customersfx.view.ThemesDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Preferences controller
 *
 * @author Cem Ikta
 */
public class PreferencesController implements Controller {

    private final Logger logger = LoggerFactory.getLogger(PreferencesController.class);
    private Preferences preferences;
    private PreferencesService preferencesService;

    public PreferencesController() {
        preferencesService = new PreferencesService();
        preferences = preferencesService.find(1L);
    }

    public void showPreferences() {
        new PreferencesDialog(CustomersFxApp.get().getMainStage(), this, preferences).showAndWait();
    }

    public void showThemes() {
        new ThemesDialog(CustomersFxApp.get().getMainStage(), this, preferences).showAndWait();
    }

    public void onSave(Preferences entity) {
        logger.info("on save action");
        try {
            entity.setUpdatedBy(CustomersFxApp.get().getLoggedInUser().getId());
            preferences = preferencesService.update(entity);
            CustomersFxApp.get().setPreferences(preferences);
        } catch (Exception e) {
            logger.error("Preferences save error {}", e);
            MessageBox.create()
                    .owner(CustomersFxApp.get().getMainStage())
                    .contentText(I18n.COMMON.getString("notification.saveException"))
                    .showError(e);
        }
    }

    public void changeAppTheme(AppTheme appTheme) {
        CustomersFxApp.get().changeAppTheme(appTheme);
    }

    @Override
    public String getName() {
        return "PreferencesController";
    }

}
