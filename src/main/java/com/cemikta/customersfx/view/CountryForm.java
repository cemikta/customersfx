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
import com.cemikta.customersfx.control.ShortField;
import com.cemikta.customersfx.control.TextFieldExt;
import com.cemikta.customersfx.control.TitledSeparator;
import com.cemikta.customersfx.model.Country;
import com.cemikta.customersfx.mvc.AbstractFormView;
import com.cemikta.customersfx.mvc.DataPageController;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import org.controlsfx.validation.Validator;

/**
 * Country form
 *
 * @author Cem Ikta
 */
public class CountryForm extends AbstractFormView<Country> {

    private Country country;
    private TextFieldExt tfAlpha2Code;
    private TextFieldExt tfAlpha3Code;
    private ShortField sfNumericCode;
    private TextFieldExt tfName;
    private TextArea taNotes;

    public CountryForm(DataPageController<Country> controller, Country country) {
        super(CustomersFxApp.get().getMainStage(), controller);
        this.country = country;
        setTitle(getFormTitle());
    }

    @Override
    public String getFormTitle() {
        return country.isNew() ? I18n.COUNTRY.getString("form.newTitle") : I18n.COUNTRY.getString("form.editTitle");
    }

    @Override
    public Node buildFormView() {
        tfAlpha2Code = ViewHelpers.createTextFieldExt(2, 100);
        getValidationSupport().registerValidator(tfAlpha2Code, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        tfAlpha3Code = ViewHelpers.createTextFieldExt(3, 100);
        getValidationSupport().registerValidator(tfAlpha3Code, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        sfNumericCode = ViewHelpers.createShortField(100);
        getValidationSupport().registerValidator(sfNumericCode, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        tfName = ViewHelpers.createTextFieldExt(100, 300);
        getValidationSupport().registerValidator(tfName, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        taNotes = ViewHelpers.createTextArea(300, 5);

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(20, 30, 10, 30));
        formPane.setHgap(10);
        formPane.setVgap(5);

        formPane.add(new Label(I18n.COUNTRY.getString("form.alpha2Code")), 0, 0);
        formPane.add(tfAlpha2Code, 1, 0);

        formPane.add(new Label(I18n.COUNTRY.getString("form.alpha3Code")), 0, 1);
        formPane.add(tfAlpha3Code, 1, 1);

        formPane.add(new Label(I18n.COUNTRY.getString("form.numericCode")), 0, 2);
        formPane.add(sfNumericCode, 1, 2);

        formPane.add(new Label(I18n.COUNTRY.getString("form.name")), 0, 3);
        formPane.add(tfName, 1, 3);

        formPane.add(new TitledSeparator(I18n.COUNTRY.getString("form.notes")), 0, 4, 3, 1);
        formPane.add(taNotes, 1, 5);

        return formPane;
    }

    @Override
    public Country getCurrentEntity() {
        return country;
    }

    @Override
    public void pop() {
        tfAlpha2Code.setText(country.getAlpha2Code());
        tfAlpha3Code.setText(country.getAlpha3Code());
        sfNumericCode.setText(country.getNumericCode() != null ? country.getNumericCode().toString() : "");
        tfName.setText(country.getName());
        taNotes.setText(country.getNotes());
    }

    @Override
    public void push() {
        country.setAlpha2Code(tfAlpha2Code.getText());
        country.setAlpha3Code(tfAlpha3Code.getText());
        country.setNumericCode(sfNumericCode.getValue());
        country.setName(tfName.getText());
        country.setNotes(taNotes.getText());
    }

}
