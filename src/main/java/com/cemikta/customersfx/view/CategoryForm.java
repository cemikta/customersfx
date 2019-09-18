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
import com.cemikta.customersfx.model.Category;
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
 * Category form
 *
 * @author Cem Ikta
 */
public class CategoryForm extends AbstractFormView<Category> {

    private Category category;
    private TextFieldExt tfName;
    private TextArea taNotes;

    public CategoryForm(DataPageController<Category> controller, Category category) {
        super(CustomersFxApp.get().getMainStage(), controller);
        this.category = category;
        setTitle(getFormTitle());
    }

    @Override
    public String getFormTitle() {
        return category.isNew() ? I18n.CATEGORY.getString("form.newTitle") : I18n.CATEGORY.getString("form.editTitle");
    }

    @Override
    public Node buildFormView() {
        tfName = ViewHelpers.createTextFieldExt(100, 300);
        getValidationSupport().registerValidator(tfName, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        taNotes = ViewHelpers.createTextArea(300, 5);

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(20, 30, 10, 30));
        formPane.setHgap(10);
        formPane.setVgap(5);

        formPane.add(new Label(I18n.CATEGORY.getString("form.name")), 0, 0);
        formPane.add(tfName, 1, 0);

        formPane.add(new TitledSeparator(I18n.CATEGORY.getString("form.notes")), 0, 1, 3, 1);
        formPane.add(taNotes, 1, 2);

        return formPane;
    }

    @Override
    public Category getCurrentEntity() {
        return category;
    }

    @Override
    public void pop() {
        tfName.setText(category.getName());
        taNotes.setText(category.getNotes());
    }

    @Override
    public void push() {
        category.setName(tfName.getText());
        category.setNotes(taNotes.getText());
    }

}
