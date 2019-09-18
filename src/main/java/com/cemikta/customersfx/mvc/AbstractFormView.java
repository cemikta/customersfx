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

package com.cemikta.customersfx.mvc;

import com.cemikta.customersfx.CustomersFxApp;
import com.cemikta.customersfx.app.AppFontIcons;
import com.cemikta.customersfx.control.*;
import com.cemikta.customersfx.model.BaseEntity;
import com.cemikta.customersfx.util.I18n;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Window;
import org.controlsfx.control.Notifications;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract form view for data editing in {@link Dialog} forms.
 *
 * @param <T> entity
 * @see DataPageController
 *
 * @author Cem Ikta
 */
public abstract class AbstractFormView<T extends BaseEntity> extends Dialog<Void> {

    private final Logger logger = LoggerFactory.getLogger(AbstractFormView.class);
    public static final String FORM_PAGES = "form-pages";

    protected DataPageController<T> controller;
    private ValidationSupport validationSupport;

    // commands
    private RibbonCommand saveCommand;
    private RibbonCommand saveAndCloseCommand;
    private RibbonCommand printPreviewCommand;
    private RibbonCommand printCommand;
    private RibbonCommand pdfCommand;
    private RibbonCommand closeCommand;
    private RibbonCommand helpCommand;

    /**
     * Creates form view
     *
     * @param owner owner stage
     * @param controller form's controller
     */
    public AbstractFormView(Window owner, DataPageController<T> controller) {
        super();
        this.controller = controller;
        initOwner(owner);
        getDialogPane().getScene().getWindow().setOnCloseRequest(event -> onCloseForm());
        buildView();
        setFormAccelerators();
    }

    public DataPageController<T> getController() {
        return this.controller;
    }

    private void buildView() {
        logger.debug("build form view");
        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("form-view");
        borderPane.setTop(buildRibbonMenu());
        Node centerNode = buildFormView();
        centerNode.getStyleClass().add("form-center");
        borderPane.setCenter(centerNode);
        getDialogPane().setContent(borderPane);
    }

    private TabPane buildRibbonMenu() {
        return new RibbonPane(buildHomeTab(), buildHelpTab());
    }

    private Tab buildHomeTab() {
        logger.debug("build ribbon home tab");
        saveCommand = new RibbonCommand(I18n.COMMON.getString("action.save"), AppFontIcons.SAVE);
        saveCommand.setOnAction(event -> onSave());

        saveAndCloseCommand = new RibbonCommand(I18n.COMMON.getString("action.saveAndClose"), AppFontIcons.SAVE);
        saveAndCloseCommand.setOnAction(event -> onSaveAndClose());

        RibbonGroup homeGroup = new RibbonGroup(saveCommand, saveAndCloseCommand);

        if (hasPrintActions()) {
            printPreviewCommand = new RibbonCommand(I18n.COMMON.getString("action.printPreview"),
                    AppFontIcons.PRINT_PREVIEW);
            printPreviewCommand.setOnAction(event -> {
                if (checkFormForReport()) {
                    getController().onFormPrintPreview(getCurrentEntity().getId());
                }
            });

            printCommand = new RibbonCommand(I18n.COMMON.getString("action.print"), AppFontIcons.PRINT);
            printCommand.setOnAction(event -> {
                if (checkFormForReport()) {
                    getController().onFormPrint(getCurrentEntity().getId());
                }
            });

            pdfCommand = new RibbonCommand(I18n.COMMON.getString("action.pdf"), AppFontIcons.PDF);
            pdfCommand.setOnAction(event -> {
                if (checkFormForReport()) {
                    getController().onFormPdf(getCurrentEntity().getId());
                }
            });

            homeGroup.getChildren().addAll(RibbonGroup.addSeparator(), printPreviewCommand, printCommand, pdfCommand);
        }

        closeCommand = new RibbonCommand(I18n.COMMON.getString("action.close"), AppFontIcons.CLOSE);
        closeCommand.setOnAction(event -> onCloseForm());

        homeGroup.getChildren().addAll(RibbonGroup.addSeparator(), closeCommand);

        return new RibbonTab(I18n.COMMON.getString("app.ribbon.home"), homeGroup);
    }

    private Tab buildHelpTab() {
        logger.debug("build ribbon help tab");
        helpCommand = new RibbonCommand(
                I18n.COMMON.getString("action.help"), AppFontIcons.HELP);
        helpCommand.setOnAction(event -> CustomersFxApp.get().showAppHelp());
        RibbonGroup helpGroup = new RibbonGroup(helpCommand);

        return new RibbonTab(I18n.COMMON.getString("app.ribbon.help"), helpGroup);
    }

    private void setFormAccelerators() {
        logger.debug("set form view accelerators");
        Scene scene = getDialogPane().getScene();
        if (scene == null) {
            throw new IllegalArgumentException("setFormAccelerators must be called when the FormView is " +
                    "attached to a scene");
        }
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN), saveCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN),
                saveAndCloseCommand::fire);
        if (hasPrintActions()) {
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN),
                    printPreviewCommand::fire);
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN),
                    printCommand::fire);
            scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN),
                    pdfCommand::fire);
        }
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), closeCommand::fire);
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.F1), helpCommand::fire);
    }

    /**
     * Gets the title of the form view
     *
     * @return form title
     */
    public abstract String getFormTitle();

    /**
     * Builds form view controls
     *
     * @return center node of the form view
     */
    public abstract Node buildFormView();

    /**
     * If the form view has print actions, this method should be overridden.
     * Form view has no default print actions.
     *
     * @return true if the form view has print actions, otherwise false.
     */
    public Boolean hasPrintActions() {
        return false;
    }

    /**
     * Gets the current entity object
     *
     * @return entity of the form view
     */
    public abstract T getCurrentEntity();

    /**
     * Pop data model values to form view
     */
    public abstract void pop();

    /**
     * Push form view values to data model
     */
    public abstract void push();

    /**
     * If the fields of the form view is valid, save the data of the form view.
     */
    private void onSave() {
        logger.debug("form view save action");
        if (isFormValid()) {
            push();
            controller.onSave(getCurrentEntity());
        }
    }

    /**
     * If the fields of the form view are valid, save the data of the form view and close the form view.
     */
    private void onSaveAndClose() {
        logger.debug("form view save and close action");
        if (isFormValid()) {
            push();
            controller.onSave(getCurrentEntity());
            onCloseForm();
        }
    }

    private boolean isFormValid() {
        logger.debug("form view validation");
        if (getValidationSupport().isInvalid()) {
            getValidationSupport().initInitialDecoration();
            if (CustomersFxApp.get().getPreferences().isShowInfoPopups()) {
                Notifications.create()
                        .text(I18n.COMMON.getString("notification.saveValidationError"))
                        .position(Pos.TOP_RIGHT).showWarning();
            }
        }
        return !getValidationSupport().isInvalid();
    }

    /**
     * Gets validation support of the form view.
     *
     * @return validation support
     */
    protected ValidationSupport getValidationSupport() {
        if (validationSupport == null) {
            validationSupport = new ValidationSupport();
            validationSupport.setValidationDecorator(new GraphicValidationDecoration());
        }
        return validationSupport;
    }

    public void showDialog() {
        pop();
        showAndWait();
    }

    private boolean checkFormForReport() {
        if (getCurrentEntity().getId() == null) {
            MessageBox.create()
                    .owner(CustomersFxApp.get().getMainStage())
                    .contentText(I18n.COMMON.getString("reports.formNotSaved"))
                    .showWarning();
            return false;
        }
        return true;
    }

    private void onCloseForm() {
        getDialogPane().getScene().getWindow().hide();
    }

}
