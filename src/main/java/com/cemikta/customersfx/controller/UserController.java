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
import com.cemikta.customersfx.model.User;
import com.cemikta.customersfx.service.AbstractService;
import com.cemikta.customersfx.service.UserService;
import com.cemikta.customersfx.view.UserForm;
import com.cemikta.customersfx.view.UserPage;

/**
 * User controller
 *
 * @author Cem Ikta
 */
public class UserController extends AbstractDataPageController<User> {

    @Override
    protected AbstractService<User> createService() {
        return new UserService();
    }

    @Override
    protected DataPageView<User> createDataPageView() {
        return new UserPage();
    }

    @Override
    public void openFormView(User user) {
        new UserForm(this, user).showDialog();
    }

    @Override
    public void onAddNew() {
        openFormView(new User());
    }

    @Override
    public String getNamedQuery() {
        return User.FIND_ALL;
    }

    @Override
    public String getNamedQueryWithFilter() {
        return User.FIND_BY_FILTER;
    }

    @Override
    public String getName() {
        return "UserController";
    }

}