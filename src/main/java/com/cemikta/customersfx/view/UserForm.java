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
import com.cemikta.customersfx.control.PasswordFieldExt;
import com.cemikta.customersfx.control.TextFieldExt;
import com.cemikta.customersfx.control.TitledSeparator;
import com.cemikta.customersfx.model.User;
import com.cemikta.customersfx.model.UserRole;
import com.cemikta.customersfx.mvc.AbstractFormView;
import com.cemikta.customersfx.mvc.DataPageController;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import org.controlsfx.validation.Validator;

import java.util.Arrays;

/**
 * User form
 *
 * @author Cem Ikta
 */
public class UserForm extends AbstractFormView<User> {

    private User user;
    private TextFieldExt tfUsername;
    private PasswordFieldExt pfPassword;
    private TextFieldExt tfName;
    private ComboBox<UserRole> cbUserRole;
    private CheckBox chbActive;
    private TextArea taNotes;

    public UserForm(DataPageController<User> controller, User user) {
        super(CustomersFxApp.get().getMainStage(), controller);
        this.user = user;
        setTitle(getFormTitle());
    }

    @Override
    public String getFormTitle() {
        return user.isNew() ? I18n.USER.getString("form.newTitle") : I18n.USER.getString("form.editTitle");
    }

    @Override
    public Node buildFormView() {
        tfUsername = ViewHelpers.createTextFieldExt(20, 300);
        getValidationSupport().registerValidator(tfUsername, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        pfPassword = ViewHelpers.createPasswordFieldExt(20, 300);
        getValidationSupport().registerValidator(pfPassword, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        tfName = ViewHelpers.createTextFieldExt(100, 300);
        getValidationSupport().registerValidator(tfName, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        cbUserRole = new ComboBox<>();
        cbUserRole.setPrefWidth(300);
        cbUserRole.getItems().addAll(Arrays.asList(UserRole.values()));
        getValidationSupport().registerValidator(cbUserRole, false,
                Validator.createEmptyValidator(I18n.COMMON.getString("validation.required")));

        chbActive = new CheckBox(I18n.USER.getString("form.active"));

        taNotes = ViewHelpers.createTextArea(300, 5);

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(20, 30, 10, 30));
        formPane.setHgap(10);
        formPane.setVgap(5);

        formPane.add(new Label(I18n.USER.getString("form.username")), 0, 0);
        formPane.add(tfUsername, 1, 0);

        formPane.add(new Label(I18n.USER.getString("form.password")), 0, 1);
        formPane.add(pfPassword, 1, 1);

        formPane.add(new Label(I18n.USER.getString("form.name")), 0, 2);
        formPane.add(tfName, 1, 2);

        formPane.add(new Label(I18n.USER.getString("form.userRole")), 0, 3);
        formPane.add(cbUserRole, 1, 3);

        formPane.add(new TitledSeparator(I18n.USER.getString("form.activeTitle")), 0, 4, 3, 1);
        formPane.add(chbActive, 1, 5);

        formPane.add(new TitledSeparator(I18n.USER.getString("form.notes")), 0, 6, 3, 1);
        formPane.add(taNotes, 1, 7);

        return formPane;
    }

    @Override
    public User getCurrentEntity() {
        return user;
    }

    @Override
    public void pop() {
        tfUsername.setText(user.getUsername());
        pfPassword.setText(user.getPassword());
        tfName.setText(user.getName());
        cbUserRole.getSelectionModel().select(user.getUserRole());
        chbActive.setSelected(user.isActive());
        taNotes.setText(user.getNotes());
    }

    @Override
    public void push() {
        user.setUsername(tfUsername.getText());
        user.setPassword(pfPassword.getText());
        user.setName(tfName.getText());
        user.setUserRole(cbUserRole.getSelectionModel().getSelectedItem());
        user.setActive(chbActive.isSelected());
        user.setNotes(taNotes.getText());
    }

}
