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
import com.cemikta.customersfx.control.TableColumnBuilder;
import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.mvc.AbstractDataPageView;
import com.cemikta.customersfx.mvc.AbstractPreviewView;
import com.cemikta.customersfx.model.Customer;
import com.cemikta.customersfx.util.I18n;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer page view
 *
 * @author Cem Ikta
 */
public class CustomerPage extends AbstractDataPageView<Customer> {

    @Override
    public List<TableColumn<Customer, ?>> getTableViewColumns() {
        List<TableColumn<Customer, ?>> columns = new ArrayList<>();
        // checkbox cell
        TableColumn<Customer, Boolean> activeCol = TableColumnBuilder.<Customer, Boolean> create()
                .fieldName("active")
                .title(I18n.CUSTOMER.getString("table.active"))
                .prefWidth(100)
                .build();
        setActiveColumnCheckBox(activeCol);
        columns.add(activeCol);

        columns.add(TableColumnBuilder.<Customer, Long> create()
                .fieldName("id")
                .title(I18n.CUSTOMER.getString("table.id"))
                .prefWidth(100)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("companyName")
                .title(I18n.CUSTOMER.getString("table.companyName"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("contactTitle")
                .title(I18n.CUSTOMER.getString("table.contactTitle"))
                .prefWidth(100)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("contactFirstName")
                .title(I18n.CUSTOMER.getString("table.contactFirstName"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("contactLastName")
                .title(I18n.CUSTOMER.getString("table.contactLastName"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("category")
                .title(I18n.CUSTOMER.getString("table.category"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("city")
                .title(I18n.CUSTOMER.getString("table.city"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("country")
                .title(I18n.CUSTOMER.getString("table.country"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("phone")
                .title(I18n.CUSTOMER.getString("table.phone"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("mobile")
                .title(I18n.CUSTOMER.getString("table.mobile"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("fax")
                .title(I18n.CUSTOMER.getString("table.fax"))
                .prefWidth(200)
                .visible(false)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("email")
                .title(I18n.CUSTOMER.getString("table.email"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("homepage")
                .title(I18n.CUSTOMER.getString("table.homepage"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("skype")
                .title(I18n.CUSTOMER.getString("table.skype"))
                .prefWidth(200)
                .build());

        columns.add(TableColumnBuilder.<Customer, String> create()
                .fieldName("notes")
                .title(I18n.CUSTOMER.getString("table.notes"))
                .prefWidth(300)
                .visible(false)
                .build());

        return columns;
    }

    private void setActiveColumnCheckBox(TableColumn<Customer, Boolean> activeCol) {
        activeCol.setCellValueFactory(param -> {
            Customer customer = param.getValue();
            SimpleBooleanProperty activeProperty = new SimpleBooleanProperty(customer.isActive());
            activeProperty.addListener((observable, oldValue, newValue) -> customer.setActive(newValue));
            return activeProperty;
        });

        activeCol.setCellFactory(param -> {
            CheckBoxTableCell<Customer, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
    }

    @Override
    public AbstractPreviewView getPreviewView() {
        return new CustomerPreview();
    }

    @Override
    public FontIcon getFontIcon() {
        return AppFontIcons.CUSTOMER;
    }

    @Override
    public String getTitle() {
        return I18n.CUSTOMER.getString("title");
    }

    @Override
    public Boolean hasPrintActions() {
        return true;
    }

}
