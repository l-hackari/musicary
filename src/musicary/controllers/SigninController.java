package musicary.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import musicary.graphics.components.*;
import musicary.model.User;
import musicary.model.client.Client;
import musicary.model.client.RequestManager;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SigninController {


    private Scene loginScene;
    private Stage stage;
    private Scene mainScene;
    private Scene signinScene;
    private RequestManager requestManager;
    private MainController mainController;


    @FXML
    private MPasswordIconTextField passwordFiled;
    @FXML
    private MBornDateField bornDateFiled;
    @FXML
    private MUsernameField usernameField;
    @FXML
    private MEmailField emailFIeld;

    @FXML
    private void loadLogin(MouseEvent mouseEvent) {
        BorderPane borderPane = (BorderPane) loginScene.getRoot();
        BorderPane signinBorderPane = (BorderPane) signinScene.getRoot();
        borderPane.setPrefWidth(borderPane.getWidth());
        borderPane.setPrefHeight(borderPane.getHeight());
        stage.setScene(loginScene);
    }

    @FXML
    private void checkRegistration(MouseEvent mouseEvent) {

        Client client = null;
        try {
            client = new Client();
            client.connect();

            requestManager = client.getRequestManager();

            BorderPane borderPane = (BorderPane) signinScene.getRoot();
            BorderPane mainBorderPane = (BorderPane) mainScene.getRoot();
            mainBorderPane.setPrefWidth(borderPane.getWidth());
            mainBorderPane.setPrefHeight(borderPane.getHeight());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);

            try {

                Date dateParsered = dateFormat.parse(bornDateFiled.getBornDate());
                java.sql.Date date = new java.sql.Date(dateParsered.getTime());
                User user = new User();
                user.setUsername(usernameField.getTextFieldValue());

                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(StandardCharsets.UTF_8.encode(passwordFiled.getPasswordFieldValue()));

                user.setPassword(String.format("%032x", new BigInteger(1, md5.digest())));
                user.setEmail(emailFIeld.getTextFieldValue());
                user.setBornDate(date);

                try {
                    requestManager.sendRequest("signup");

                    if (!requestManager.SignUp(user)) {
                        MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                        mAlert.setText("Username o email già esistenti");
                        mAlert.show();
                        requestManager.logOut();
                    } else {
                        MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                        mAlert.setText("Registrazione completata!");
                        mAlert.show();
                        mainController.setRequestManager(requestManager, user);
                        stage.setScene(mainScene);
                        stage.setMaximized(true);
                    }

                } catch (IOException e) {
                    MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                    mAlert.setText("Connessione persa con il server!");
                    mAlert.show();
                }
            } catch (ParseException e) {
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Data inserita non corretta");
                mAlert.show();
            } catch (NoSuchAlgorithmException e) {
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Algoritmo di criptazione non presente");
                mAlert.show();
            }

        } catch (IOException e) {
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Errore di connessione con il server");
            mAlert.show();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public void setSigninScene(Scene signinScene) {
        this.signinScene = signinScene;
    }

    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }
}
