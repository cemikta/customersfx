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

import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.control.fonticon.FontIconColor;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.control.fonticon.FontIconSize;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Splash control
 *
 * <p>Code snippet for splash screen:
 *
 * <pre>{@code Splash splash = new Splash(appFontIcon);
 * splash.getProgressText().textProperty().bind(task.messageProperty());
 * splash.getLoadProgress().progressProperty().bind(task.progressProperty());}</pre>
 *
 * @see StackPane
 *
 * @author Cem Ikta
 */
public class Splash extends StackPane {

    private static final String DEFAULT_STYLE_CLASS = "splash";
    private static final String SPLASH_PROGRESS_BAR = "splash-progress-bar";
    private static final String SPLASH_PROGRESS_TEXT = "splash-progress-text";

    private Text splashImageFontIcon;
    private ImageView splashImageView;
    private ProgressBar loadProgress;
    private Label progressText;

    public Splash(FontIcon imageFontIcon) {
        splashImageFontIcon = FontIconFactory.createIcon(imageFontIcon, FontIconSize.XXXL, FontIconColor.BLUE);
        buildView();
    }

    public Splash(String imagePath) {
        splashImageView = new ImageView(new Image(imagePath));
        buildView();
    }

    private void buildView() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        setPrefSize(500, 340);
        setAlignment(Pos.CENTER);
        setEffect(new DropShadow());

        loadProgress = new ProgressBar();
        loadProgress.getStyleClass().add(SPLASH_PROGRESS_BAR);
        loadProgress.setPrefWidth(400);

        progressText = new Label("Loading module");
        progressText.getStyleClass().add(SPLASH_PROGRESS_TEXT);
        progressText.setPrefWidth(400);

        VBox progressBox = new VBox(5);
        progressBox.setPrefWidth(400);
        progressBox.getChildren().addAll(loadProgress, progressText);
        StackPane.setMargin(progressBox, new Insets(290, 50, 20, 50));

        if (splashImageView != null) {
            getChildren().addAll(splashImageView, progressBox);
        } else {
            getChildren().addAll(splashImageFontIcon, progressBox);
        }
    }

    public ProgressBar getLoadProgress() {
        return loadProgress;
    }

    public Label getProgressText() {
        return progressText;
    }
}
