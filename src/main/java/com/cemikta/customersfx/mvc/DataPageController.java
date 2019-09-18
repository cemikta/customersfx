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

import com.cemikta.customersfx.model.BaseEntity;
import com.cemikta.customersfx.service.AbstractService;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * Data page controller
 *
 * @param <T> entity
 *
 * @author Cem Ikta
 */
public interface DataPageController<T extends BaseEntity> extends Controller {

    /**
     * Gets service of this controller
     *
     * @return service of the controller
     */
    AbstractService<T> getService();

    /**
     * Gets data page view of the controller
     *
     * @return data page view
     */
    DataPageView<T> getDataPageView();

    /**
     * Gets data list for {@link TableView}
     *
     * @param filter filter for data list
     * @param start start for paging
     * @param end end for paging
     * @return data list
     */
    List<T> getData(String filter, int start, int end);

    /**
     * Gets data size
     *
     * @param filter The filter for data list
     * @return data record size
     */
    int getDataSize(String filter);

    /**
     * Gets named query
     *
     * @return named query in entity
     */
    String getNamedQuery();

    /**
     * Gets named query with filter
     *
     * @return named query with filter in entity
     */
    String getNamedQueryWithFilter();

    /**
     * Open form view
     *
     * @param entity The entity object for form to edit.
     */
    void openFormView(T entity);

    /**
     * Add new action of the controller
     */
    void onAddNew();

    /**
     * Edit action of the controller
     */
    void onEdit();

    /**
     * Delete action of the controller
     */
    void onDelete();

    /**
     * Refresh action of the controller
     */
    void onRefresh();

    /**
     * Print preview action of the controller
     */
    void onPrintPreview();

    /**
     * Print action of the controller
     */
    void onPrint();

    /**
     * Pdf export action of the controller
     */
    void onPdf();

    /**
     * Form print preview action of the controller
     *
     * @param id The id parameter for query
     */
    void onFormPrintPreview(Long id);

    /**
     * Form print action of the controller
     *
     * @param id The id parameter for query
     */
    void onFormPrint(Long id);

    /**
     * Form pdf action of the controller
     *
     * @param id The id parameter for query
     */
    void onFormPdf(Long id);

    /**
     * Save action of the controller
     *
     * @param entity to save
     */
    void onSave(T entity);

}