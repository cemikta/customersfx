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

/**
 * Abstract page controller
 *
 * @author Cem Ikta
 */
public abstract class AbstractPageController implements PageController {

    private PageView pageView;

    @Override
    public PageView getPageView() {
        if (pageView == null) {
            pageView = createPageView();
            pageView.init(this);
        }
        return pageView;
    }

    /**
     * Creates page view of the controller.
     *
     * @return page view
     */
    protected abstract PageView createPageView();

}