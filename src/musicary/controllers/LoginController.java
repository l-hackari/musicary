package musicary.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import musicary.graphics.components.MAlert;
import musicary.graphics.components.MPasswordIconTextField;
import musicary.graphics.components.MUsernameField;
import musicary.model.User;
import musicary.model.client.Client;
import musicary.model.client.RequestManager;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {

    @FXML
    private MUsernameField username;
    @FXML
    private MPasswordIconTextField password;

    private Scene mainScene;
    private Stage stage;
    private Scene signinScene;
    private Scene loginScene;

    private RequestManager requestManager;
    private MainController mainController;

    @FXML
    private void checkLogin(MouseEvent mouseEvent) {

        Client client = null;
        try {
            client = new Client();
            client.connect();

            requestManager = client.getRequestManager();

            BorderPane borderPane = (BorderPane) loginScene.getRoot();
            BorderPane mainBorderPane = (BorderPane) mainScene.getRoot();
            mainBorderPane.setPrefWidth(borderPane.getWidth());
            mainBorderPane.setPrefHeight(borderPane.getHeight());

            User user = new User();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(password.getPasswordFieldValue()));
            user.setPassword(String.format("%032x", new BigInteger(1, md5.digest())));
            user.setUsername(username.getTextFieldValue());

            try {
                requestManager.sendRequest("login");

                if (!requestManager.Login(user)) {
                    MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                    mAlert.setText("Username o password errati!");
                    mAlert.show();
                    requestManager.logOut();
                } else {

                    mainController.setRequestManager(requestManager, requestManager.getLoggedUser());
                    stage.setScene(mainScene);
                    stage.setMaximized(true);
                }

            } catch (IOException e) {
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Connessione persa con il server!");
                mAlert.show();
            } catch (ClassNotFoundException e) {
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Errore nell'invio dei dati!");
                mAlert.show();
            }

        } catch (IOException e){

            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Errore di connessione con il server");
            mAlert.show();
        } catch (NoSuchAlgorithmException e) {
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Algoritmo di criptazione non presente");
            mAlert.show();
        }


    }

    @FXML
    private void loadSignin(MouseEvent mouseEvent) {
        BorderPane borderPane = (BorderPane) loginScene.getRoot();
        BorderPane signinBorderPane = (BorderPane) signinScene.getRoot();
        signinBorderPane.setPrefWidth(borderPane.getWidth());
        signinBorderPane.setPrefHeight(borderPane.getHeight());
        stage.setScene(signinScene);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setSigninScene(Scene signinScene) {
        this.signinScene = signinScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }
}
