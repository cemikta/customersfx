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

package com.cemikta.customersfx.control;

import com.cemikta.customersfx.util.I18n;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Window;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * MessageBox control for information, warning, confirmation, error and exception message dialogs.
 *
 * <p><strong>Code snippet for information message box:</strong>
 * <pre>{@code MessageBox.create()
 *      .owner(mainStage)
 *      .contentText("Information message")
 *      .showInformation();}</pre>
 *
 * <p><strong>Code snippet for warning message box:</strong>
 * <pre>{@code MessageBox.create()
 *      .owner(mainStage)
 *      .contentText("Warning message")
 *      .showWarning();}</pre>
 *
 * <p><strong>Code snippet for confirmation message box:</strong>
 * <pre>{@code Optional<ButtonType> result = MessageBox.create()
 *      .owner(mainStage)
 *      .contentText("Confirmation message")
 *      .showConfirmation();
 *  if (result.isPresent() && result.get() == ButtonType.YES) {
 *      exitApplication();
 *  }}</pre>
 *
 * <p><strong>Code snippet for error message box:</strong>
 * <pre>{@code MessageBox.create()
 *      .owner(mainStage)
 *      .contentText("Error message")
 *      .showError();}</pre>
 *
 * <p><strong>Code snippet for error message box with exception:</strong>
 * <pre>{@code MessageBox.create()
 *      .owner(mainStage)
 *      .contentText("Error message")
 *      .showError(exception);}</pre>
 *
 * @see Alert
 * @see ButtonType
 *
 * @author Cem Ikta
 */
public final class MessageBox {

    private Alert.AlertType type;
    private Window owner;
    private String title;
    private String headerText;
    private String contentText;
    private Throwable exception;
    private Set<ButtonType> actions = new LinkedHashSet<>();

    /**
     * Creates new instance of MessageBox.
     *
     * @return messageBox.
     */
    public static MessageBox create() {
        return new MessageBox();
    }

    /**
     * Owner property
     * @param owner the owner stage {@link Window} for this dialog.
     * @return message box.
     */
    public MessageBox owner(Window owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Title property
     *
     * @param title The title.
     * @return message box.
     */
    public MessageBox title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Header text property.
     *
     * @param headerText The header text.
     * @return message box.
     */
    public MessageBox headerText(String headerText) {
        this.headerText = headerText;
        return this;
    }

    /**
     * Content text property.
     *
     * @param contentText The content text.
     * @return message box.
     */
    public MessageBox contentText(String contentText) {
        this.contentText = contentText;
        return this;
    }

    /**
     * Shows information message box.
     */
    public void showInformation() {
        this.type = Alert.AlertType.INFORMATION;
        show();
    }

    /**
     * Shows warning message box.
     */
    public void showWarning(){
        this.type = Alert.AlertType.WARNING;
        show();
    }

    /**
     * Shows confirmation message box with delete and cancel actions.
     *
     * @return An {@link Optional} that contains the clicked {@link ButtonType result}.
     */
    public Optional<ButtonType> showDeleteConfirmation(){
        ButtonType deleteButtonType = new ButtonType(I18n.CONTROL.getString("messageBox.deleteButton"),
                ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType(I18n.CONTROL.getString("messageBox.cancelButton"),
                ButtonData.CANCEL_CLOSE);
        return showConfirmation(deleteButtonType, cancelButtonType);
    }

    /**
     * Shows confirmation message box with exit and cancel actions.
     *
     * @return An {@link Optional} that contains the clicked {@link ButtonType result}.
     */
    public Optional<ButtonType> showExitConfirmation(){
        ButtonType exitButtonType = new ButtonType(I18n.CONTROL.getString("messageBox.exitButton"),
                ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType(I18n.CONTROL.getString("messageBox.cancelButton"),
                ButtonData.CANCEL_CLOSE);
        return showConfirmation(exitButtonType, cancelButtonType);
    }

    /**
     * Shows confirmation message box with yes, no, cancel actions.
     *
     * @return An {@link Optional} that contains the clicked {@link ButtonType result}.
     */
    public Optional<ButtonType> showConfirmation(){
        return showConfirmation(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
    }

    /**
     * Shows confirmation message box with specified actions.
     *
     * @param actions The confirmation dialog actions {@link ButtonType}.
     * @return An {@link Optional} that contains the clicked {@link ButtonType result}.
     */
    public Optional<ButtonType> showConfirmation(ButtonType... actions){
        this.type = Alert.AlertType.CONFIRMATION;
        this.actions.clear();
        this.actions.addAll(Arrays.asList(actions));
        return confirmation();
    }

    /**
     * Shows error message box.
     */
    public void showError(){
        this.type = Alert.AlertType.ERROR;
        show();
    }

    /**
     * Shows error message box with exception.
     *
     * @param exception {@link Throwable exception}
     */
    public void showError(Throwable exception){
        this.type = Alert.AlertType.ERROR;
        this.exception = exception;
        show();
    }

    private void show() {
        Alert alert = createAlert();

        if (type == Alert.AlertType.ERROR && exception != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exception.printStackTrace(pw);
            String exceptionText = sw.toString();
            pw.close();

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setVisible(false);
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(textArea, 0, 0);

            alert.getDialogPane().setExpandableContent(expContent);
        }

        alert.showAndWait();
    }

    private Optional<ButtonType> confirmation() {
        Alert alert = createAlert();
        alert.getButtonTypes().setAll(actions);
        return alert.showAndWait();
    }

    private Alert createAlert() {
        Alert alert = new Alert(type);

        if (StringUtils.isEmpty(title)) {
            switch (type) {
                case NONE: {
                    break;
                }
                case INFORMATION: {
                    title = I18n.CONTROL.getString("messageBox.information");
                    break;
                }
                case WARNING: {
                    title = I18n.CONTROL.getString("messageBox.warning");
                    break;
                }
                case ERROR: {
                    title = I18n.CONTROL.getString("messageBox.error");
                    break;
                }
                case CONFIRMATION: {
                    title = I18n.CONTROL.getString("messageBox.confirmation");
                    break;
                }
            }
        }

        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

}