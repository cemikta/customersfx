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

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.control.fonticon.FontIconColor;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.control.fonticon.FontIconSize;
import com.cemikta.customersfx.util.CssHelpers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * About dialog
 *
 * <p>Below is the code snippet for the application about dialog with builder:
 *
 * <pre>{@code new AboutDialog.Builder()
 *      .owner(CustomersFxApp.get().getMainStage())
 *      .title(I18n.COMMON.getString("aboutDialog.title"))
 *      .appIcon(AppFontIcons.APP)
 *      .appName(I18n.COMMON.getString("aboutDialog.appName"))
 *      .appVersion(I18n.COMMON.getString("aboutDialog.appVersion"))
 *      .appCopyright(I18n.COMMON.getString("aboutDialog.appCopyright"))
 *      .appHomepage("http://cemikta.com")
 *      .build()
 *      .showAndWait();}</pre>
 *
 * @see Dialog
 *
 * @author Cem Ikta
 */
public class AboutDialog extends Dialog<Void> {

    private AboutDialog(Builder builder) {
        initOwner(builder.owner);
        setTitle(builder.title);
        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> onClose());

        Text appFontIcon = FontIconFactory.createIcon(builder.appIcon, FontIconSize.XXL, FontIconColor.BLUE);
        Label appName = new Label(builder.appName);
        CssHelpers.setFontBoldSize(appName, 14);
        Label appVersion = new Label(builder.appVersion);
        Label appCopyright = new Label(builder.appCopyright);
        appCopyright.setTextAlignment(TextAlignment.CENTER);
        Hyperlink appHomepage = new Hyperlink(builder.appHomepage);
        appHomepage.setOnAction(event -> CustomersFxApp.get().getHostServices().showDocument(appHomepage.getText()));

        VBox aboutBox = new VBox(10);
        aboutBox.setPadding(new Insets(10, 30, 10, 30));
        aboutBox.setAlignment(Pos.CENTER);
        aboutBox.getChildren().addAll(appFontIcon, appName, appVersion, appCopyright, appHomepage);
        getDialogPane().setContent(aboutBox);
        setAccelerators();
    }

    private void setAccelerators() {
        getDialogPane().getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), this::onClose);
    }

    private void onClose() {
        getDialogPane().getScene().getWindow().hide();
    }

    public static class Builder {

        private Window owner;
        private String title;
        private FontIcon appIcon;
        private String appName;
        private String appVersion;
        private String appCopyright;
        private String appHomepage;

        public Builder owner(Window owner) {
            this.owner = owner;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder appIcon(FontIcon appIcon) {
            this.appIcon = appIcon;
            return this;
        }

        public Builder appName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder appVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder appCopyright(String appCopyright) {
            this.appCopyright = appCopyright;
            return this;
        }

        public Builder appHomepage(String appHomepage) {
            this.appHomepage = appHomepage;
            return this;
        }

        public AboutDialog build() {
            return new AboutDialog(this);
        }
    }

}
