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

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.control.MessageBox;
import com.cemikta.customersfx.mvc.AbstractDataPageController;
import com.cemikta.customersfx.mvc.DataPageView;
import com.cemikta.customersfx.model.Category;
import com.cemikta.customersfx.model.Country;
import com.cemikta.customersfx.model.Customer;
import com.cemikta.customersfx.service.AbstractService;
import com.cemikta.customersfx.service.CategoryService;
import com.cemikta.customersfx.service.CountryService;
import com.cemikta.customersfx.service.CustomerService;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ReportHelpers;
import com.cemikta.customersfx.view.CustomerForm;
import com.cemikta.customersfx.view.CustomerPage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import net.sf.jasperreports.engine.JRParameter;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Customer controller
 *
 * @author Cem Ikta
 */
public class CustomerController extends AbstractDataPageController<Customer> {

    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private static final String CUSTOMER_LIST_REPORT = "customerList.jasper";
    private static final String CUSTOMER_LIST_QUERY = "SELECT customer_id, company_name, ca.name AS category, " +
            "concat(contact_title, ' ', contact_first_name, ' ', contact_last_name) AS contact, " +
            "concat(city, ', ', co.name) AS location, phone, mobile " +
            "FROM customer c " +
            "LEFT JOIN category ca ON c.category_id = ca.category_id " +
            "LEFT JOIN country co ON c.country_id = co.country_id " +
            "ORDER BY company_name";
    private static final String CUSTOMER_DETAILS_REPORT = "customerDetails.jasper";
    private static final String CUSTOMER_DETAILS_QUERY = "SELECT customer_id, company_name, ca.name AS category, " +
            "concat(contact_title, ' ', contact_first_name, ' ', contact_last_name) AS contact, active, " +
            "address, city, region, postal_code, co.name AS country, phone, mobile, fax, email, homepage, " +
            "skype, c.notes " +
            "FROM customer c " +
            "LEFT JOIN category ca ON c.category_id = ca.category_id " +
            "LEFT JOIN country co ON c.country_id = co.country_id " +
            "WHERE customer_id = %d";

    @Override
    protected AbstractService<Customer> createService() {
        return new CustomerService();
    }

    @Override
    protected DataPageView<Customer> createDataPageView() {
        return new CustomerPage();
    }

    @Override
    public void openFormView(Customer customer) {
        new CustomerForm(this, customer).showDialog();
    }

    @Override
    public void onAddNew() {
        openFormView(new Customer());
    }

    public List<Category> getCategories() {
        return new CategoryService().getListWithNamedQuery(Category.FIND_ALL);
    }

    public List<Country> getCountries() {
        return new CountryService().getListWithNamedQuery(Country.FIND_ALL);
    }

    @Override
    public void onPrintPreview() {
        logger.info("on print preview action");
        ReportHelpers.showReportPreview(CUSTOMER_LIST_REPORT, I18n.CUSTOMER.getString("customerList.reportTitle"),
                CUSTOMER_LIST_QUERY, getReportParams(), getReportConnection());
    }

    @Override
    public void onPrint() {
        logger.info("on print action");
        ReportHelpers.printReport(CUSTOMER_LIST_REPORT, CUSTOMER_LIST_QUERY, getReportParams(), getReportConnection());
    }

    @Override
    public void onPdf() {
        logger.info("on pdf action");
        String exportDirectory = CustomersFxApp.get().getPreferences().getReportExportDirectory();
        if (StringUtils.isEmpty(exportDirectory)) {
            showWarningEmptyExportDirectory();
        } else {
            String exportFileAbsolutePath = generatePdfFileName(exportDirectory, "CustomerList");
            ReportHelpers.exportPDF(CUSTOMER_LIST_REPORT, exportFileAbsolutePath, CUSTOMER_LIST_QUERY,
                    getReportParams(), getReportConnection());
        }
    }

    @Override
    public void onFormPrintPreview(Long id) {
        logger.info("on form print preview action");
        String query = String.format(CUSTOMER_DETAILS_QUERY, id);
        ReportHelpers.showReportPreview(CUSTOMER_DETAILS_REPORT, I18n.CUSTOMER.getString("customerDetails.reportTitle"),
                query, getReportParams(), getReportConnection());
    }

    @Override
    public void onFormPrint(Long id) {
        logger.info("on form print action");
        String query = String.format(CUSTOMER_DETAILS_QUERY, id);
        ReportHelpers.printReport(CUSTOMER_DETAILS_REPORT, query, getReportParams(), getReportConnection());
    }

    @Override
    public void onFormPdf(Long id) {
        logger.info("on form pdf action");
        String exportDirectory = CustomersFxApp.get().getPreferences().getReportExportDirectory();
        if (StringUtils.isEmpty(exportDirectory)) {
            showWarningEmptyExportDirectory();
        } else {
            String exportFileAbsolutePath = generatePdfFileName(exportDirectory, "CustomerDetails");
            String query = String.format(CUSTOMER_DETAILS_QUERY, id);
            ReportHelpers.exportPDF(CUSTOMER_DETAILS_REPORT, exportFileAbsolutePath, query, getReportParams(),
                    getReportConnection());
        }
    }

    private HashMap<String, Object> getReportParams() {
        HashMap<String, Object> params = new HashMap<>();
        params.put(JRParameter.REPORT_LOCALE, Locale.getDefault());
        return params;
    }

    private Connection getReportConnection() {
        return getService().getConnection();
    }

    private String generatePdfFileName(String exportDirectory, String reportFileName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return exportDirectory + "/" + reportFileName + "-" + timestamp + ".pdf";
    }

    private void showWarningEmptyExportDirectory() {
        MessageBox.create()
                .owner(CustomersFxApp.get().getMainStage())
                .contentText(I18n.COMMON.getString("reports.emptyExportDirectory"))
                .showWarning();
    }

    @Override
    public String getNamedQuery() {
        return Customer.FIND_ALL;
    }

    @Override
    public String getNamedQueryWithFilter() {
        return Customer.FIND_BY_FILTER;
    }

    @Override
    public String getName() {
        return "CustomerController";
    }

}