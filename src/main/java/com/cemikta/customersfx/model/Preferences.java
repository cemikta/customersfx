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

package com.cemikta.customersfx.model;

import javax.persistence.*;

/**
 * Preferences entity
 *
 * @author Cem Ikta
 */
@Entity
@Table(name = "app_preferences")
@AttributeOverride(name = "id", column = @Column(name = "app_preferences_id", nullable = false,
        columnDefinition = "BIGINT UNSIGNED"))
public class Preferences extends BaseEntity {

    @Column(name = "items_per_page", nullable = false)
    private Integer itemsPerPage;

    @Column(name = "show_message_dialog", nullable = false)
    private Boolean showMessageDialog;

    @Column(name = "show_info_popups", nullable = false)
    private Boolean showInfoPopups;

    @Column(name = "show_tip_of_the_day", nullable = false)
    private Boolean showTipOfTheDay;

    @Column(name = "report_export_directory", length = 255)
    private String reportExportDirectory;

    @Column(name = "app_theme", length = 50, nullable = false)
    private String appTheme;

    public Preferences() {
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Boolean isShowMessageDialog() {
        return showMessageDialog;
    }

    public void setShowMessageDialog(Boolean showMessageDialog) {
        this.showMessageDialog = showMessageDialog;
    }

    public Boolean isShowInfoPopups() {
        return showInfoPopups;
    }

    public void setShowInfoPopups(Boolean showInfoPopups) {
        this.showInfoPopups = showInfoPopups;
    }

    public Boolean isShowTipOfTheDay() {
        return showTipOfTheDay;
    }

    public void setShowTipOfTheDay(Boolean showTipOfTheDay) {
        this.showTipOfTheDay = showTipOfTheDay;
    }

    public String getReportExportDirectory() {
        return reportExportDirectory;
    }

    public void setReportExportDirectory(String reportExportDirectory) {
        this.reportExportDirectory = reportExportDirectory;
    }

    public String getAppTheme() {
        return appTheme;
    }

    public void setAppTheme(String appTheme) {
        this.appTheme = appTheme;
    }

    @Override
    public String toString() {
        return "Preferences{" +
                "itemsPerPage=" + itemsPerPage +
                ", showMessageDialog=" + showMessageDialog +
                ", showInfoPopups=" + showInfoPopups +
                ", showTipOfTheDay=" + showTipOfTheDay +
                ", reportExportDirectory='" + reportExportDirectory + '\'' +
                ", appTheme=" + appTheme +
                '}';
    }

}