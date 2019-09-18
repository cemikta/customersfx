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

package com.cemikta.customersfx.app;

import com.cemikta.customersfx.util.I18n;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * App splash task for splash control
 *
 * @author Cem Ikta
 */
public class SplashTask extends Task<ObservableList<String>> {

    private ObservableList<String> moduleList;

    public SplashTask() {
        super();
        moduleList = FXCollections.observableArrayList(
                I18n.COMMON.getString("app.module.dashboard"),
                I18n.COMMON.getString("app.module.customer"),
                I18n.COMMON.getString("app.module.category"),
                I18n.COMMON.getString("app.module.country"),
                I18n.COMMON.getString("app.module.user"),
                I18n.COMMON.getString("app.module.preferences")
        );
    }

    @Override
    protected ObservableList<String> call() throws Exception {
        updateMessage(I18n.COMMON.getString("app.loading"));
        for (int i = 0; i < moduleList.size(); i++) {
            Thread.sleep(100);
            updateProgress(i + 1, moduleList.size());
            String next = moduleList.get(i);
            updateMessage(I18n.COMMON.getString("app.loading.module", next));
        }
        Thread.sleep(100);
        updateMessage(I18n.COMMON.getString("app.loading.done"));

        return moduleList;
    }

}
