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

package com.cemikta.customersfx.controller;

import com.cemikta.customersfx.mvc.AbstractDataPageController;
import com.cemikta.customersfx.mvc.DataPageView;
import com.cemikta.customersfx.model.Category;
import com.cemikta.customersfx.service.AbstractService;
import com.cemikta.customersfx.service.CategoryService;
import com.cemikta.customersfx.view.CategoryForm;
import com.cemikta.customersfx.view.CategoryPage;

/**
 * Category controller
 *
 * @author Cem Ikta
 */
public class CategoryController extends AbstractDataPageController<Category> {

    @Override
    protected AbstractService<Category> createService() {
        return new CategoryService();
    }

    @Override
    protected DataPageView<Category> createDataPageView() {
        return new CategoryPage();
    }

    @Override
    public void openFormView(Category category) {
        new CategoryForm(this, category).showDialog();
    }

    @Override
    public void onAddNew() {
        openFormView(new Category());
    }

    @Override
    public String getNamedQuery() {
        return Category.FIND_ALL;
    }

    @Override
    public String getNamedQueryWithFilter() {
        return Category.FIND_BY_FILTER;
    }

    @Override
    public String getName() {
        return "CategoryController";
    }

}