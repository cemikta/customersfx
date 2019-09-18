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
import com.cemikta.customersfx.model.Category;
import com.cemikta.customersfx.util.I18n;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * Category page view
 *
 * @author Cem Ikta
 */
public class CategoryPage extends AbstractDataPageView<Category> {

    @Override
    public List<TableColumn<Category, ?>> getTableViewColumns() {
        List<TableColumn<Category, ?>> columns = new ArrayList<>();
        columns.add(TableColumnBuilder.<Category, Long> create()
                .fieldName("id")
                .title(I18n.CATEGORY.getString("table.id"))
                .prefWidth(100)
                .build());

        columns.add(TableColumnBuilder.<Category, String> create()
                .fieldName("name")
                .title(I18n.CATEGORY.getString("table.name"))
                .prefWidth(500)
                .build());

        columns.add(TableColumnBuilder.<Category, String> create()
                .fieldName("notes")
                .title(I18n.CATEGORY.getString("table.notes"))
                .prefWidth(500)
                .build());

        return columns;
    }

    @Override
    public FontIcon getFontIcon() {
        return AppFontIcons.CATEGORY;
    }

    @Override
    public String getTitle() {
        return I18n.CATEGORY.getString("title");
    }
}
