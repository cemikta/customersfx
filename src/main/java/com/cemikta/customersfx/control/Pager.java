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

import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.util.I18n;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Pager control
 *
 * @see PagerRefreshCallback
 *
 * @author Cem Ikta
 */
public class Pager extends HBox {

    private static final int DEFAULT_ITEMS_PER_PAGE = 20;
    private Label lblPages;
    private Label lblRecordsFound;
    private int rowCount = 0;
    private int pageCount = 0;
    private int itemsPerPage;
    private int currentPageIndex = 1;
    private PagerRefreshCallback pagerRefreshCallback;

    public Pager(PagerRefreshCallback pagerRefreshCallback) {
        this(DEFAULT_ITEMS_PER_PAGE, pagerRefreshCallback);
    }
    
    /**
     * Pager with items per page.
     *
     * @param itemsPerPage The items per page for pager
     * @param pagerRefreshCallback The callback method for refresh action
     */
    public Pager(int itemsPerPage, PagerRefreshCallback pagerRefreshCallback) {
        this.itemsPerPage = itemsPerPage;
        this.pagerRefreshCallback = pagerRefreshCallback;
        buildView();
    }

    private void buildView() {
        Button btnFirst = new Button("", FontIconFactory.createIcon(AppFontIcons.FIRST_PAGE));
        btnFirst.setTooltip(new Tooltip(I18n.CONTROL.getString("pager.firstPage")));
        btnFirst.getStyleClass().addAll("left-pill", "pager-button");
        btnFirst.setOnAction(event -> onFirstPage());

        Button btnPrevious = new Button("", FontIconFactory.createIcon(AppFontIcons.PREVIOUS_PAGE));
        btnPrevious.setTooltip(new Tooltip(I18n.CONTROL.getString("pager.previousPage")));
        btnPrevious.getStyleClass().addAll("center-pill", "pager-button");
        btnPrevious.setOnAction(event -> onPreviousPage());

        lblPages = new Label();
        lblPages.getStyleClass().addAll("button", "center-pill", "pager-button");

        Button btnNext = new Button("", FontIconFactory.createIcon(AppFontIcons.NEXT_PAGE));
        btnNext.setTooltip(new Tooltip(I18n.CONTROL.getString("pager.nextPage")));
        btnNext.getStyleClass().addAll("center-pill", "pager-button");
        btnNext.setOnAction(event -> onNextPage());

        Button btnLast = new Button("", FontIconFactory.createIcon(AppFontIcons.LAST_PAGE));
        btnLast.setTooltip(new Tooltip(I18n.CONTROL.getString("pager.lastPage")));
        btnLast.getStyleClass().addAll("right-pill", "pager-button");
        btnLast.setOnAction(event -> onLastPage());

        Button btnRefresh = new Button("", FontIconFactory.createIcon(AppFontIcons.REFRESH));
        btnRefresh.setTooltip(new Tooltip(I18n.CONTROL.getString("pager.refresh")));
        btnRefresh.getStyleClass().add("pager-button");
        btnRefresh.setOnAction(event -> callRefreshCallback());
        HBox.setMargin(btnRefresh, new Insets(0, 10, 0, 10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        lblRecordsFound = new Label();
        HBox.setMargin(lblRecordsFound, new Insets(5, 0, 0, 0));

        getChildren().addAll(btnFirst, btnPrevious, lblPages, btnNext, btnLast, btnRefresh, spacer, lblRecordsFound);
    }

    /**
     * Pager first page action.
     */
    private void onFirstPage() {
        if (getCurrentPageIndex() > 1) {
            setCurrentPageIndex(1);
        }
    }

    /**
     * Pager previous page action.
     */
    private void onPreviousPage() {
        if (getCurrentPageIndex() > 1) {
            setCurrentPageIndex(getCurrentPageIndex() - 1);
        }
    }

    /**
     * Pager next page action.
     */
    private void onNextPage() {
        if (getCurrentPageIndex() < getPageCount()) {
            setCurrentPageIndex(getCurrentPageIndex() + 1);
        }
    }

    /**
     * Pager last page action.
     */
    private void onLastPage() {
        if (getCurrentPageIndex() < getPageCount()) {
            setCurrentPageIndex(getPageCount());
        }
    }
    
    /**
     * Gets current page index of pager
     * 
     * @return The current page index
     */
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    /**
     * Sets current page index of pager.
     * 
     * @param currentPageIndex The index value to set
     */
    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
        callRefreshCallback();
    }
    
    private void callRefreshCallback() {
        if (pagerRefreshCallback != null) {
            pagerRefreshCallback.onRefresh();
        }
    }

    /**
     * Gets row count for pager.
     *
     * @return The row count of pager.
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Sets row count for pager.
     *
     * @param rowCount The row count of pager.
     */
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        
        pageCount = (int) Math.ceil((double) (rowCount / itemsPerPage));
        double remainder = (rowCount % itemsPerPage);

        if (remainder > 0) {
            pageCount += 1;
        }

        setPagerValues();
    }

    /**
     * Gets page count for pager.
     *
     * @return The page count of pager.
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Sets pager label values. 
     */
    private void setPagerValues() {
        lblPages.setText(I18n.CONTROL.getString("pager.page", getCurrentPageIndex(), getPageCount()));

        if (getRowCount() > 1) {
            lblRecordsFound.setText(I18n.CONTROL.getString("pager.recordsFound", getRowCount()));
        } else {
            lblRecordsFound.setText(I18n.CONTROL.getString("pager.recordFound", getRowCount()));
        }
    }
    
    /**
     * Gets refresh callback of pager control
     * 
     * @return The pager refresh callback
     */
    public PagerRefreshCallback getRefreshCallback() {
        return pagerRefreshCallback;
    }

    /**
     * Sets refresh callback of pager control
     * 
     * @param pagerRefreshCallback The pager refresh callback
     */
    public void setRefreshCallback(PagerRefreshCallback pagerRefreshCallback) {
        this.pagerRefreshCallback = pagerRefreshCallback;
    }
    
    /**
     * Gets start position of pager for service query.
     * 
     * @return The pager start position
     */
    public int getStartPosition() {
        return ((itemsPerPage * getCurrentPageIndex()) - itemsPerPage);
    }

    /**
     * Gets end position of pager for service query.
     * 
     * @return The pager end position
     */
    public int getEndPosition() {
	    return getStartPosition() + itemsPerPage;
    }

}
