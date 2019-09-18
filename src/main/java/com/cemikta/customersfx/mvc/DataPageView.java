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

/**
 * Data page view
 *
 * @param <T> entity of the page
 */
public interface DataPageView<T extends BaseEntity> extends View {

    /**
     * Data page view init
     *
     * @param controller The controller of the data page view.
     */
    void init(DataPageController<T> controller);

    /**
     * Gets data page controller
     *
     * @return controller of the data page.
     */
    DataPageController<T> getController();

    /**
     * Gets selected data model of data page view
     *
     * @return selected entity model
     */
    T getSelectedModel();

    /**
     * Refresh data of data page view
     */
    void refreshData();

    /**
     * Sets keyboard shortcuts of data page.
     *
     * <p>This method must be called when the DataPageView is attached to a scene.
     */
    void setDataPageAccelerators();

}
