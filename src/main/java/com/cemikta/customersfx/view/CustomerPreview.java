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

import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.mvc.AbstractPreviewView;
import com.cemikta.customersfx.model.Customer;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Customer preview
 *
 * @author Cem Ikta
 */
public class CustomerPreview extends AbstractPreviewView<Customer> {

    private Label lblCompanyName;
    private Label lblContact;
    private Label lblLocation;
    private Label lblEmail;
    private Label lblHomepage;
    private Label lblPhone;
    private Label lblMobile;
    private Label lblSkype;

    @Override
    public String getTitle() {
        return I18n.CUSTOMER.getString("preview.title");
    }

    @Override
    public Node buildCenterView() {
        lblCompanyName = new Label();
        lblContact = new Label();

        lblLocation = ViewHelpers.createFontIconLabel(AppFontIcons.LOCATION,
                I18n.CUSTOMER.getString("preview.cityCountryTooltip"));

        lblEmail = ViewHelpers.createFontIconLabel(AppFontIcons.EMAIL,
                I18n.CUSTOMER.getString("preview.emailTooltip"));

        lblHomepage = ViewHelpers.createFontIconLabel(AppFontIcons.HOMEPAGE,
                I18n.CUSTOMER.getString("preview.homepageTooltip"));

        lblPhone = ViewHelpers.createFontIconLabel(AppFontIcons.PHONE,
                I18n.CUSTOMER.getString("preview.phoneTooltip"));

        lblMobile = ViewHelpers.createFontIconLabel(AppFontIcons.MOBILE,
                I18n.CUSTOMER.getString("preview.mobileTooltip"));

        lblSkype = ViewHelpers.createFontIconLabel(AppFontIcons.SKYPE,
                I18n.CUSTOMER.getString("preview.skypeTooltip"));

        GridPane previewPane = new GridPane();
        previewPane.setHgap(50);
        previewPane.setVgap(5);
        previewPane.setPadding(new Insets(10, 20, 10, 20));
        previewPane.getColumnConstraints().add(new ColumnConstraints(350));

        previewPane.add(lblCompanyName, 0, 0);
        previewPane.add(lblContact, 0, 1);
        previewPane.add(lblLocation, 0, 2);
        previewPane.add(lblEmail, 0, 3);
        previewPane.add(lblHomepage, 0, 4);

        previewPane.add(lblPhone, 1, 0);
        previewPane.add(lblMobile, 1, 1);
        previewPane.add(lblSkype, 1, 2);

        return previewPane;
    }

    @Override
    public void pop() {
        Customer customer = getEntity();
        lblCompanyName.setText(customer.getCompanyName());
        lblContact.setText(
                customer.getContactTitle() + " " +
                customer.getContactFirstName() + " " +
                customer.getContactLastName());

        String location = customer.getCity();
        if (customer.getCountry() != null) {
            location = location + ", " + customer.getCountry().getName();
        }

        lblLocation.setText(location);
        lblEmail.setText(customer.getEmail());
        lblHomepage.setText(customer.getHomepage());
        lblPhone.setText(customer.getPhone());
        lblMobile.setText(customer.getMobile());
        lblSkype.setText(customer.getSkype());
    }
}
