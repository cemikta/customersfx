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

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Table column builder.
 *
 * <p>Code snippet for table columns:
 * <pre>{@code List<TableColumn<User, ?>> columns = new ArrayList<>();
 * columns.add(TableColumnBuilder.<User, Long> create()
 *      .fieldName("id")
 *      .title("ID")
 *      .prefWidth(100)
 *      .build());
 * columns.add(TableColumnBuilder.<User, String> create()
 *      .fieldName("username")
 *      .title("Username")
 *      .prefWidth(200)
 *      .build());}</pre>
 *
 * @see TableColumn
 * @see TableView
 *
 * @author Cem Ikta
 */
public class TableColumnBuilder<S, T> {

    private String fieldName;
    private String title;
    private double prefWidth;
    private boolean visible = true;
    private boolean sortable = true;
    private boolean resizable = true;

    private TableColumnBuilder() {
    }

    public static <S, T> TableColumnBuilder<S, T> create() {
        return new TableColumnBuilder<>();
    }

    public TableColumnBuilder<S, T> fieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public TableColumnBuilder<S, T> title(String title) {
        this.title = title;
        return this;
    }

    public TableColumnBuilder<S, T> prefWidth(double prefWidth) {
        this.prefWidth = prefWidth;
        return this;
    }

    public TableColumnBuilder<S, T> visible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public TableColumnBuilder<S, T> sortable(boolean sortable) {
        this.sortable = sortable;
        return this;
    }

    public TableColumnBuilder<S, T> resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public TableColumn<S, T> build() {
        TableColumn<S, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(fieldName));
        column.setPrefWidth(prefWidth);
        column.setSortable(sortable);
        column.setResizable(resizable);
        column.setVisible(visible);
        return column;
    }

}
