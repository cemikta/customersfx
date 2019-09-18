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

import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.control.TableColumnBuilder;
import com.cemikta.customersfx.mvc.AbstractDataPageView;
import com.cemikta.customersfx.model.User;
import com.cemikta.customersfx.util.I18n;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.util.ArrayList;
import java.util.List;

/**
 * User page view
 *
 * @author Cem Ikta
 */
public class UserPage extends AbstractDataPageView<User> {

    @Override
    public List<TableColumn<User, ?>> getTableViewColumns() {
        List<TableColumn<User, ?>> columns = new ArrayList<>();
        // checkbox cell
        TableColumn<User, Boolean> activeCol = TableColumnBuilder.<User, Boolean> create()
                .fieldName("active")
                .title(I18n.USER.getString("table.active"))
                .prefWidth(100)
                .build();
        setActiveColumnCheckBox(activeCol);
        columns.add(activeCol);

        columns.add(TableColumnBuilder.<User, Long> create()
                .fieldName("id")
                .title(I18n.USER.getString("table.id"))
                .prefWidth(100)
                .build());

        columns.add(TableColumnBuilder.<User, String> create()
                .fieldName("username")
                .title(I18n.USER.getString("table.username"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<User, String> create()
                .fieldName("name")
                .title(I18n.USER.getString("table.name"))
                .prefWidth(400)
                .build());

        columns.add(TableColumnBuilder.<User, String> create()
                .fieldName("userRole")
                .title(I18n.USER.getString("table.userRole"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<User, String> create()
                .fieldName("notes")
                .title(I18n.USER.getString("table.notes"))
                .prefWidth(300)
                .build());

        return columns;
    }

    private void setActiveColumnCheckBox(TableColumn<User, Boolean> activeCol) {
        activeCol.setCellValueFactory(param -> {
            User user = param.getValue();
            SimpleBooleanProperty activeProperty = new SimpleBooleanProperty(user.isActive());
            activeProperty.addListener((observable, oldValue, newValue) -> user.setActive(newValue));
            return activeProperty;
        });

        activeCol.setCellFactory(param -> {
            CheckBoxTableCell<User, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }


    @Override
    public FontIcon getFontIcon() {
        return AppFontIcons.USER;
    }

    @Override
    public String getTitle() {
        return I18n.USER.getString("title");
    }
}
