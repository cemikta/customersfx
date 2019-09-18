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

import com.cemikta.customersfx.mvc.AbstractPageController;
import com.cemikta.customersfx.mvc.PageView;
import com.cemikta.customersfx.model.Customer;
import com.cemikta.customersfx.service.CustomerService;
import com.cemikta.customersfx.view.DashboardPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Dashboard controller
 *
 * @author Cem Ikta
 */
public class DashboardController extends AbstractPageController {

    private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private static final String QUERY ="SELECT cc.name, COUNT(c) FROM Customer c " +
            "INNER JOIN c.country cc GROUP BY c.country ORDER BY cc.name";

    private CustomerService service = new CustomerService();

    @Override
    protected PageView createPageView() {
        return new DashboardPage();
    }

    public int getCustomerCount() {
        return service.getListWithNamedQuery(Customer.FIND_ALL).size();
    }

    public List<Customer> getLastTenCustomer() {
        return service.getListWithNamedQuery(Customer.SORT_CREATED_AT, 10);
    }

    public ObservableList<PieChart.Data> getCustomersByCountryData() {
        logger.info("prepare customers country data for piechart");
        ObservableList<PieChart.Data> customersByCountryData = FXCollections.observableArrayList();
        CustomerService service = new CustomerService();
        List<Object[]> results = service.getUnmappedResultList(QUERY);
        results.forEach(record -> {
            String country = (String) record[0];
            Long count = (Long) record[1];
            customersByCountryData.add(new PieChart.Data(country, count));
        });

        return customersByCountryData;
    }

    @Override
    public String getName() {
        return "DashboardController";
    }

}
