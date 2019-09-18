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

package com.cemikta.customersfx;

import com.cemikta.customersfx.app.*;
import com.cemikta.customersfx.control.AppHelp;
import com.cemikta.customersfx.control.MessageBox;
import com.cemikta.customersfx.control.Splash;
import com.cemikta.customersfx.control.TipOfTheDay;
import com.cemikta.customersfx.controller.DashboardController;
import com.cemikta.customersfx.model.Preferences;
import com.cemikta.customersfx.model.User;
import com.cemikta.customersfx.service.PreferencesService;
import com.cemikta.customersfx.service.UserService;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.JpaUtil;
import com.cemikta.customersfx.view.AppView;
import com.cemikta.customersfx.view.LoginView;
import com.sun.javafx.css.StyleManager;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static com.cemikta.customersfx.service.QueryParameter.with;
import static javafx.scene.control.ButtonBar.ButtonData;

/**
 * CustomersFX MVC Application Demo
 *
 * @author Cem Ikta
 */
public class CustomersFxApp extends Application {

    private final Logger logger = LoggerFactory.getLogger(CustomersFxApp.class);
    private static CustomersFxApp customersFxApp;
    private Stage mainStage;
    private Scene mainScene;
    private Splash splash;
    private AppView appView;
    private User loggedInUser;
    private PreferencesService preferencesService;
    private Preferences preferences;

    public static void main(String[] args) throws Exception {
        // english
        Locale.setDefault(new Locale("en", "GB"));
        // german
        //Locale.setDefault(new Locale("de", "DE"));
        // turkish
        //Locale.setDefault(new Locale("tr", "TR"));
        launch(args);
    }

    @Override
    public void init() throws Exception {
        logger.info("init application");
        AppDbMigrator appDbMigrator = new AppDbMigrator();
        appDbMigrator.migrate();
        readPreferences();
    }

    @Override
    public void start(Stage initStage) throws Exception {
        logger.info("start application");
        customersFxApp = this;
        final SplashTask splashTask = new SplashTask();
        showSplash(initStage, splashTask, this::showMainStage);
        new Thread(splashTask).start();
    }

    private void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        splash = new Splash(AppFontIcons.APP);
        splash.getProgressText().textProperty().bind(task.messageProperty());
        splash.getLoadProgress().progressProperty().bind(task.progressProperty());

        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                splash.getLoadProgress().progressProperty().unbind();
                splash.getLoadProgress().setProgress(1);
                initStage.toFront();

                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splash);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene(splash);
        splashScene.getStylesheets().add(getClass().getResource(getCurrentAppTheme().getThemeStyle()).toExternalForm());

        initStage.initStyle(StageStyle.UNDECORATED);
        initStage.setScene(splashScene);
        initStage.show();
    }

    private void showMainStage() {
        mainScene = new Scene(new LoginView(), AppFeatures.WIDTH, AppFeatures.HEIGHT);
        mainScene.getStylesheets().add(getClass().getResource(getCurrentAppTheme().getThemeStyle()).toExternalForm());

        mainStage = new Stage();
        mainStage.setTitle(I18n.COMMON.getString("app.title"));
        mainStage.getIcons().add(new Image(CustomersFxApp.class.getResourceAsStream(AppFeatures.APP_ICON)));
        mainStage.setOnCloseRequest(this::onAppExit);
        mainStage.setScene(mainScene);
        mainStage.setMaximized(true);
        mainStage.show();
    }

    private void showAppView() {
        appView = new AppView();
        appView.addPageToCenter(new DashboardController().getPageView());
        mainScene.setRoot(appView);
        appView.setRibbonAccelerators();

        if (preferences.isShowTipOfTheDay()) {
            showTipOfTheDay();
        }
    }

    public void showTipOfTheDay() {
        TipOfTheDay tipOfTheDay = new TipOfTheDay(getMainStage(), getTipsProperties());
        tipOfTheDay.getShowOnStartupCheckBox().setSelected(preferences.isShowTipOfTheDay());
        tipOfTheDay.getShowOnStartupCheckBox().selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (preferences.isShowTipOfTheDay() != newValue) {
                preferences.setShowTipOfTheDay(newValue);
                preferences = preferencesService.update(preferences);
            }
        });
        tipOfTheDay.showAndWait();
    }

    private String getTipsProperties() {
        String locale = Locale.getDefault().toString();
        switch (locale) {
            case "en_GB": return AppFeatures.TIPS_PROPERTIES;
            case "de_DE": return AppFeatures.TIPS_DE_PROPERTIES;
            case "tr_TR": return AppFeatures.TIPS_TR_PROPERTIES;
            default: throw new IllegalArgumentException("Tips of the locale not found!");
        }
    }

    public void showAppHelp() {
        new AppHelp.Builder()
                .title(I18n.COMMON.getString("appHelp.title"))
                .iconPath(AppFeatures.APP_ICON)
                .helpHtmlPath("/help/help.html")
                .stylesheet(getCurrentAppTheme().getThemeStyle())
                .build()
                .show();
    }

    public AppTheme getCurrentAppTheme() {
        return AppTheme.valueOf(preferences.getAppTheme().toUpperCase());
    }

    public void changeAppTheme(AppTheme appTheme) {
        mainScene.getStylesheets().clear();
        mainScene.getStylesheets().add(getClass().getResource(appTheme.getThemeStyle()).toExternalForm());
    }

    public interface InitCompletionHandler {

        void complete();
    }

    /**
     * Gets application instance.
     *
     * @return app instance
     */
    public static CustomersFxApp get() {
        return customersFxApp;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Gets application view.
     *
     * @return app view
     */
    public AppView getAppView() {
        return appView;
    }

    public void login(String username, String password) {
        logger.info("login process");
        UserService userService = new UserService();
        User user = userService.findOneWithNamedQuery(User.FIND_BY_USERNAME, with("username", username).parameters());

        if (user == null) {
            Notifications.create()
                    .text(I18n.COMMON.getString("login.userNotFound"))
                    .position(Pos.TOP_RIGHT).showInformation();
        } else if (!user.isActive()) {
            Notifications.create()
                    .text(I18n.COMMON.getString("login.userNotActive"))
                    .position(Pos.TOP_RIGHT).showInformation();
        } else {
            if (Objects.equals(password, user.getPassword())) {
                setLoggedInUser(user);
                showAppView();
            } else {
                Notifications.create()
                        .text(I18n.COMMON.getString("login.wrongPassword"))
                        .position(Pos.TOP_RIGHT).showInformation();
            }
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    private void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    private void readPreferences() {
        logger.info("read app preferences");
        preferencesService = new PreferencesService();
        preferences = preferencesService.find(1L);
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public void onAppExit(WindowEvent windowEvent) {
        if (preferences.isShowMessageDialog()) {
            Optional<ButtonType> result = MessageBox.create()
                    .owner(CustomersFxApp.get().getMainStage())
                    .contentText(I18n.COMMON.getString("confirm.appExit"))
                    .showExitConfirmation();
            if (result.isPresent() && result.get().getButtonData() == ButtonData.OK_DONE) {
                exitApplication();
            } else {
                if (windowEvent != null) {
                    windowEvent.consume();
                }
            }
        } else {
            exitApplication();
        }
    }

    private void exitApplication() {
        logger.info("exit application");
        JpaUtil.closeEntityManagerFactory();
        Platform.exit();
    }

}
