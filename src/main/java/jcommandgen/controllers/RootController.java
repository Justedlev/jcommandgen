package jcommandgen.controllers;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import jcommandgen.api.lang.AppLanguage;
import jcommandgen.config.DefaultRamValuesProperties;
import jcommandgen.config.LanguageProperties;
import jcommandgen.api.ReturnCode;
import jcommandgen.service.IJustCmdGen;
import jcommandgen.service.JustCmdGenService;
import jcommandgen.controllers.animation.Shake;

import java.io.File;
import java.io.IOException;

import static jcommandgen.api.Constants.*;
import static jcommandgen.api.ReturnCode.*;

public class RootController {

    public static final String EULA_EQUAL_TRUE = "eula=true";
    public static final String EULA_EQUAL_FALSE = "eula=false";
    public static final String BACKGROUND_GREEN = "background-color-green";
    public static final String BACKGROUND_RED = "background-color-red";
    public static final String NOT_SELECTED = "not-selected";
    public static final String SELECTED = "selected";
    public static final String EULA_AGREEMENT_NOT_ACCEPTED = "EULA agreement not accepted";
    public static final String EULA_AGREEMENT_ACCEPTED = "EULA agreement accepted";

    private final FileChooser fileChooser = new FileChooser();
    private AppLanguage lang;
    private DefaultRamValuesProperties drvp;
    private IJustCmdGen service;
    private Shake buttonShakeAnimation;

    @FXML
    private Text appMessage;

    @FXML
    private AnchorPane rootPanel;

    @FXML
    private Button infoButton;

    @FXML
    private AnchorPane generatorPanel;

    @FXML
    private Text minRamTextField;

    @FXML
    private Text minStorageUnit;

    @FXML
    private Button minRamIncrementButton;

    @FXML
    private Button minRamDecrementButton;

    @FXML
    private TextField minRamValue;

    @FXML
    private Text maxRamTextField;

    @FXML
    private Text maxStorageUnit;

    @FXML
    private Button maxRamIncrementButton;

    @FXML
    private Button maxRamDecrementButton;

    @FXML
    private TextField maxRamValue;

    @FXML
    private CheckBox isGui;

    @FXML
    private Tooltip withGuiHint;

    @FXML
    private CheckBox isIgnore20Sec;

    @FXML
    private Tooltip ignore20SecHint;

    @FXML
    private Button chooseFileEngineButton;

    @FXML
    private Text chooseFileEngineText;

    @FXML
    private TextField cmdFileName;

    @FXML
    private Button generateButton;

    @FXML
    private Button eulaAgreementButton;

    @FXML
    private Text eulaFileText;

    @FXML
    void initialize() {
        try {
            lang = new LanguageProperties().getLanguage();
            drvp = new DefaultRamValuesProperties();
            if (drvp.getDefaultMinRamValue() <= 0 || drvp.getDefaultMaxRamValue() <= 0) {
                rootPanel.setVisible(false);
                String msg = String.format(lang.getError_msg(),
                        DEFAULT_VALUES_PROPS_FILE_NAME);
                showErrorAlert(msg);
                appMessage.setText(msg);
            } else {
                service = new JustCmdGenService(drvp.getDefaultMinRamValue(), drvp.getDefaultMaxRamValue());
                applyLang(lang);
                minRamValue.setText(service.getObservableMinRam().toString());
                maxRamValue.setText(service.getObservableMinRam().toString());
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("JAR Files", "*.jar")
                );
                buttonShakeAnimation = new Shake(70);
            }
        } catch (Exception e) {
            showErrorAlert(e.getLocalizedMessage());
            appMessage.setText(e.getMessage());
            rootPanel.setVisible(false);
        }
    }

    @FXML
    void minRamDecrementButton() {
        try {
            ReturnCode code = service.minRamDecrement();
            switch (code) {
                case OK: {
                    minRamValue.setText(service.getObservableMinRam().toString());
                    break;
                }
                case MIN_LESS_THAN_DEFAULT_MIN: {
                    showWarningAlert(String.format(lang.getCodes_descriptions().getMin_less_than_default_min(), drvp.getDefaultMinRamValue()));
                    break;
                }
                case MIN_MORE_THAN_DEFAULT_MAX: {
                    showWarningAlert(String.format(lang.getCodes_descriptions().getEntered_min_more_than_default_max(), drvp.getDefaultMaxRamValue()));
                    break;
                }
                default: {
                    showErrorAlert(code.toString());
                    break;
                }
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void minRamIncrementButton() {
        try {
            ReturnCode code = service.minRamIncrement();
            switch (code) {
                case OK: {
                    minRamValue.setText(service.getObservableMinRam().toString());
                    maxRamValue.setText(service.getObservableMaxRam().toString());
                    break;
                }
                case MIN_LESS_THAN_DEFAULT_MIN: {
                    showWarningAlert(String.format(lang.getCodes_descriptions().getMin_less_than_default_min(), drvp.getDefaultMinRamValue()));
                    break;
                }
                case MIN_MORE_THAN_DEFAULT_MAX: {
                    showWarningAlert(String.format(lang.getCodes_descriptions().getMin_more_than_default_max(), drvp.getDefaultMaxRamValue()));
                    break;
                }
                default: {
                    showErrorAlert(code.toString());
                    break;
                }
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void maxRamDecrementButton() {
        try {
            ReturnCode code = service.maxRamDecrement();
            switch (code) {
                case OK: {
                    minRamValue.setText(service.getObservableMinRam().toString());
                    maxRamValue.setText(service.getObservableMaxRam().toString());
                    break;
                }
                case MAX_LESS_THAN_DEFAULT_MIN: {
                    showWarningAlert(String.format(lang.getCodes_descriptions().getMax_less_than_default_max(), drvp.getDefaultMinRamValue()));
                    break;
                }
                default: {
                    showErrorAlert(code.toString());
                    break;
                }
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void maxRamIncrementButton() {
        try {
            ReturnCode code = service.maxRamIncrement();
            switch (code) {
                case OK: {
                    maxRamValue.setText(service.getObservableMaxRam().toString());
                    break;
                }
                case MAX_MORE_THAN_DEFAULT_MAX: {
                    showWarningAlert(String.format(lang.getCodes_descriptions().getMax_more_than_default_max(), drvp.getDefaultMaxRamValue()));
                    break;
                }
                case MAX_LESS_THAN_DEFAULT_MIN: {
                    showWarningAlert(String.format(lang.getCodes_descriptions().getMax_less_than_default_max(), drvp.getDefaultMaxRamValue()));
                    break;
                }
                default: {
                    showErrorAlert(code.toString());
                    break;
                }
            }
        } catch (Exception e) {
            showErrorAlert(e.getMessage());
        }
    }

    @FXML
    void chooseFileEngineButton() {
        File selectedFile = fileChooser.showOpenDialog(rootPanel.getScene().getWindow());
        if (selectedFile != null) {
            if (service.getEngineFile() == null || !service.getEngineFile().equals(selectedFile)) {
                service.setEngineFile(selectedFile);
                service.setEulaFile(new File(String.format("%s\\%s", service.getEngineFileDirectory(), EULA_TXT)));
                chooseFileEngineText.setText(service.getEngineFullFileName());
                chooseFileEngineText.getStyleClass().removeAll(NOT_SELECTED);
                chooseFileEngineText.getStyleClass().add(SELECTED);
                engineFileSelected();
                if (cmdFileName.getText().isEmpty()) {
                    cmdFileName.setText(service.getEngineFileNameWithoutExpansion() + "-" + ENGINE_FILE_NAME);
                }
            }
        } else {
            engineFileNotSelected();
            buttonShakeAnimation.play(chooseFileEngineButton);
        }
    }

    @FXML
    void eulaAgreementButton() {
        if (showConfirmationEulaAgreementAlert()) {
            setEula(EULA_EQUAL_FALSE, EULA_EQUAL_TRUE);
        } else {
            setEula(EULA_EQUAL_TRUE, EULA_EQUAL_FALSE);
        }
    }

    @FXML
    void generateButton() {
        ReturnCode code = service.generateCmd(minRamValue.getText(), maxRamValue.getText(),
                isIgnore20Sec.isSelected(), isGui.isSelected());
        switch (code) {
            case ENGINE_FILE_NOT_SELECTED:
                engineFileNotSelected();
                showWarningAlert(lang.getCodes_descriptions().getEngine_file_not_selected());
                buttonShakeAnimation.play(chooseFileEngineButton);
                break;
            case CMD_FILE_GENERATED:
            case CMD_FILE_NOT_MODIFIED:
            case CMD_FILE_MODIFIED:
                cmdGenerated();
                showInfoAlert(lang.getCodes_descriptions().getFile_generated());
                clear();
                break;
            case CMD_FILE_NOT_GENERATED:
                cmdNotGenerated();
                showWarningAlert(lang.getCodes_descriptions().getFile_not_generated());
                break;
            case ENTERED_MAX_LESS_THAN_DEFAULT_MIN:
            case ENTERED_MAX_MORE_THAN_DEFAULT_MAX:
                showWarningAlert(lang.getCodes_descriptions().getFile_not_generated());
                maxRamValue.setText(service.getObservableMaxRam().toString());
                break;
            case ENTERED_MIN_LESS_THAN_DEFAULT_MIN:
            case ENTERED_MIN_MORE_THAN_DEFAULT_MAX:
                showWarningAlert(lang.getCodes_descriptions().getFile_not_generated());
                minRamValue.setText(service.getObservableMinRam().toString());
                break;
            case INCORRECT_INPUT_NUMBER:
                showErrorAlert(lang.getCodes_descriptions().getIncorrect_input_number());
                break;
            default:
                showInfoAlert(code.toString());
                break;
        }
    }

    private void applyLang(AppLanguage language) {
        infoButton.setOnAction(event -> showInfoAlert(language.getApp_info()));
        chooseFileEngineButton.setText(language.getChose_engine_button());
        eulaAgreementButton.setText(language.getEula_button());
        generateButton.setText(language.getGenerate_button().toUpperCase() + "_>");
        fileChooser.setTitle(language.getSelect_engine() + ":");
        chooseFileEngineText.setText(language.getSelect_engine());
        chooseFileEngineText.setText(language.getEngine_not_selected());
        chooseFileEngineButton.setText(language.getSelect_engine());
        isGui.setText(language.getWith_gui());
        withGuiHint.setText(language.getWith_gui());
        isIgnore20Sec.setText(language.getIgnore_20_seconds());
        ignore20SecHint.setText(language.getIgnore_20_seconds());
        minRamTextField.setText(language.getMin_ram());
        maxRamTextField.setText(language.getMax_ram());
        cmdFileName.setPromptText(language.getCmd_file_name());
    }

    private void setEula(String firstValue, String secondValue) {
        ReturnCode code = service.eulaAgreement(firstValue, secondValue);
        if (code == ENGINE_FILE_NOT_SELECTED) {
            engineFileNotSelected();
            showWarningAlert(lang.getCodes_descriptions().getEngine_file_not_selected());
            buttonShakeAnimation.play(chooseFileEngineButton);
        } else if (code == ACCEPTED || secondValue.equals(EULA_EQUAL_TRUE)) {
            eulaAgreementAccepted();
            showInfoAlert(lang.getCodes_descriptions().getAccepted());
        } else if (code == NOT_ACCEPTED || secondValue.equals(EULA_EQUAL_FALSE)) {
            eulaAgreementNotAccepted();
            showErrorAlert(lang.getCodes_descriptions().getNot_accepted());
            buttonShakeAnimation.play(eulaAgreementButton);
        }
    }

    private void clear() {
        generateButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        chooseFileEngineText.setText(lang.getEngine_not_selected());
        chooseFileEngineButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        eulaAgreementButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        eulaAgreementButton.setText(lang.getEula_button());
        isGui.setSelected(false);
        isIgnore20Sec.setSelected(false);
        minRamValue.setText(service.getDefaultMinRamValue().toString());
        maxRamValue.setText(service.getDefaultMinRamValue().toString());
        chooseFileEngineText.getStyleClass().removeAll(NOT_SELECTED, SELECTED);
        chooseFileEngineText.getStyleClass().add(NOT_SELECTED);
        eulaFileText.setText("");
        cmdFileName.setText("");
        service.clear();
    }

    private void cmdGenerated() {
        generateButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        generateButton.getStyleClass().add(BACKGROUND_GREEN);
    }

    private void cmdNotGenerated() {
        generateButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        generateButton.getStyleClass().add(BACKGROUND_RED);
    }

    private void engineFileSelected() {
        chooseFileEngineButton.setText(lang.getChange_engine());
        chooseFileEngineButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        chooseFileEngineButton.getStyleClass().add(BACKGROUND_GREEN);
    }

    private void engineFileNotSelected() {
        chooseFileEngineButton.getStyleClass().removeAll(BACKGROUND_GREEN, BACKGROUND_RED);
        chooseFileEngineButton.getStyleClass().add(BACKGROUND_RED);
    }

    private void eulaAgreementNotAccepted() {
        eulaAgreementButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        eulaAgreementButton.getStyleClass().add(BACKGROUND_RED);
        eulaAgreementButton.setText(EULA_EQUAL_FALSE);
        eulaFileText.getStyleClass().removeAll(SELECTED);
        eulaFileText.getStyleClass().add(NOT_SELECTED);
        eulaFileText.setText(EULA_AGREEMENT_NOT_ACCEPTED);
    }

    private void eulaAgreementAccepted() {
        eulaAgreementButton.getStyleClass().removeAll(BACKGROUND_RED, BACKGROUND_GREEN);
        eulaAgreementButton.getStyleClass().add(BACKGROUND_GREEN);
        eulaAgreementButton.setText(EULA_EQUAL_TRUE);
        eulaFileText.getStyleClass().removeAll(NOT_SELECTED);
        eulaFileText.getStyleClass().add(SELECTED);
        eulaFileText.setText(EULA_AGREEMENT_ACCEPTED);
    }

    private void showErrorAlert(String explanation) {
        getAlert(Alert.AlertType.ERROR, "Error!",
                explanation).showAndWait();
    }

    private void showWarningAlert(String explanation) {
        getAlert(Alert.AlertType.WARNING, "Warning!",
                explanation).showAndWait();
    }

    private void showInfoAlert(String explanation) {
        getAlert(Alert.AlertType.INFORMATION, "Info!",
                explanation).showAndWait();
    }

    private boolean showConfirmationEulaAgreementAlert() {
        Alert alert = getAlert(Alert.AlertType.CONFIRMATION, "EULA Agreement",
                "Do you accept the EULA agreement?\n" +
                        "More on the website https://account.mojang.com/documents/minecraft_eula");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
        ButtonType button = alert.showAndWait().orElse(ButtonType.CLOSE);
        if (button == ButtonType.YES) {
            return true;
        } else if (button == ButtonType.NO) {
            return false;
        }
        return false;
    }

    private Alert getAlert(Alert.AlertType type, String title, String explanation) {
        Alert alert = new Alert(type);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(explanation);
        return alert;
    }

}