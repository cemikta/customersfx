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
import com.cemikta.customersfx.util.CssHelpers;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

/**
 * Button bar pane control
 *
 * <p>Code snippet for button bar pane:
 *
 * <pre>{@code ButtonBarPane bbpPages = new ButtonBarPane();
 * bbpPages.addPage(fontIcon, "General"), generalPagePane);
 * bbpPages.addPage(fontIcon, "Reporting"), reportingPagePane);}</pre>
 *
 * @see BorderPane
 * @see ToolBar
 *
 * @author Cem Ikta
 */
public class ButtonBarPane extends BorderPane {

    private static final String DEFAULT_STYLE_CLASS = "button-bar-pane";
    private ToolBar buttonBar;
    private Node currentPagePane;

    public ButtonBarPane() {
        buildView();
    }

    private void buildView() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
        buttonBar = new ToolBar();
        setTop(buttonBar);
    }

    /**
     * Adds new page to button bar pane.
     *
     * @param fontIcon The page font icon
     * @param title The page title
     * @param pagePane The page content node
     */
    public void addPage(FontIcon fontIcon, String title, Node pagePane) {
        Button btn = ViewHelpers.createFontIconButton(title, fontIcon, ContentDisplay.TOP);
        btn.setOnAction((ActionEvent event) -> {
            setSelectedButton(btn);
            showPage(pagePane);
        });
        buttonBar.getItems().add(btn);

        if (buttonBar.getItems().size() == 1) {
            setSelectedButton(btn);
            showPage(pagePane);
        }
    }

    private void setSelectedButton(Button btnSelected) {
        buttonBar.getItems().forEach((node) -> {
            node.getStyleClass().removeAll("selected");
        });
        btnSelected.getStyleClass().add("selected");
    }

    private void showPage(Node pagePane) {
        if (currentPagePane != null) {
            setCenter(null);
        }
        this.currentPagePane = pagePane;
        setCenter(pagePane);
    }

}
