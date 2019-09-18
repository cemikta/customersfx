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

package com.cemikta.customersfx.util;

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.control.MessageBox;
import javafx.geometry.Pos;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Report helpers
 *
 * @author Cem Ikta
 */
public class ReportHelpers {

    private static final Logger logger = LoggerFactory.getLogger(ReportHelpers.class);
    private static final String REPORT_BASE = "reports/";

    /**
     * Shows Jasper report viewer.
     *
     * @param name report file name
     * @param title report title
     * @param query report's query
     * @param params report parameters
     * @param connection db connection for report
     */
    public static void showReportPreview(String name, String title, String query, HashMap<String, Object> params,
                                         Connection connection) {
        logger.info("show report preview");
        try {
            JasperPrint jasperPrint = getFilledReport(name, query, params, connection);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle(title);
            jasperViewer.setExtendedState(6);
            jasperViewer.setZoomRatio(1);
            jasperViewer.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            jasperViewer.setVisible(true);
        } catch (SQLException | JRException e) {
            showException(e);
        }
    }

    /**
     * Prints Jasper report with print dialog.
     *
     * @param name report file name
     * @param query report's query
     * @param params report parameters
     * @param connection db connection for report
     */
    public static void printReport(String name, String query, HashMap<String, Object> params, Connection connection) {
        logger.info("print report");
        try {
            JasperPrint jasperPrint = getFilledReport(name, query, params, connection);
            JasperPrintManager.printReport(jasperPrint, true);
        } catch (SQLException | JRException e) {
            showException(e);
        }
    }

    /**
     * Exports Jasper report as PDF.
     *
     * @param name report file name
     * @param query report's query
     * @param params report parameters
     * @param connection db connection for report
     */
    public static void exportPDF(String name, String exportFileAbsolutePath, String query,
                                 HashMap<String, Object> params, Connection connection) {
        logger.info("export pdf report");
        try {
            JasperPrint jasperPrint = getFilledReport(name, query, params, connection);
            JasperExportManager.exportReportToPdfFile(jasperPrint, exportFileAbsolutePath);
            Notifications.create()
                    .text(I18n.COMMON.getString("reports.pdfExportSuccessful"))
                    .position(Pos.TOP_RIGHT).showInformation();
        } catch (SQLException | JRException e) {
            showException(e);
        }
    }

    private static JasperPrint getFilledReport(String name, String query, HashMap<String, Object> params,
                                               Connection connection) throws SQLException, JRException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);
        return JasperFillManager.fillReport(REPORT_BASE + name, params, new JRResultSetDataSource(resultSet));
    }

    private static void showException(Exception e) {
        logger.error("Jasper report exception {}", e);
        String contentText = "";
        if (e instanceof SQLException) {
            contentText = I18n.COMMON.getString("reports.query.error");
        } else if (e instanceof JRException) {
            contentText = I18n.COMMON.getString("reports.runtime.error");
        }
        MessageBox.create()
                .owner(CustomersFxApp.get().getMainStage())
                .contentText(contentText)
                .showError(e);
    }

}
