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
import com.cemikta.customersfx.control.*;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.controller.*;
import com.cemikta.customersfx.mvc.DataPageView;
import com.cemikta.customersfx.mvc.View;
import com.cemikta.customersfx.util.I18n;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Application view
 *
 * @author Cem Ikta
 */
public class AppView extends BorderPane {

    private final static Logger logger = LoggerFactory.getLogger(AppView.class);
    private BorderPane appCenter;
    private StatusBar statusBar;

    // home commands
    private RibbonCommand dashboardCommand;
    private RibbonCommand customerCommand;
    private RibbonCommand categoryCommand;
    private RibbonCommand countryCommand;
    private RibbonCommand userCommand;
    private RibbonCommand exitCommand;

    // view commands
    private RibbonCommand statusBarCommand;
    private RibbonCommand themesCommand;

    // extras tab
    private RibbonCommand preferencesCommand;

    // help tab
    private RibbonCommand helpCommand;
    private RibbonCommand keyboardShortcutsCommand;
    private RibbonCommand tipOfTheDayCommand;
    private RibbonCommand aboutCommand;

    public AppView() {
        super();
        buildView();
    }

    private void buildView() {
        getStyleClass().add("app-view");
        setPadding(new Insets(10, 0, 0, 0));
        setTop(buildRibbonMenu());
        setCenter(buildAppCenter());
        setBottom(buildStatusBar());
    }

    private void onOpenDashboard() {
        logger.info("On open dashboard");
        addPageToCenter(new DashboardController().getPageView());
    }

    private void onOpenCustomer() {
        logger.info("On open customer");
        addPageToCenter(new CustomerController().getDataPageView());
    }

    private void onOpenCategory() {
        logger.info("On open category");
        addPageToCenter(new CategoryController().getDataPageView());
    }

    private void onOpenCountry() {
        logger.info("On open country");
        addPageToCenter(new CountryController().getDataPageView());
    }

    private void onOpenUser() {
        logger.info("On open user");
        addPageToCenter(new UserController().getDataPageView());
    }

    public void onToggleStatusBar() {
        logger.info("On toggle statusbar");
        statusBar.setVisible(!statusBar.isVisible());
        if (statusBar.isVisible()) {
          setBottom(statusBar);
        } else {
            setBottom(null);
        }
    }

    private void onOpenThemes() {
        logger.info("On open themes dialog");
        new PreferencesController().showThemes();
    }

    private void onOpenPreferences() {
        logger.info("On open preferences dialog");
        new PreferencesController().showPreferences();
    }

    private void onOpenHelp() {
        logger.info("On open app help dialog");
        CustomersFxApp.get().showAppHelp();
    }

    private void onOpenKeyboardShortcuts() {
        logger.info("On open keyboard shortcuts dialog");
        new ShortcutsDialog(CustomersFxApp.get().getMainStage()).showAndWait();
    }

    private void onOpenTipOfTheDay() {
        logger.info("On open tip of the day dialog");
        CustomersFxApp.get().showTipOfTheDay();
    }

    private void onOpenAbout() {
        logger.info("On open about dialog");
        new AboutDialog.Builder()
                .owner(CustomersFxApp.get().getMainStage())
                .title(I18n.COMMON.getString("aboutDialog.title"))
                .appIcon(AppFontIcons.APP)
                .appName(I18n.COMMON.getString("aboutDialog.appName"))
                .appVersion(I18n.COMMON.getString("aboutDialog.appVersion"))
                .appCopyright(I18n.COMMON.getString("aboutDialog.appCopyright"))
                .appHomepage("http://cemikta.com")
                .build()
                .showAndWait();
    }

    public void addPageToCenter(View page) {
        logger.info("Add page to center with page {}", page);
        appCenter.setLeft(null);
        appCenter.setRight(null);
        appCenter.setTop(null);
        appCenter.setCenter(page.asNode());
        appCenter.setBottom(null);
        if (page instanceof DataPageView) {
            ((DataPageView) page).setDataPageAccelerators();
        }
        page.asNode().requestFocus();
    }

    private BorderPane buildAppCenter() {
        appCenter = new BorderPane();
        appCenter.setId("app-center");
        return appCenter;
    }

    private RibbonPane buildRibbonMenu() {
        return new RibbonPane(buildHomeTab(), buildViewTab(), buildExtrasTab(), buildHelpTab());
    }

    private Tab buildHomeTab() {
        dashboardCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.dashboard"), AppFontIcons.DASHBOARD);
        dashboardCommand.setOnAction(event -> onOpenDashboard());

        customerCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.customer"), AppFontIcons.CUSTOMER);
        customerCommand.setOnAction(event -> onOpenCustomer());

        categoryCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.category"), AppFontIcons.CATEGORY);
        categoryCommand.setOnAction(event -> onOpenCategory());

        countryCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.country"), AppFontIcons.COUNTRY);
        countryCommand.setOnAction(event -> onOpenCountry());

        userCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.user"), AppFontIcons.USER);
        userCommand.setOnAction(event -> onOpenUser());

        exitCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.exit"), AppFontIcons.EXIT);
        exitCommand.setOnAction(event -> CustomersFxApp.get().onAppExit(null));

        RibbonGroup homeGroup = new RibbonGroup(dashboardCommand, RibbonGroup.addSeparator(),
                customerCommand, categoryCommand, countryCommand, RibbonGroup.addSeparator(),
                userCommand, RibbonGroup.addSeparator(), exitCommand);

        return new RibbonTab(I18n.COMMON.getString("app.ribbon.home"), homeGroup);
    }

    private Tab buildViewTab() {
        statusBarCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.statusbar"), AppFontIcons.STATUS_BAR);
        statusBarCommand.setOnAction(event -> onToggleStatusBar());

        themesCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.themes"), AppFontIcons.THEMES);
        themesCommand.setOnAction(event -> onOpenThemes());

        RibbonGroup viewGroup = new RibbonGroup(statusBarCommand, themesCommand);

        return new RibbonTab(I18n.COMMON.getString("app.ribbon.view"), viewGroup);
    }

    private Tab buildExtrasTab() {
        preferencesCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.preferences"), AppFontIcons.PREFERENCES);
        preferencesCommand.setOnAction(event -> onOpenPreferences());

        RibbonGroup settingsGroup = new RibbonGroup(preferencesCommand);

        return new RibbonTab(I18n.COMMON.getString("app.ribbon.extras"), settingsGroup);
    }

    private Tab buildHelpTab() {
        helpCommand = new RibbonCommand(I18n.COMMON.getString("app.menu.help"), AppFontIcons.HELP);
        helpCommand.setOnAction(event -> onOpenHelp());

        keyboardShortcutsCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.keyboardShortcuts"), AppFontIcons.KEYBOARD_SHORTCUTS);
        keyboardShortcutsCommand.setOnAction(event -> onOpenKeyboardShortcuts());

        tipOfTheDayCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.tipOfTheDay"), AppFontIcons.TIP_OF_THE_DAY);
        tipOfTheDayCommand.setOnAction(event -> onOpenTipOfTheDay());

        aboutCommand = new RibbonCommand(
                I18n.COMMON.getString("app.menu.about"), AppFontIcons.ABOUT);
        aboutCommand.setOnAction(event -> onOpenAbout());

        RibbonGroup helpGroup = new RibbonGroup(helpCommand, keyboardShortcutsCommand, tipOfTheDayCommand,
                RibbonGroup.addSeparator(), aboutCommand);

        return new RibbonTab(I18n.COMMON.getString("app.ribbon.help"), helpGroup);
    }

    private StatusBar buildStatusBar() {
        Label loggedInUser = new Label(CustomersFxApp.get().getLoggedInUser().getName(),
                FontIconFactory.createIcon(AppFontIcons.APP_USER));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE', 'dd. MMMM yyyy");
        Label currentDate = new Label(sdf.format(new Date()), FontIconFactory.createIcon(AppFontIcons.APP_DATE));

        statusBar = new StatusBar();
        statusBar.getLeftItems().addAll(loggedInUser, currentDate);
        return statusBar;
    }

    public void setRibbonAccelerators() {
        Scene scene = this.getScene();
        if (scene == null) {
            throw new IllegalArgumentException("setRibbonAccelerators must be called when the AppView is " +
                    "attached to a scene");
        }
        // home tab
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D, KeyCombination.ALT_DOWN), dashboardCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN), customerCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.A, KeyCombination.ALT_DOWN), categoryCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.O, KeyCombination.ALT_DOWN), countryCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.U, KeyCombination.ALT_DOWN), userCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.Q, KeyCombination.ALT_DOWN), exitCommand::fire);
        // view tab
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN), statusBarCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.T, KeyCombination.ALT_DOWN), themesCommand::fire);
        // extras tab
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.P, KeyCombination.ALT_DOWN),
                preferencesCommand::fire);
        // help tab
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F1), helpCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.K, KeyCombination.ALT_DOWN),
                keyboardShortcutsCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.I, KeyCombination.ALT_DOWN),
                tipOfTheDayCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.B, KeyCombination.ALT_DOWN), aboutCommand::fire);
    }

}
