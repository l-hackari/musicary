package musicary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import musicary.graphics.components.*;
import musicary.model.User;
import musicary.model.client.RequestManager;

import java.io.IOException;
import java.math.BigInteger;
import java.net.UnknownServiceException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileController {

    private RequestManager requestManager;
    private User loggedUser;

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
        } else {
            try {
                requestManager.sendRequest("changePassword");

                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(StandardCharsets.UTF_8.encode(passwordField.getTextFieldValue()));

                User user = new User();
                user.setUsername(loggedUser.getUsername());
                user.setPassword(String.format("%032x", new BigInteger(1, md5.digest())));

                if(requestManager.changePassword(user)){
                    MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                    mAlert.setText("Password cambiata!");
                    mAlert.show();
                } else {
                    MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                    mAlert.setText("Password inserita non valida!");
                    mAlert.show();
                }

            } catch (IOException e) {
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Connessione con il server interrotta!");
                mAlert.show();
            } catch (ClassNotFoundException e) {
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Errore durante l'invio dei dati!");
                mAlert.show();
            } catch (NoSuchAlgorithmException e) {
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Algoritmo di crittografia non esistente!");
                mAlert.show();
            }

        }

    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }
}
