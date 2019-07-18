package musicary.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import musicary.graphics.components.MPasswordIconTextField;
import musicary.graphics.components.MUsernameField;

public class LoginController {
    @FXML
    private MUsernameField username;
    @FXML
    private MPasswordIconTextField password;

    private Scene mainScene;
    private Stage stage;
    private Scene signinScene;
    private Scene loginScene;

    @FXML
    private void checkLogin(MouseEvent mouseEvent) {
        BorderPane borderPane = (BorderPane) loginScene.getRoot();
        BorderPane mainBorderPane = (BorderPane) mainScene.getRoot();
        mainBorderPane.setPrefWidth(borderPane.getWidth());
        mainBorderPane.setPrefHeight(borderPane.getHeight());
        stage.setScene(mainScene);
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
}
