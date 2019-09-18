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

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.control.ButtonBarPane;
import com.cemikta.customersfx.control.IntegerField;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.controller.PreferencesController;
import com.cemikta.customersfx.model.Preferences;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;

import java.io.File;

/**
 * Application preferences dialog
 *
 * @author Cem Ikta
 */
public class PreferencesDialog extends Dialog<Preferences> {

    private PreferencesController controller;
    private Preferences preferences;
    private ValidationSupport validationSupport;

    private IntegerField tfItemsPerPage;
    private CheckBox chbShowMessageDialog;
    private CheckBox chbShowInfoPopups;
    private CheckBox chbShowTipOfTheDay;
    private CustomTextField tfReportExportDirectory;

    public PreferencesDialog(Window owner, PreferencesController controller, Preferences preferences) {
        initOwner(owner);
        this.controller = controller;
        this.preferences = preferences;
        setTitle(I18n.COMMON.getString("preferencesDialog.title"));
        buildView();
        setAccelerators();
        pop();
        getDialogPane().getScene().getWindow().setOnCloseRequest(this::onClose);
    }

    private void buildView() {
        ButtonBarPane bbpPages = new ButtonBarPane();
        bbpPages.addPage(AppFontIcons.PREFERENCES_GENERAL,
                I18n.COMMON.getString("preferencesDialog.general"),
                buildGeneralPage());
        bbpPages.addPage(AppFontIcons.PREFERENCES_REPORTING,
                I18n.COMMON.getString("preferencesDialog.reporting"),
                buildReportingPage());

        bbpPages.setMinWidth(550);
        bbpPages.setPrefWidth(550);
        bbpPages.setMaxWidth(550);

        getDialogPane().setContent(bbpPages);
    }

    private void onClose(WindowEvent event) {
        if (isFormValid()) {
            push();
            this.controller.onSave(preferences);
            getDialogPane().getScene().getWindow().hide();
        } else {
            if (event != null) {
                event.consume();
            }
        }
    }

    private Node buildGeneralPage() {
        GridPane generalPage = new GridPane();
        generalPage.setPadding(new Insets(10, 20, 20, 20));
        generalPage.setHgap(10);
        generalPage.setVgap(10);

        tfItemsPerPage = ViewHelpers.createIntegerField(100);
        getValidationSupport().registerValidator(tfItemsPerPage, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        chbShowMessageDialog = new CheckBox(I18n.COMMON.getString("preferencesDialog.showMessageDialog"));
        chbShowInfoPopups = new CheckBox(I18n.COMMON.getString("preferencesDialog.showInfoPopups"));
        chbShowTipOfTheDay = new CheckBox(I18n.COMMON.getString("preferencesDialog.showTipOfTheDay"));

        generalPage.add(new Label(I18n.COMMON.getString("preferencesDialog.itemsPerPage")), 0, 1);
        generalPage.add(tfItemsPerPage, 1, 1);
        generalPage.add(chbShowMessageDialog, 1, 2);
        generalPage.add(chbShowInfoPopups, 1, 3);
        generalPage.add(chbShowTipOfTheDay, 1, 4);

        return generalPage;
    }

    private Node buildReportingPage() {
        GridPane reportingPage = new GridPane();
        reportingPage.setPadding(new Insets(10, 20, 20, 20));
        reportingPage.setHgap(10);
        reportingPage.setVgap(10);

        tfReportExportDirectory = new CustomTextField();
        tfReportExportDirectory.setPrefWidth(300);
        Label chooseIcon = new Label("", FontIconFactory.createIcon(AppFontIcons.CHOOSE));
        chooseIcon.setTooltip(new Tooltip(I18n.COMMON.getString("preferencesDialog.openDirectoryChooser")));
        chooseIcon.setCursor(Cursor.HAND);
        chooseIcon.setOnMouseClicked(event -> {
            final DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle(I18n.COMMON.getString("preferencesDialog.directoryChooserTitle"));
            final File selectedDirectory = directoryChooser.showDialog(getOwner());
            if (selectedDirectory != null) {
                tfReportExportDirectory.setText(selectedDirectory.getAbsolutePath());
            }
        });
        tfReportExportDirectory.setRight(chooseIcon);

        reportingPage.add(new Label(I18n.COMMON.getString("preferencesDialog.reportExportDirectory")), 0, 1);
        reportingPage.add(tfReportExportDirectory, 1, 1);

        return reportingPage;
    }

    private void setAccelerators() {
        getDialogPane().getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> onClose(null));
    }

    private void pop() {
        // general page
        tfItemsPerPage.setText(preferences.getItemsPerPage() != null ? preferences.getItemsPerPage().toString() : "");
        chbShowMessageDialog.setSelected(preferences.isShowMessageDialog() != null ?
                preferences.isShowMessageDialog() : false);
        chbShowInfoPopups.setSelected(preferences.isShowInfoPopups() != null ?
                preferences.isShowInfoPopups() : false);
        chbShowTipOfTheDay.setSelected(preferences.isShowTipOfTheDay() != null ?
                preferences.isShowTipOfTheDay() : false);

        // reporting page
        tfReportExportDirectory.setText(preferences.getReportExportDirectory());
    }

    private void push() {
        // general page
        preferences.setItemsPerPage(tfItemsPerPage.getValue());
        preferences.setShowMessageDialog(chbShowMessageDialog.isSelected());
        preferences.setShowInfoPopups(chbShowInfoPopups.isSelected());
        preferences.setShowTipOfTheDay(chbShowTipOfTheDay.isSelected());

        // reporting page
        preferences.setReportExportDirectory(tfReportExportDirectory.getText());
    }

    private ValidationSupport getValidationSupport() {
        if (validationSupport == null) {
            validationSupport = new ValidationSupport();
            validationSupport.setValidationDecorator(new GraphicValidationDecoration());
        }
        return validationSupport;
    }

    private boolean isFormValid() {
        if (getValidationSupport().isInvalid()) {
            getValidationSupport().initInitialDecoration();
            if (CustomersFxApp.get().getPreferences().isShowInfoPopups()) {
                Notifications.create()
                        .text(I18n.COMMON.getString("notification.saveValidationError"))
                        .position(Pos.TOP_RIGHT).showWarning();
            }
        }
        return !getValidationSupport().isInvalid();
    }

}
