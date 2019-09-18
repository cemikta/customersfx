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
import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.control.fonticon.FontIconSize;
import com.cemikta.customersfx.control.tips.Tip;
import com.cemikta.customersfx.util.CssHelpers;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Tip of the day control
 *
 * <p>The example file tips.properties has tree keys: optional name, description and optional imagePath
 * with the following content:
 *
 * <pre>{@code tip.1.name=First tip
 * tip.1.description=<strong>First tip</strong> description.
 * tip.1.imagePath=/images/firstTip.png
 * tip.2.name=Second tip
 * tip.2.description=Second tip description.}</pre>
 *
 * <p>Code snippet for tip of the day control:
 *
 * <pre>{@code TipOfTheDay tipOfTheDay = new TipOfTheDay(mainStage, "tips.properties");
 * tipOfTheDay.getShowOnStartupCheckBox().setSelected(true);
 * tipOfTheDay.getShowOnStartupCheckBox().selectedProperty().addListener((observable, oldValue, newValue) -> {
 *      // showOnStartupCheckBox changed, save in preferences
 * });
 * tipOfTheDay.showAndWait();}</pre>
 *
 * @see Tip
 * @see Dialog
 * @see Properties
 *
 * @author Cem Ikta
 */
public class TipOfTheDay extends Dialog<Void> {

    private final Logger logger = LoggerFactory.getLogger(TipOfTheDay.class);

    private String tipsPropertiesPath;
    private List<Tip> tips;
    private WebView tipBrowser;
    private CheckBox chbShowOnStartup;
    private int currentTip = 0;

    /**
     * Creates new instance of tip of the day control.
     *
     * @param owner the owner stage {@link Window} for this control.
     * @param tipsPropertiesPath the tips {@link Properties} file
     */
    public TipOfTheDay(Window owner, String tipsPropertiesPath) {
        super();
        initOwner(owner);
        this.tipsPropertiesPath = tipsPropertiesPath;
        setTitle(I18n.CONTROL.getString("tipOfTheDay.title"));
        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> onClose());
        buildView();
        setAccelerators();
    }

    private void buildView() {
        Label lblDidYouKnow = new Label(I18n.CONTROL.getString("tipOfTheDay.didYouKnow"),
                FontIconFactory.createIcon(AppFontIcons.DID_YOU_KNOW, FontIconSize.MD));
        CssHelpers.setFontSize(lblDidYouKnow, 16);

        tipBrowser = new WebView();
        tipBrowser.setContextMenuEnabled(false);
        tipBrowser.setMinSize(540, 200);
        tipBrowser.setPrefSize(540, 200);
        tipBrowser.setMaxSize(540, 200);

        VBox browserBox = new VBox();
        browserBox.getStyleClass().add("tip-of-the-day-border");
        browserBox.getChildren().add(tipBrowser);

        chbShowOnStartup = new CheckBox(I18n.CONTROL.getString("tipOfTheDay.showOnStartup"));

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button btnPreviousTip = ViewHelpers.createFontIconButton(I18n.CONTROL.getString("tipOfTheDay.previousTip"),
                AppFontIcons.PREVIOUS_TIP, FontIconSize.XS, ContentDisplay.LEFT);
        btnPreviousTip.setMinWidth(100);
        btnPreviousTip.setOnAction(event -> onPreviousTip());

        Button btnNextTip = ViewHelpers.createFontIconButton(I18n.CONTROL.getString("tipOfTheDay.nextTip"),
                AppFontIcons.NEXT_TIP, FontIconSize.XS, ContentDisplay.RIGHT);
        btnNextTip.setMinWidth(100);
        btnNextTip.setDefaultButton(true);
        btnNextTip.setOnAction(event -> onNextTip());

        Button btnClose = ViewHelpers.createFontIconButton(I18n.CONTROL.getString("tipOfTheDay.close"),
                AppFontIcons.CLOSE, FontIconSize.XS, ContentDisplay.RIGHT);
        btnClose.setMinWidth(100);
        btnClose.setOnAction(event -> onClose());

        HBox buttonBar = new HBox(10);
        buttonBar.getChildren().addAll(spacer, btnPreviousTip, btnNextTip, btnClose);

        VBox content = new VBox(10);
        content.getChildren().addAll(lblDidYouKnow, browserBox, chbShowOnStartup, buttonBar);
        getDialogPane().setContent(content);

        loadTips();
        showCurrentTip();
    }

    /**
     * Gets the CheckBox for showing on startup option.
     *
     * @return The show on startup CheckBox.
     */
    public CheckBox getShowOnStartupCheckBox() {
        return chbShowOnStartup;
    }

    private void onPreviousTip() {
        int count = tips.size();
        if (count == 0) {
            return;
        }

        int previousTip = currentTip - 1;
        if (previousTip < 0) {
            previousTip = count - 1;
        }
        setCurrentTip(previousTip);
    }

    private void onNextTip() {
        int count = tips.size();
        if (count == 0) {
            return;
        }

        int nextTip = currentTip + 1;
        if (nextTip >= count) {
            nextTip = 0;
        }
        setCurrentTip(nextTip);
    }

    private int getCurrentTip() {
        return currentTip;
    }

    private void setCurrentTip(int currentTip) {
        if (currentTip < 0 || currentTip >= tips.size()) {
            throw new IllegalArgumentException("Current tip must be within the bounds [0, " + tips.size() + "]");
        }

        this.currentTip = currentTip;
        showCurrentTip();
    }

    private void showCurrentTip() {
        Tip tip = tips.get(getCurrentTip());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body><p>");
        stringBuilder.append(tip.getTip());
        stringBuilder.append("</p>");
        if (StringUtils.isNotEmpty(tip.getImagePath())) {
            stringBuilder.append("<p><img src='");
            stringBuilder.append(this.getClass().getResource(tip.getImagePath()).toString());
            stringBuilder.append("' width='500' height='auto'/></p>");
        }


        stringBuilder.append("</body></html>");
        tipBrowser.getEngine().loadContent(stringBuilder.toString());
    }

    private void loadTips() {
        Properties tipsProperties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(tipsPropertiesPath);
        try {
            tipsProperties.load(inputStream);
        } catch (IOException e) {
            logger.error("tips.properties file load error {}", e);
            MessageBox.create()
                    .owner(CustomersFxApp.get().getMainStage())
                    .contentText(I18n.CONTROL.getString("tipOfTheDay.tipLoadError"))
                    .showError(e);
        }

        tips = new ArrayList<>();
        int count = 1;
        while (true) {
            String nameKey = "tip." + count + ".name";
            String nameValue = tipsProperties.getProperty(nameKey);
            String descriptionKey = "tip." + count + ".description";
            String descriptionValue = tipsProperties.getProperty(descriptionKey);
            String imagePathKey = "tip." + count + ".imagePath";
            String imagePathValue = tipsProperties.getProperty(imagePathKey);

            if (nameValue != null && descriptionValue == null) {
                throw new IllegalArgumentException("No description for name " + nameValue); }

            if (descriptionValue == null) {
                break;
            }

            Tip tip = new Tip(nameValue, descriptionValue, imagePathValue);
            tips.add(tip);
            count++;
        }
    }

    private void setAccelerators() {
        getDialogPane().getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), this::onClose);
    }

    private void onClose() {
        getDialogPane().getScene().getWindow().hide();
    }

}
