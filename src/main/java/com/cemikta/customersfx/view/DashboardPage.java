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
import com.cemikta.customersfx.control.fonticon.FontIcon;
import com.cemikta.customersfx.control.fonticon.FontIconColor;
import com.cemikta.customersfx.control.fonticon.FontIconFactory;
import com.cemikta.customersfx.control.fonticon.FontIconSize;
import com.cemikta.customersfx.controller.DashboardController;
import com.cemikta.customersfx.mvc.PageController;
import com.cemikta.customersfx.mvc.PageView;
import com.cemikta.customersfx.model.Customer;
import com.cemikta.customersfx.util.CssHelpers;
import com.cemikta.customersfx.util.I18n;
import com.cemikta.customersfx.util.ViewHelpers;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class DashboardPage implements PageView {

    private BorderPane pageView;
    private PageController controller;
    private Label lblCustomerCount;
    private VBox customerBox;
    private PieChart chart;

    @Override
    public void init(PageController controller) {
        this.controller = controller;
        buildView();
        refreshData();
    }

    private void buildView() {
        pageView = new BorderPane();
        pageView.getStyleClass().addAll("page-view");
        pageView.setTop(buildHeaderBar());
        pageView.setCenter(buildCenter());
    }

    private HBox buildHeaderBar() {
        // title
        Label title = ViewHelpers.createFontIconLabel(getTitle(), getFontIcon(), "");
        title.getStyleClass().add("page-title");

        HBox header = new HBox();
        header.getStyleClass().add("page-header");
        header.getChildren().add(title);
        return header;
    }

    private HBox buildCenter() {
        Text icon = FontIconFactory.createIcon(AppFontIcons.CUSTOMER, FontIconSize.XL, FontIconColor.BLUE);
        lblCustomerCount = new Label();
        lblCustomerCount.setPadding(new Insets(10, 0, 0, 0));
        CssHelpers.setFontSize(lblCustomerCount, 18);

        HBox countBox = new HBox(10);
        countBox.setPadding(new Insets(30, 30, 30, 20));
        countBox.getStyleClass().addAll("box-pane", "box-pane-blue");
        countBox.setPrefWidth(500);
        countBox.getChildren().addAll(icon, lblCustomerCount);

        Label lblLatestTen = new Label(I18n.DASHBOARD.getString("latestTen"));
        lblLatestTen.setAlignment(Pos.CENTER);
        CssHelpers.setFontSize(lblLatestTen, 18);
        Separator latestTenSeparator = new Separator();
        latestTenSeparator.setMaxWidth(300);

        customerBox = new VBox(5);
        customerBox.getStyleClass().addAll("box-pane", "box-pane-green");
        customerBox.setPrefWidth(500);
        customerBox.setPadding(new Insets(30, 30, 30, 30));
        customerBox.getChildren().addAll(lblLatestTen, latestTenSeparator);

        VBox leftBox = new VBox(20);
        VBox.setVgrow(customerBox, Priority.ALWAYS);
        leftBox.getChildren().addAll(countBox, customerBox);

        chart = new PieChart();
        chart.setTitle(I18n.DASHBOARD.getString("customersByCountryChart.title"));
        chart.setLegendVisible(false);

        VBox chartBox = new VBox(5);
        chartBox.setPadding(new Insets(30, 30, 30, 30));
        chartBox.getStyleClass().addAll("box-pane", "box-pane-orange");
        chartBox.setPrefWidth(500);
        chartBox.getChildren().add(chart);

        HBox center = new HBox(20);
        center.setAlignment(Pos.CENTER);
        center.getStyleClass().add("dashboard-center");
        center.getChildren().addAll(leftBox, chartBox);

        return center;
    }

    private void refreshData() {
        // count
        int customerCount = ((DashboardController)getController()).getCustomerCount();
        if (customerCount > 1) {
            lblCustomerCount.setText(customerCount + " \n"+ I18n.DASHBOARD.getString("customers"));
        } else {
            lblCustomerCount.setText(customerCount + " \n"+ I18n.DASHBOARD.getString("customer"));
        }

        // latest ten
        List<Customer> latestTenCustomer = ((DashboardController)getController()).getLastTenCustomer();
        for (Customer customer : latestTenCustomer) {
            Label companyName = new Label(customer.getCompanyName());
            CssHelpers.setFontSize(companyName, 14);
            customerBox.getChildren().add(companyName);
        }

        // chart
        ObservableList<PieChart.Data> customersByCountryData =
                ((DashboardController)getController()).getCustomersByCountryData();
        customersByCountryData.forEach(data ->
                data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty().intValue()))
        );
        chart.setData(customersByCountryData);
    }

    @Override
    public PageController getController() {
        return this.controller;
    }

    @Override
    public Node asNode() {
        return this.pageView;
    }
    
    @Override
    public FontIcon getFontIcon() {
        return AppFontIcons.DASHBOARD;
    }

    @Override
    public String getTitle() {
        return I18n.DASHBOARD.getString("title");
    }

}
