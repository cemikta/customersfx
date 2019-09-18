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

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Application help
 *
 * <p>Below is the code snippet application help with builder:
 *
 * <pre>{@code new AppHelp.Builder()
 *      .title("App help title"))
 *      .iconPath("/images/app.png")
 *      .helpHtmlPath("/help/help.html")
 *      .stylesheet("/styles/style.css")
 *      .build()
 *      .show();}</pre>
 *
 * @see Stage
 *
 * @author Cem Ikta
 */
public class AppHelp extends Stage {

    private AppHelp(AppHelp.Builder builder) {
        setTitle(builder.title);
        getIcons().add(new Image(AppHelp.class.getResourceAsStream(builder.iconPath)));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        setX(screenBounds.getMinX() + screenBounds.getWidth() - builder.width);
        setY(screenBounds.getMinY() + screenBounds.getHeight() - builder.height);

        WebView helpBrowser = new WebView();
        helpBrowser.setContextMenuEnabled(false);

        Scene helpScene = new Scene(helpBrowser, builder.width, builder.height);
        helpScene.getStylesheets().add(builder.stylesheet);
        setScene(helpScene);
        setAccelerators();

        URL url = getClass().getResource(builder.helpHtmlPath);
        helpBrowser.getEngine().load(url.toExternalForm());
    }

    private void setAccelerators() {
        getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), this::close);
    }

    public static class Builder {

        private String title;
        private String iconPath;
        private String helpHtmlPath;
        private double width = 500;
        private double height = 600;
        private String stylesheet;

        public AppHelp.Builder title(String title) {
            this.title = title;
            return this;
        }

        public AppHelp.Builder iconPath(String iconPath) {
            this.iconPath = iconPath;
            return this;
        }

        public AppHelp.Builder helpHtmlPath(String helpHtmlPath) {
            this.helpHtmlPath = helpHtmlPath;
            return this;
        }

        public AppHelp.Builder width(double width) {
            this.width = width;
            return this;
        }

        public AppHelp.Builder height(double height) {
            this.height = height;
            return this;
        }

        public AppHelp.Builder stylesheet(String stylesheet) {
            this.stylesheet = stylesheet;
            return this;
        }

        public AppHelp build() {
            return new AppHelp(this);
        }
    }

}
