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
import com.cemikta.customersfx.model.Country;
import com.cemikta.customersfx.service.AbstractService;
import com.cemikta.customersfx.service.CountryService;
import com.cemikta.customersfx.view.CountryForm;
import com.cemikta.customersfx.view.CountryPage;

/**
 * Country controller
 *
 * @author Cem Ikta
 */
public class CountryController extends AbstractDataPageController<Country> {

    @Override
    protected AbstractService<Country> createService() {
        return new CountryService();
    }

    @Override
    protected DataPageView<Country> createDataPageView() {
        return new CountryPage();
    }

    @Override
    public void openFormView(Country country) {
        new CountryForm(this, country).showDialog();
    }

    @Override
    public void onAddNew() {
        openFormView(new Country());
    }

    @Override
    public String getNamedQuery() {
        return Country.FIND_ALL;
    }

    @Override
    public String getNamedQueryWithFilter() {
        return Country.FIND_BY_FILTER;
    }

    @Override
    public String getName() {
        return "CountryController";
    }

}