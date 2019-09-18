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

package com.cemikta.customersfx.mvc;

import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.model.BaseEntity;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * Abstract preview view
 *
 * @param <T> entity
 *
 * @author Cem Ikta
 */
public abstract class AbstractPreviewView<T extends BaseEntity> implements View {

    private T entity;
    private BorderPane previewView;

    /**
     * Creates preview view
     */
    public AbstractPreviewView() {
        buildView();
    }

    private void buildView() {
        previewView = new BorderPane();
        previewView.getStyleClass().addAll("preview-view");
        previewView.setPrefHeight(220);
        previewView.setTop(getHeaderBar());
        previewView.setCenter(getCenterView());
        previewView.setBottom(null);
    }

    private HBox getHeaderBar() {
        Label title = ViewHelpers.createFontIconLabel(getTitle(), getFontIcon(), "");
        title.getStyleClass().add("preview-title");

        HBox headerBar = new HBox();
        headerBar.getStyleClass().add("preview-header");
        headerBar.getChildren().add(title);
        return headerBar;
    }

    private ScrollPane getCenterView() {
        ScrollPane sp = new ScrollPane();
        sp.getStyleClass().add("preview-center");
        sp.setContent(buildCenterView());
        sp.setPrefHeight(210);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return sp;
    }

    /**
     * Builds center view of the preview view.
     *
     * @return center view
     */
    public abstract Node buildCenterView();

    /**
     * Gets entity data model of preview view.
     *
     * @return entity data model
     */
    public T getEntity() {
        return entity;
    }

    /**
     * Sets entity data model of preview view.
     *
     * @param entity data model
     */
    public void setEntity(T entity) {
        this.entity = entity;
        pop();
    }

    /**
     * Pop data model values to preview view
     */
    public abstract void pop();

    @Override
    public FontIcon getFontIcon() {
        return AppFontIcons.DATA_PAGE_PREVIEW;
    }

    @Override
    public Node asNode() {
        return previewView;
    }

}
