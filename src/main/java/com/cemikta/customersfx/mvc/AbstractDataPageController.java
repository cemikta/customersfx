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

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.control.MessageBox;
import com.cemikta.customersfx.model.BaseEntity;
import com.cemikta.customersfx.service.AbstractService;
import com.cemikta.customersfx.util.I18n;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static com.cemikta.customersfx.service.QueryParameter.with;
import static javafx.scene.control.ButtonBar.*;

/**
 * Abstract data page controller.
 *
 * @param <T> entity
 *
 * @author Cem Ikta
 */
public abstract class AbstractDataPageController<T extends BaseEntity> implements DataPageController<T> {

    private final Logger logger = LoggerFactory.getLogger(AbstractDataPageController.class);
    private AbstractService<T> service;
    private DataPageView<T> dataPageView;

    public AbstractDataPageController() {
    }

    @Override
    public AbstractService<T> getService() {
        logger.debug("get service");
        if (service == null) {
            service = createService();
        }
        return service;
    }

    protected abstract AbstractService<T> createService();

    @Override
    public DataPageView<T> getDataPageView() {
        logger.debug("get data page view");
        if (dataPageView == null) {
            dataPageView = createDataPageView();
            dataPageView.init(this);
            dataPageView.refreshData();
        } else {
            dataPageView.refreshData();
        }
        return dataPageView;
    }

    protected abstract DataPageView<T> createDataPageView();

    @Override
    public void onEdit() {
        logger.debug("on edit action");
        if (dataPageView == null) {
            return;
        }
        if (dataPageView.getSelectedModel() == null) {
            return;
        }
        openFormView(dataPageView.getSelectedModel());
    }

    @Override
    public void onDelete() {
        logger.debug("on delete action");
        if (dataPageView == null) {
            return;
        }
        if (dataPageView.getSelectedModel() == null) {
            return;
        }

        Optional<ButtonType> result = MessageBox.create()
                .owner(CustomersFxApp.get().getMainStage())
                .contentText(I18n.COMMON.getString("confirm.delete"))
                .showDeleteConfirmation();

        if (result.isPresent() && result.get().getButtonData() == ButtonData.OK_DONE) {
            try {
                getService().remove(dataPageView.getSelectedModel());
                onRefresh();
                if (CustomersFxApp.get().getPreferences().isShowInfoPopups()) {
                    Notifications.create()
                            .text(I18n.COMMON.getString("notification.deleted"))
                            .position(Pos.TOP_RIGHT).showInformation();
                }
            } catch (Exception e) {
                MessageBox.create()
                        .owner(CustomersFxApp.get().getMainStage())
                        .contentText(I18n.COMMON.getString("exception.delete"))
                        .showError(e);
            }
        }
    }

    @Override
    public void onRefresh() {
        logger.debug("on refresh action");
        if (dataPageView != null) {
            dataPageView.refreshData();
        }
    }

    @Override
    public void onSave(T entity) {
        logger.debug("on save action {}", entity);
        try {
            if (entity.isNew()) {
                entity.setCreatedBy(CustomersFxApp.get().getLoggedInUser().getId());
                getService().create(entity);
            } else {
                entity.setUpdatedBy(CustomersFxApp.get().getLoggedInUser().getId());
                getService().update(entity);
            }
            onRefresh();
            if (CustomersFxApp.get().getPreferences().isShowInfoPopups()) {
                Notifications.create()
                        .text(I18n.COMMON.getString("notification.saved"))
                        .position(Pos.TOP_RIGHT).showInformation();
            }
        } catch (Exception e) {
            logger.error("save is not successful", e);
            MessageBox.create()
                    .owner(CustomersFxApp.get().getMainStage())
                    .contentText(I18n.COMMON.getString("exception.save"))
                    .showError(e);
        }
    }

    @Override
    public List<T> getData(String filter, int start, int end) {
        logger.debug("get data with filter {}, start {} and end {}", filter, start, end);
        if (StringUtils.isEmpty(filter)) {
            return getService().getListWithNamedQuery(getNamedQuery(), start, end);
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("search", "%" + filter + "%").parameters(), start, end);
        }
    }

    @Override
    public int getDataSize(String filter) {
        logger.debug("get data size with filter {}", filter);
        if (StringUtils.isEmpty(filter)) {
            return getService().getListWithNamedQuery(getNamedQuery()).size();
        } else {
            return getService().getListWithNamedQuery(getNamedQueryWithFilter(),
                    with("search", "%" + filter + "%").parameters()).size();
        }
    }

    @Override
    public void onPrintPreview() {
    }

    @Override
    public void onPrint(){
    }

    @Override
    public void onPdf(){
    }

    @Override
    public void onFormPrintPreview(Long id) {
    }

    @Override
    public void onFormPrint(Long id){
    }

    @Override
    public void onFormPdf(Long id){
    }

}
