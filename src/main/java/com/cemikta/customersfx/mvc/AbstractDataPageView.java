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
import com.cemikta.customersfx.control.Pager;
import com.cemikta.customersfx.control.SearchField;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.model.BaseEntity;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Abstract data page view
 *
 * @param <T> entity
 *
 * @author Cem Ikta
 */
public abstract class AbstractDataPageView<T extends BaseEntity> implements DataPageView<T> {

    private final Logger logger = LoggerFactory.getLogger(AbstractDataPageView.class);
    protected DataPageController<T> controller;
    private BorderPane dataPageView;
    private AbstractPreviewView previewView;
    private SearchField searchField;
    private ProgressIndicator progressIndicator;
    private TableView<T> tableView;
    private Pager pager;

    // page actions
    private Button btnAddNew;
    private Button btnEdit;
    private Button btnDelete;
    private Button btnPrintPreview;
    private Button btnPrint;
    private Button btnPdf;

    /**
     * init process
     *
     * @param controller The data page controller
     */
    @Override
    public void init(DataPageController<T> controller) {
        logger.debug("init data page");
        this.controller = controller;
        buildView();
    }

    @Override
    public DataPageController<T> getController() {
        return controller;
    }

    private void buildView() {
        logger.debug("build data page view");
        dataPageView = new BorderPane();
        dataPageView.getStyleClass().addAll("data-page-view");
        dataPageView.setTop(getHeaderBar());
        dataPageView.setCenter(getTablePane());
        previewView = getPreviewView();
        if (previewView != null) {
            dataPageView.setBottom(previewView.asNode());
        }
    }

    private HBox getHeaderBar() {
        logger.debug("get data page header");
        HBox headerBar = new HBox();
        headerBar.getStyleClass().add("data-page-header");

        Label title = ViewHelpers.createFontIconLabel(getTitle(), getFontIcon(), "");
        title.getStyleClass().add("page-title");

        progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(50, 50);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // actions
        btnAddNew = new Button("", FontIconFactory.createIcon(AppFontIcons.ADD_NEW));
        btnAddNew.setTooltip(new Tooltip(I18n.COMMON.getString("action.addNew")));
        btnAddNew.getStyleClass().add("left-pill");
        btnAddNew.setOnAction(event -> getController().onAddNew());

        btnEdit = new Button("", FontIconFactory.createIcon(AppFontIcons.EDIT));
        btnEdit.setTooltip(new Tooltip(I18n.COMMON.getString("action.edit")));
        btnEdit.getStyleClass().add("right-pill");
        btnEdit.setOnAction(event -> getController().onEdit());
        HBox.setMargin(btnEdit, new Insets(0, 10, 0, 0));

        btnDelete = new Button("", FontIconFactory.createIcon(AppFontIcons.DELETE));
        btnDelete.setTooltip(new Tooltip(I18n.COMMON.getString("action.delete")));
        btnDelete.setOnAction(event -> getController().onDelete());
        HBox.setMargin(btnDelete, new Insets(0, 10, 0, 0));

        headerBar.getChildren().addAll(title, progressIndicator, spacer, btnAddNew, btnEdit, btnDelete);

        if (hasPrintActions()) {
            btnPrintPreview = new Button("", FontIconFactory.createIcon(AppFontIcons.PRINT_PREVIEW));
            btnPrintPreview.setTooltip(new Tooltip(I18n.COMMON.getString("action.printPreview")));
            btnPrintPreview.getStyleClass().add("left-pill");
            btnPrintPreview.setOnAction(event -> getController().onPrintPreview());

            btnPrint = new Button("", FontIconFactory.createIcon(AppFontIcons.PRINT));
            btnPrint.setTooltip(new Tooltip(I18n.COMMON.getString("action.print")));
            btnPrint.getStyleClass().add("center-pill");
            btnPrint.setOnAction(event -> getController().onPrint());

            btnPdf = new Button("", FontIconFactory.createIcon(AppFontIcons.PDF));
            btnPdf.setTooltip(new Tooltip(I18n.COMMON.getString("action.pdf")));
            btnPdf.getStyleClass().add("right-pill");
            btnPdf.setOnAction(event -> getController().onPdf());
            HBox.setMargin(btnPdf, new Insets(0, 10, 0, 0));

            headerBar.getChildren().addAll(btnPrintPreview, btnPrint, btnPdf);
        }

        // search field
        searchField = new SearchField(300);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            pager.setCurrentPageIndex(1);
            refreshData();
        });
        HBox.setMargin(searchField, new Insets(0, 0, 0, 100));
        headerBar.getChildren().add(searchField);
        return headerBar;
    }

    public Boolean hasPrintActions() {
        return false;
    }

    private VBox getTablePane() {
        logger.debug("get table pane");
        tableView = new TableView<>();
        tableView.setPlaceholder(new Label(I18n.COMMON.getString("tableView.dataNotFound")));
        tableView.setEditable(false);
        tableView.setTableMenuButtonVisible(true);
        tableView.getColumns().addAll(getTableViewColumns());
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                getController().onEdit();
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            onSelectedItemChanged();
        });
        pager = new Pager(this::refreshData);

        VBox tablePane = new VBox();
        tablePane.getStyleClass().add("data-page-center");
        tablePane.getChildren().addAll(tableView, pager);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        VBox.setVgrow(pager, Priority.NEVER);
        VBox.setMargin(pager, new Insets(10, 10, 10, 10));
        return tablePane;
    }

    public void setDataPageAccelerators() {
        logger.debug("set data page view accelerators");
        Scene scene = dataPageView.getScene();
        if (scene == null) {
            throw new IllegalArgumentException("setDataPageAccelerators must be called when the DataPageView is " +
                    "attached to a scene");
        }
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN), btnAddNew::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN), btnEdit::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN), btnDelete::fire);
        if (hasPrintActions()) {
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN),
                    btnPrintPreview::fire);

            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN),
                    btnPrint::fire);

            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN), btnPdf::fire);
        }
    }

    public abstract List<TableColumn<T, ?>> getTableViewColumns();

    private void onSelectedItemChanged() {
        if (previewView != null) {
            notifyPreviewView();
        }
    }

    /**
     * If the data page view has preview, this method should be overridden.
     * Data page view has no default preview.
     *
     * @return preview view if exist, otherwise null.
     */
    public AbstractPreviewView getPreviewView() {
        return null;
    }

    /**
     * If the selected model of the {@link TableView} changed, notifies the preview view and
     * preview view shows new data values.
     */
    private void notifyPreviewView() {
        logger.debug("notify preview view");
        if (getSelectedModel() != null) {
            previewView.setEntity(getSelectedModel());
        }
    }

    /**
     * Refreshs the data of the {@link TableView} and {@link Pager}
     */
    @Override
    public void refreshData() {
        logger.debug("refresh data");
        Task<List<T>> task = new Task<List<T>>() {
            @Override
            protected List<T> call() throws Exception {
                return getController().getData(searchField.getText(), pager.getStartPosition(), pager.getEndPosition());
            }
        };

        task.stateProperty().addListener((source, oldState, newState) -> {
            if (newState.equals(Worker.State.SUCCEEDED)) {
                tableView.getItems().clear();
                if (task.getValue() != null) {
                    tableView.getItems().addAll(task.getValue());
                    tableView.getSelectionModel().selectFirst();
                    pager.setRowCount(getController().getDataSize(searchField.getText()));
                }
            }
        });

        progressIndicator.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
    }

    @Override
    public T getSelectedModel() {
        logger.debug("get tableview selected model");
        return tableView.getSelectionModel().getSelectedItem();
    }

    @Override
    public Node asNode() {
        return dataPageView;
    }

}
