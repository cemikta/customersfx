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

package com.cemikta.customersfx.view;

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.control.TextFieldExt;
import com.cemikta.customersfx.control.TitledSeparator;
import com.cemikta.customersfx.controller.CustomerController;
import com.cemikta.customersfx.model.Category;
import com.cemikta.customersfx.model.Country;
import com.cemikta.customersfx.model.Customer;
import com.cemikta.customersfx.mvc.AbstractFormView;
import com.cemikta.customersfx.mvc.DataPageController;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import org.controlsfx.validation.Validator;

/**
 * Customer form
 *
 * @author Cem Ikta
 */
public class CustomerForm extends AbstractFormView<Customer> {

    private Customer customer;
    // General page
    private TextFieldExt tfCompanyName;
    private ComboBox<Category> cbCategory;
    private TextFieldExt tfTitle;
    private TextFieldExt tfFirstName;
    private TextFieldExt tfLastName;
    private CheckBox chbActive;
    // Address page
    private TextArea taAddress;
    private TextFieldExt tfCity;
    private TextFieldExt tfRegion;
    private TextFieldExt tfPostalCode;
    private ComboBox<Country> cbCountry;
    // Communication page
    private TextFieldExt tfPhone;
    private TextFieldExt tfMobile;
    private TextFieldExt tfFax;
    private TextFieldExt tfEmail;
    private TextFieldExt tfHomepage;
    private TextFieldExt tfSkype;
    // Notes page
    private TextArea taNotes;

    public CustomerForm(DataPageController<Customer> controller, Customer customer) {
        super(CustomersFxApp.get().getMainStage(), controller);
        this.customer = customer;
        setTitle(getFormTitle());
    }

    @Override
    public String getFormTitle() {
        return customer.isNew() ? I18n.CUSTOMER.getString("form.newTitle") : I18n.CUSTOMER.getString("form.editTitle");
    }

    @Override
    public Node buildFormView() {
        TabPane tabPanePages = new TabPane(buildGeneralPage(), buildAddressPage(), buildCommunicationPage(),
                buildNotesPage());
        tabPanePages.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPanePages.getStyleClass().add(FORM_PAGES);
        return tabPanePages;
    }

    private Tab buildGeneralPage() {
        tfCompanyName = ViewHelpers.createTextFieldExt(100, 300);
        cbCategory = new ComboBox<>();
        cbCategory.setPromptText(I18n.CUSTOMER.getString("form.categorySelect"));
        cbCategory.setPrefWidth(300);
        cbCategory.getItems().addAll(((CustomerController) getController()).getCategories());
        tfTitle = ViewHelpers.createTextFieldExt(50, 300);
        tfFirstName = ViewHelpers.createTextFieldExt(100, 300);
        tfLastName = ViewHelpers.createTextFieldExt(100, 300);
        chbActive = new CheckBox(I18n.CUSTOMER.getString("form.active"));

        addValidator(tfCompanyName);
        addValidator(cbCategory);
        addValidator(tfFirstName);
        addValidator(tfLastName);

        GridPane generalForm = new GridPane();
        generalForm.setPadding(new Insets(20, 30, 20, 30));
        generalForm.setHgap(10);
        generalForm.setVgap(5);

        generalForm.add(new Label(I18n.CUSTOMER.getString("form.companyName")), 0, 0);
        generalForm.add(tfCompanyName, 1, 0);

        generalForm.add(new Label(I18n.CUSTOMER.getString("form.category")), 0, 1);
        generalForm.add(cbCategory, 1, 1);

        generalForm.add(new Label(I18n.CUSTOMER.getString("form.contactTitle")), 0, 2);
        generalForm.add(tfTitle, 1, 2);

        generalForm.add(new Label(I18n.CUSTOMER.getString("form.firstName")), 0, 3);
        generalForm.add(tfFirstName, 1, 3);

        generalForm.add(new Label(I18n.CUSTOMER.getString("form.lastName")), 0, 4);
        generalForm.add(tfLastName, 1, 4);

        generalForm.add(new TitledSeparator(I18n.CUSTOMER.getString("form.activeTitle")), 0, 5, 3, 1);
        generalForm.add(chbActive, 1, 6);

        return new Tab(I18n.CUSTOMER.getString("form.generalPage"), generalForm);
    }

    private void addValidator(Control control) {
        getValidationSupport().registerValidator(control, false, Validator.createEmptyValidator(
                I18n.COMMON.getString("validation.required")));
    }

    private Tab buildAddressPage() {
        taAddress = ViewHelpers.createTextArea(300, 2);
        tfCity = ViewHelpers.createTextFieldExt(100, 300);
        tfRegion = ViewHelpers.createTextFieldExt(50, 300);
        tfPostalCode = ViewHelpers.createTextFieldExt(100, 300);
        cbCountry = new ComboBox<>();
        cbCountry.setPromptText(I18n.CUSTOMER.getString("form.countrySelect"));
        cbCountry.setPrefWidth(300);
        cbCountry.getItems().addAll(((CustomerController) getController()).getCountries());

        GridPane addressForm = new GridPane();
        addressForm.setPadding(new Insets(20, 30, 20, 30));
        addressForm.setHgap(10);
        addressForm.setVgap(5);

        addressForm.add(new Label(I18n.CUSTOMER.getString("form.address")), 0, 0);
        addressForm.add(taAddress, 1, 0);

        addressForm.add(new Label(I18n.CUSTOMER.getString("form.city")), 0, 1);
        addressForm.add(tfCity, 1, 1);

        addressForm.add(new Label(I18n.CUSTOMER.getString("form.region")), 0, 2);
        addressForm.add(tfRegion, 1, 2);

        addressForm.add(new Label(I18n.CUSTOMER.getString("form.postalCode")), 0, 3);
        addressForm.add(tfPostalCode, 1, 3);

        addressForm.add(new Label(I18n.CUSTOMER.getString("form.country")), 0, 4);
        addressForm.add(cbCountry, 1, 4);

        return new Tab(I18n.CUSTOMER.getString("form.addressPage"), addressForm);
    }

    private Tab buildCommunicationPage() {
        tfPhone = ViewHelpers.createTextFieldExt(100, 300);
        tfMobile = ViewHelpers.createTextFieldExt(100, 300);
        tfFax = ViewHelpers.createTextFieldExt(100, 300);
        tfEmail = ViewHelpers.createTextFieldExt(100, 300);
        tfHomepage = ViewHelpers.createTextFieldExt(100, 300);
        tfSkype = ViewHelpers.createTextFieldExt(100, 300);

        GridPane communicationForm = new GridPane();
        communicationForm.setPadding(new Insets(20, 30, 20, 30));
        communicationForm.setHgap(10);
        communicationForm.setVgap(5);

        communicationForm.add(new Label(I18n.CUSTOMER.getString("form.phone")), 0, 0);
        communicationForm.add(tfPhone, 1, 0);

        communicationForm.add(new Label(I18n.CUSTOMER.getString("form.mobile")), 0, 1);
        communicationForm.add(tfMobile, 1, 1);

        communicationForm.add(new Label(I18n.CUSTOMER.getString("form.fax")), 0, 2);
        communicationForm.add(tfFax, 1, 2);

        communicationForm.add(new Label(I18n.CUSTOMER.getString("form.email")), 0, 3);
        communicationForm.add(tfEmail, 1, 3);

        communicationForm.add(new Label(I18n.CUSTOMER.getString("form.homepage")), 0, 4);
        communicationForm.add(tfHomepage, 1, 4);

        communicationForm.add(new Label(I18n.CUSTOMER.getString("form.skype")), 0, 5);
        communicationForm.add(tfSkype, 1, 5);

        return new Tab(I18n.CUSTOMER.getString("form.communicationPage"), communicationForm);
    }

    private Tab buildNotesPage() {
        taNotes = ViewHelpers.createTextArea(300, 10);

        GridPane notesFormPane = new GridPane();
        notesFormPane.setPadding(new Insets(20, 30, 20, 30));
        notesFormPane.setHgap(10);
        notesFormPane.setVgap(5);

        notesFormPane.add(new Label(I18n.CUSTOMER.getString("form.notes")), 0, 0);
        notesFormPane.add(taNotes, 1, 0);

        return new Tab(I18n.CUSTOMER.getString("form.notesPage"), notesFormPane);
    }

    @Override
    public Customer getCurrentEntity() {
        return customer;
    }

    @Override
    public void pop() {
        // general page
        tfCompanyName.setText(customer.getCompanyName());
        cbCategory.getSelectionModel().select(customer.getCategory());
        tfTitle.setText(customer.getContactTitle());
        tfFirstName.setText(customer.getContactFirstName());
        tfLastName.setText(customer.getContactLastName());
        chbActive.setSelected(customer.isActive() != null ? customer.isActive() : false);
        // address page
        taAddress.setText(customer.getAddress());
        tfCity.setText(customer.getCity());
        tfRegion.setText(customer.getRegion());
        tfPostalCode.setText(customer.getPostalCode());
        cbCountry.getSelectionModel().select(customer.getCountry());
        // communication page
        tfPhone.setText(customer.getPhone());
        tfMobile.setText(customer.getMobile());
        tfFax.setText(customer.getFax());
        tfEmail.setText(customer.getEmail());
        tfHomepage.setText(customer.getHomepage());
        tfSkype.setText(customer.getSkype());
        // notes page
        taNotes.setText(customer.getNotes());
    }

    @Override
    public void push() {
        // general page
        customer.setCompanyName(tfCompanyName.getText());
        customer.setCategory(cbCategory.getSelectionModel().getSelectedItem());
        customer.setContactTitle(tfTitle.getText());
        customer.setContactFirstName(tfFirstName.getText());
        customer.setContactLastName(tfLastName.getText());
        customer.setActive(chbActive.isSelected());
        // address page
        customer.setAddress(taAddress.getText());
        customer.setCity(tfCity.getText());
        customer.setRegion(tfRegion.getText());
        customer.setPostalCode(tfPostalCode.getText());
        customer.setCountry(cbCountry.getSelectionModel().getSelectedItem());
        // communication page
        customer.setPhone(tfPhone.getText());
        customer.setMobile(tfMobile.getText());
        customer.setFax(tfFax.getText());
        customer.setEmail(tfEmail.getText());
        customer.setHomepage(tfHomepage.getText());
        customer.setSkype(tfSkype.getText());
        // notes page
        customer.setNotes(taNotes.getText());
    }

    @Override
    public Boolean hasPrintActions() {
        return true;
    }
}
