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
import com.cemikta.customersfx.control.fonticon.FontIconColor;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.control.fonticon.FontIconSize;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * Login view
 *
 * @author Cem Ikta
 */
public class LoginView extends StackPane {

    private CustomTextField tfUsername;
    private CustomPasswordField pfPassword;
    private Hyperlink linkForgotPassword;

    public LoginView() {
        super();
        buildView();
    }

    private void buildView() {
        getStyleClass().add("login-view");

        // info
        Label appFontIcon = new Label();
        appFontIcon.setGraphic(FontIconFactory.createIcon(AppFontIcons.APP, FontIconSize.XXL, FontIconColor.WHITE));
        appFontIcon.getStyleClass().add("info-app-icon");

        Label infoTitle = new Label("CustomersFX");
        infoTitle.getStyleClass().add("info-title");

        VBox infoBox = new VBox();
        infoBox.getStyleClass().add("info");
        infoBox.getChildren().addAll(appFontIcon, infoTitle);

        // form
        Label formTitle = new Label(I18n.COMMON.getString("login.formTitle"));
        formTitle.getStyleClass().add("form-title");

        tfUsername = new CustomTextField();
        tfUsername.setLeft(FontIconFactory.createIcon(AppFontIcons.APP_USER));
        tfUsername.setPromptText(I18n.COMMON.getString("login.username"));

        pfPassword = new CustomPasswordField();
        pfPassword.setLeft(FontIconFactory.createIcon(AppFontIcons.APP_PASSWORD));
        pfPassword.setPromptText(I18n.COMMON.getString("login.password"));

        linkForgotPassword = new Hyperlink(I18n.COMMON.getString("login.forgotPassword"));
        linkForgotPassword.setOnAction(event -> onForgotPassword());

        Button btnLogin = ViewHelpers.createFontIconButton(I18n.COMMON.getString("login.login"), AppFontIcons.LOGIN,
                FontIconSize.XS, ContentDisplay.RIGHT);
        btnLogin.setDefaultButton(true);
        btnLogin.setOnAction(event -> onLogin());
        GridPane.setHalignment(btnLogin, HPos.RIGHT);

        GridPane loginForm = new GridPane();
        loginForm.getStyleClass().add("form");
        loginForm.add(formTitle, 0, 0, 2, 1);
        loginForm.add(tfUsername, 0, 1, 2, 1);
        loginForm.add(pfPassword, 0, 2, 2, 1);
        loginForm.add(linkForgotPassword, 0, 3);
        loginForm.add(btnLogin, 1, 3);

        HBox loginBox = new HBox();
        loginBox.getStyleClass().add("login-box");
        loginBox.getChildren().addAll(infoBox, loginForm);

        getChildren().add(loginBox);
    }

    private void onLogin() {
        if (StringUtils.isNotEmpty(tfUsername.getText()) && StringUtils.isNotEmpty(pfPassword.getText())) {
            CustomersFxApp.get().login(tfUsername.getText(), pfPassword.getText());
        }
    }

    private void onForgotPassword() {
        Text text = new Text(I18n.COMMON.getString("login.forgotPasswordInfo"));
        VBox content = new VBox(10);
        content.setPadding(new Insets(10, 10, 0, 10));
        content.getChildren().add(text);

        PopOver popOver = new PopOver(content);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        popOver.show(linkForgotPassword);
    }
}
