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
import com.cemikta.customersfx.model.Country;
import com.cemikta.customersfx.util.I18n;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * Country page view
 *
 * @author Cem Ikta
 */
public class CountryPage extends AbstractDataPageView<Country> {

    @Override
    public List<TableColumn<Country, ?>> getTableViewColumns() {
        List<TableColumn<Country, ?>> columns = new ArrayList<>();
        columns.add(TableColumnBuilder.<Country, Long> create()
                .fieldName("id")
                .title(I18n.COUNTRY.getString("table.id"))
                .prefWidth(100)
                .build());

        columns.add(TableColumnBuilder.<Country, String> create()
                .fieldName("alpha2Code")
                .title(I18n.COUNTRY.getString("table.alpha2Code"))
                .prefWidth(150)
                .build());

        columns.add(TableColumnBuilder.<Country, String> create()
                .fieldName("alpha3Code")
                .title(I18n.COUNTRY.getString("table.alpha3Code"))
                .prefWidth(150)
                .build());

        columns.add(TableColumnBuilder.<Country, Short> create()
                .fieldName("numericCode")
                .title(I18n.COUNTRY.getString("table.numericCode"))
                .prefWidth(150)
                .build());

        columns.add(TableColumnBuilder.<Country, String> create()
                .fieldName("name")
                .title(I18n.COUNTRY.getString("table.name"))
                .prefWidth(400)
                .build());

        columns.add(TableColumnBuilder.<Country, String> create()
                .fieldName("notes")
                .title(I18n.COUNTRY.getString("table.notes"))
                .prefWidth(400)
                .build());

        return columns;
    }

    @Override
    public FontIcon getFontIcon() {
        return AppFontIcons.COUNTRY;
    }

    @Override
    public String getTitle() {
        return I18n.COUNTRY.getString("title");
    }
}
