package Controllers;

import Classes.AES;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class AESDecryptionInterfaceController {

    @FXML
    private TextArea plainTextTextArea;
    @FXML
    private TextArea encryptedTextTextArea;
    @FXML
    private HBox ivHBox;
    @FXML
    private ComboBox chooseAlgorithmComboBox;
    @FXML
    private TextField keyTextField;
    @FXML
    private TextField ivTextField;

    Alert alert;
    private AES aes;
    private ArrayList<String> availableAlgorithms;

    public AESDecryptionInterfaceController() {
        alert = new Alert(Alert.AlertType.ERROR);
        aes = new AES();
        availableAlgorithms = new ArrayList<String>();
        addAlgorithms();
    }

    @FXML
    void initialize() {
        plainTextTextArea.setEditable(false);
        plainTextTextArea.setWrapText(true);
        encryptedTextTextArea.setWrapText(true);
        disableNode(ivHBox);
        chooseAlgorithmComboBox.getItems().addAll(availableAlgorithms);
    }

    private void addAlgorithms() {
        availableAlgorithms.add("ECB");
        availableAlgorithms.add("CBC");
        availableAlgorithms.add("CFB");
        availableAlgorithms.add("OFB");
    }

    private void disableNode(Node node) {
        node.setVisible(false);
        node.setDisable(true);
    }

    private void enableNode(Node node) {
        node.setVisible(true);
        node.setDisable(false);
    }

    public void backButtonPressed(ActionEvent actionEvent) throws IOException {
        Parent newRoot = FXMLLoader.load(getClass().getResource("/FXMLFiles/ChooseDecryptionMethod.fxml"));

        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(newRoot);
        scene.getStylesheets().add("CSS/style.css");
        stageTheEventSourceNodeBelongs.setScene(scene);
        stageTheEventSourceNodeBelongs.setResizable(false);
    }

    public void keyTextFieldFilled(ActionEvent actionEvent) {
    }

    public void runButtonPressed(ActionEvent actionEvent) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (!encryptedTextTextArea.getText().equals("") && !keyTextField.getText().equals("") && aes.checkKey(keyTextField.getText())) {
            aes.setKeyString(keyTextField.getText());
            aes.setInputString(encryptedTextTextArea.getText());
            if (aes.getAlgorithm() != "ECB") {
                if (!ivTextField.getText().equals("") && aes.checkIv(ivTextField.getText())) {
                    aes.setIv(ivTextField.getText());
                } else {
                    alert.setTitle("Input Error");
                    alert.setContentText("Nieprawid??owy wektor inicjalizacyjny");
                    alert.showAndWait();
                }
            }
            aes.decrypt();
            plainTextTextArea.setText(aes.getOutputString());
        } else {
            alert.setTitle("Input Error");
            alert.setContentText("Nieprawid??owy klucz b??dz szyfrogram");
            alert.showAndWait();
        }
    }

    public void saveButtonPressed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("pliki TXT", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                FileWriter myWriter = new FileWriter(file.getName());
                myWriter.write(aes.getOutput());
                myWriter.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void ivTextFieldFilled(ActionEvent actionEvent) {
    }

    public void chooseAlgorithmComboBoxOnAction(ActionEvent actionEvent) {
        aes.setAlgorithm(chooseAlgorithmComboBox.getValue().toString());
        if (aes.getAlgorithm() != "ECB") enableNode(ivHBox);
        else disableNode(ivHBox);
    }
}
