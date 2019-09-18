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

package com.cemikta.customersfx.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Query parameters for services.
 *
 * <pre>{@code // import static com.cemikta.customersfx.service.QueryParameter.*;
 * count = userService.findWithNamedQuery(User.FIND_ALL, with(&quot;name&quot;, "filter").parameters()).size();}</pre>
 *
 * @see AbstractService
 *
 * @author Cem Ikta
 */
public class QueryParameter {

    private Map<String, Object> parameters = null;

    /**
     * New instance is possible only <code>with</code>
     *
     * @param name parameter name
     * @param value parameter value
     */
    private QueryParameter(String name, Object value) {
        this.parameters = new HashMap<>();
        this.parameters.put(name, value);
    }

    /**
     * Creates new QueryParameter instance.
     *
     * @param name parameter name
     * @param value parameter value
     * @return neue Instance von Query Parameter.
     */
    public static QueryParameter with(String name, Object value) {
        return new QueryParameter(name, value);
    }

    /**
     * Adds new parameter. It is not <code>and</code> parameter in NamedQuery.
     *
     * @param name parameter name
     * @param value parameter value
     * @return Query Parameter instance
     */
    public QueryParameter and(String name, Object value) {
        this.parameters.put(name, value);
        return this;
    }

    /**
     * Gets parameters map.
     *
     * @return parameters map
     */
    public Map<String, Object> parameters() {
        return this.parameters;
    }

}
