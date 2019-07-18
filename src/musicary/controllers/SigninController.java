package musicary.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SigninController {

    private Scene loginScene;
    private Stage stage;
    private Scene mainScene;
    private Scene signinScene;

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
        BorderPane borderPane = (BorderPane) signinScene.getRoot();
        BorderPane mainBorderPane = (BorderPane) mainScene.getRoot();
        mainBorderPane.setPrefWidth(borderPane.getWidth());
        mainBorderPane.setPrefHeight(borderPane.getHeight());
        stage.setScene(mainScene);
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
}
