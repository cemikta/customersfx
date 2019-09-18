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

package com.cemikta.customersfx.view;

import com.cemikta.customersfx.app.AppTheme;
import com.cemikta.customersfx.controller.PreferencesController;
import com.cemikta.customersfx.model.Preferences;
import com.cemikta.customersfx.util.CssHelpers;
import com.cemikta.customersfx.util.I18n;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

/**
 * Application themes dialog
 *
 * @author Cem Ikta
 */
public class ThemesDialog extends Dialog<Preferences> {

    private final static double WIDTH = 72;
    private final static double HEIGHT = 50;

    private PreferencesController controller;
    private Preferences preferences;
    private ToggleGroup themeGroup;
    private ToggleButton btnLight;
    private ToggleButton btnDark;
    private ToggleButton btnBlue;
    private ToggleButton btnGreen;
    private ToggleButton btnOrange;
    private ToggleButton btnLila;

    public ThemesDialog(Window owner, PreferencesController controller, Preferences preferences) {
        initOwner(owner);
        this.controller = controller;
        this.preferences = preferences;
        setTitle(I18n.COMMON.getString("themesDialog.title"));
        buildView();
        setAccelerators();
        pop();
        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> onClose());
    }

    private void buildView() {
        Label lblSelectYourTheme = new Label(I18n.COMMON.getString("themesDialog.selectYourTheme"));
        CssHelpers.setFontBold(lblSelectYourTheme);

        themeGroup = new ToggleGroup();
        themeGroup.selectedToggleProperty().addListener((observable, oldValue, selectedToggle) -> {
            if (selectedToggle != null) {
                controller.changeAppTheme((AppTheme) selectedToggle.getUserData());
            }
        });
        btnLight = createToggleButton(I18n.COMMON.getString("themesDialog.lightTheme"),
                "theme-button-gray", AppTheme.LIGHT);
        btnDark = createToggleButton(I18n.COMMON.getString("themesDialog.darkTheme"),
                "theme-button-dark", AppTheme.DARK);
        btnBlue = createToggleButton(I18n.COMMON.getString("themesDialog.blueTheme"),
                "theme-button-blue", AppTheme.BLUE);
        btnGreen = createToggleButton(I18n.COMMON.getString("themesDialog.greenTheme"),
                "theme-button-green", AppTheme.GREEN);
        btnOrange = createToggleButton(I18n.COMMON.getString("themesDialog.orangeTheme"),
                "theme-button-orange", AppTheme.ORANGE);
        btnLila = createToggleButton(I18n.COMMON.getString("themesDialog.lilaTheme"),
                "theme-button-lila", AppTheme.LILA);

        GridPane contentPane = new GridPane();
        contentPane.setPadding(new Insets(20, 50, 20, 50));
        contentPane.setHgap(15);
        contentPane.setVgap(15);
        contentPane.setMinWidth(300);
        contentPane.setPrefWidth(300);
        contentPane.setMaxWidth(300);
        contentPane.setAlignment(Pos.CENTER);

        contentPane.add(lblSelectYourTheme, 0, 0, 3, 1);
        contentPane.add(btnLight, 0, 1);
        contentPane.add(btnDark, 1, 1);
        contentPane.add(btnBlue, 2, 1);
        contentPane.add(btnGreen, 0, 2);
        contentPane.add(btnOrange, 1, 2);
        contentPane.add(btnLila, 2, 2);

        getDialogPane().setContent(contentPane);
    }

    private ToggleButton createToggleButton(String tooltip, String styleClass, AppTheme appTheme) {
        ToggleButton button = new ToggleButton();
        button.setTooltip(new Tooltip(tooltip));
        button.getStyleClass().addAll("theme-button", styleClass);
        button.setMinSize(WIDTH, HEIGHT);
        button.setUserData(appTheme);
        button.setToggleGroup(themeGroup);
        return button;
    }

    private void onClose() {
        push();
        this.controller.onSave(preferences);
        getDialogPane().getScene().getWindow().hide();
    }

    private void setAccelerators() {
        getDialogPane().getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), this::onClose);
    }

    private void pop() {
        AppTheme appTheme = AppTheme.valueOf(preferences.getAppTheme().toUpperCase());
        switch (appTheme) {
            case LIGHT:
                btnLight.setSelected(true);
                break;
            case DARK:
                btnDark.setSelected(true);
                break;
            case BLUE:
                btnBlue.setSelected(true);
                break;
            case GREEN:
                btnGreen.setSelected(true);
                break;
            case ORANGE:
                btnOrange.setSelected(true);
                break;
            case LILA:
                btnLila.setSelected(true);
                break;
            default:
                btnLight.setSelected(true);
                break;
        }
    }

    private void push() {
        AppTheme appTheme = (AppTheme) themeGroup.getSelectedToggle().getUserData();
        preferences.setAppTheme(appTheme.getTheme());
    }

}
