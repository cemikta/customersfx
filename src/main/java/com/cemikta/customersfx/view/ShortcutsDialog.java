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

import com.cemikta.customersfx.control.ShortcutBox;
import com.cemikta.customersfx.util.I18n;
import javafx.geometry.Insets;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;
import org.apache.commons.lang3.SystemUtils;

import static com.cemikta.customersfx.control.ShortcutBox.PLUS;

/**
 * Keyboard shortcuts dialog
 *
 * @author Cem Ikta
 */
public class ShortcutsDialog extends Dialog<Void> {

    private GridPane content;
    private String shortcutDown;
    private String altDown;

    public ShortcutsDialog(Window owner) {
        initOwner(owner);
        setTitle(I18n.COMMON.getString("shortcutsDialog.title"));
        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> onClose());
        buildView();
        setAccelerators();
    }

    private void buildView() {
        if (SystemUtils.IS_OS_MAC) {
            shortcutDown = I18n.COMMON.getString("shortcutsDialog.shortcutDownMac");
            altDown = I18n.COMMON.getString("shortcutsDialog.altDownMac");
        } else {
            shortcutDown = I18n.COMMON.getString("shortcutsDialog.shortcutDown");
            altDown = I18n.COMMON.getString("shortcutsDialog.altDown");
        }

        content = new GridPane();
        content.setPadding(new Insets(20, 30, 20, 30));
        content.setHgap(20);
        content.setVgap(10);
        buildMainMenuShortcuts();
        buildPagesShortcuts();
        buildFormsShortcuts();
        getDialogPane().setContent(content);
    }

    private void buildMainMenuShortcuts() {
        Label mainMenuShortcuts = new Label(I18n.COMMON.getString("shortcutsDialog.mainMenuShortcuts"));
        mainMenuShortcuts.getStyleClass().add("shortcuts-title");

        ShortcutBox dashboardShortcut = new ShortcutBox(altDown, PLUS, "H");
        ShortcutBox customerShortcut = new ShortcutBox(altDown, PLUS, "C");
        ShortcutBox categoryShortcut = new ShortcutBox(altDown, PLUS, "A");
        ShortcutBox countryShortcut = new ShortcutBox(altDown, PLUS, "O");
        ShortcutBox userShortcut = new ShortcutBox(altDown, PLUS, "U");
        ShortcutBox exitShortcut = new ShortcutBox(altDown, PLUS, "Q");
        ShortcutBox statusBarShortcut = new ShortcutBox(altDown, PLUS, "S");
        ShortcutBox themesShortcut = new ShortcutBox(altDown, PLUS, "T");
        ShortcutBox preferencesShortcut = new ShortcutBox(altDown, PLUS, "P");
        ShortcutBox helpShortcut = new ShortcutBox("F1");
        ShortcutBox keyboardShortcutsShortcut = new ShortcutBox(altDown, PLUS, "K");
        ShortcutBox tipOfTheDayShortcut = new ShortcutBox(altDown, PLUS, "I");
        ShortcutBox aboutShortcut = new ShortcutBox(altDown, PLUS, "B");

        content.add(mainMenuShortcuts, 0, 0, 2, 1);

        content.add(dashboardShortcut, 0, 1);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.dashboardShortcut")), 1, 1);

        content.add(customerShortcut, 0, 2);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.customerShortcut")), 1, 2);

        content.add(categoryShortcut, 0, 3);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.categoryShortcut")), 1, 3);

        content.add(countryShortcut, 0, 4);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.countryShortcut")), 1, 4);

        content.add(userShortcut, 0, 5);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.userShortcut")), 1, 5);

        content.add(exitShortcut, 0, 6);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.exitShortcut")), 1, 6);

        content.add(statusBarShortcut, 0, 7);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.statusBarShortcut")), 1, 7);

        content.add(themesShortcut, 0, 8);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.themesShortcut")), 1, 8);

        content.add(preferencesShortcut, 0, 9);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.preferencesShortcut")), 1, 9);

        content.add(helpShortcut, 0, 10);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.helpShortcut")), 1, 10);

        content.add(keyboardShortcutsShortcut, 0, 11);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.keyboardShortcutsShortcut")), 1, 11);

        content.add(tipOfTheDayShortcut, 0, 12);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.tipOfTheDayShortcut")), 1, 12);

        content.add(aboutShortcut, 0, 13);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.aboutShortcut")), 1, 13);
    }

    private void buildPagesShortcuts() {
        Label pagesShortcuts = new Label(I18n.COMMON.getString("shortcutsDialog.pageShortcuts"));
        pagesShortcuts.getStyleClass().add("shortcuts-title");

        ShortcutBox addNewShortcut = new ShortcutBox(shortcutDown, PLUS, "N");
        ShortcutBox editShortcut = new ShortcutBox(shortcutDown, PLUS, "E");
        ShortcutBox deleteShortcut = new ShortcutBox(shortcutDown, PLUS, "D");
        ShortcutBox printPreviewShortcut = new ShortcutBox(shortcutDown, PLUS, "R");
        ShortcutBox printShortcut = new ShortcutBox(shortcutDown, PLUS, "P");
        ShortcutBox pdfShortcut = new ShortcutBox(shortcutDown, PLUS, "F");

        content.add(pagesShortcuts, 3, 0, 2, 1);

        content.add(addNewShortcut, 3, 1);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.addNewShortcut")), 4, 1);

        content.add(editShortcut, 3, 2);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.editShortcut")), 4, 2);

        content.add(deleteShortcut, 3, 3);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.deleteShortcut")), 4, 3);

        content.add(printPreviewShortcut, 3, 4);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.printPreviewShortcut")), 4, 4);

        content.add(printShortcut, 3, 5);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.printShortcut")), 4, 5);

        content.add(pdfShortcut, 3, 6);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.pdfShortcut")), 4, 6);
    }

    private void buildFormsShortcuts() {
        Label formsShortcuts = new Label(I18n.COMMON.getString("shortcutsDialog.formsShortcuts"));
        formsShortcuts.getStyleClass().add("shortcuts-title");

        ShortcutBox saveShortcut = new ShortcutBox(shortcutDown, PLUS, "S");
        ShortcutBox saveAndCloseShortcut = new ShortcutBox(shortcutDown, PLUS, "A");
        ShortcutBox formPrintPreviewShortcut = new ShortcutBox(shortcutDown, PLUS, "R");
        ShortcutBox formPrintShortcut = new ShortcutBox(shortcutDown, PLUS, "P");
        ShortcutBox formPdfShortcut = new ShortcutBox(shortcutDown, PLUS, "F");
        ShortcutBox escShortcut = new ShortcutBox("ESC");
        ShortcutBox formHelpShortcut = new ShortcutBox("F1");

        content.add(formsShortcuts, 6, 0, 2, 1);

        content.add(saveShortcut, 6, 1);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.saveShortcut")), 7, 1);

        content.add(saveAndCloseShortcut, 6, 2);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.saveAndCloseShortcut")), 7, 2);

        content.add(formPrintPreviewShortcut, 6, 3);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.formPrintPreviewShortcut")), 7, 3);

        content.add(formPrintShortcut, 6, 4);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.formPrintShortcut")), 7, 4);

        content.add(formPdfShortcut, 6, 5);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.formPdfShortcut")), 7, 5);

        content.add(escShortcut, 6, 6);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.escShortcut")), 7, 6);

        content.add(formHelpShortcut, 6, 7);
        content.add(new Label(I18n.COMMON.getString("shortcutsDialog.formHelpShortcut")), 7, 7);
    }

    private void setAccelerators() {
        getDialogPane().getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), this::onClose);
    }

    private void onClose() {
        getDialogPane().getScene().getWindow().hide();
    }

}
