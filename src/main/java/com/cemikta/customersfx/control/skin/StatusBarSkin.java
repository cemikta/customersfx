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

package com.cemikta.customersfx.control.skin;

import com.cemikta.customersfx.control.StatusBar;
import javafx.beans.Observable;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Status bar control skin
 *
 * @author Cem Ikta
 */
public class StatusBarSkin extends SkinBase<StatusBar> {

    private HBox leftBox;

    private HBox rightBox;

    public StatusBarSkin(StatusBar control) {
        super(control);

        leftBox = new HBox();
        leftBox.getStyleClass().add("left-items");
        leftBox.getChildren().setAll(getSkinnable().getLeftItems());
        control.getLeftItems().addListener(
                (Observable evt) -> leftBox.getChildren().setAll(
                        getSkinnable().getLeftItems()));

        rightBox = new HBox();
        rightBox.getStyleClass().add("right-items");
        rightBox.getChildren().setAll(getSkinnable().getRightItems());
        control.getRightItems().addListener(
                (Observable evt) -> rightBox.getChildren().setAll(
                        getSkinnable().getRightItems()));

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox container = new HBox();
        container.getChildren().addAll(leftBox, spacer, rightBox);

        getChildren().add(container);
    }

}
