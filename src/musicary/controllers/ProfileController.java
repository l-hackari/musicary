package musicary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import musicary.graphics.components.*;

public class ProfileController {

    @FXML
    private MPasswordIconTextFieldWhite passwordField;
    @FXML
    private MPasswordIconTextFieldWhite newPasswordField;
    @FXML
    private void checkPassword(MouseEvent mouseEvent) {
        MPasswordWhiteField actualPassword = (MPasswordWhiteField) passwordField.getCenter();
        MPasswordWhiteField newPassword = (MPasswordWhiteField) newPasswordField.getCenter();
        if(!actualPassword.getText().equals(newPassword.getText())){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Le password inserite non coincidono!");
            mAlert.show();
        }
    }
}
