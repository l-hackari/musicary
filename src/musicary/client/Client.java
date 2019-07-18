package musicary.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import musicary.controllers.LoginController;
import musicary.controllers.MainController;
import musicary.controllers.SigninController;

import java.io.File;

public class Client extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainSectionLoader = new FXMLLoader(getClass().getResource(".." + File.separator +
                        "graphics" + File.separator + "scenes" + File.separator + "mainSection.fxml"));
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource(".." + File.separator +
                "graphics" + File.separator + "scenes" + File.separator +"login.fxml"));
        FXMLLoader signinLoader = new FXMLLoader(getClass().getResource(".." + File.separator +
                "graphics" + File.separator + "scenes" + File.separator +"signin.fxml"));


        BorderPane mainSectionroot = (BorderPane) mainSectionLoader.load();
        BorderPane loginRoot = (BorderPane) loginLoader.load();
        BorderPane signinRoot = (BorderPane) signinLoader.load();

        LoginController loginController = loginLoader.getController();
        MainController mainController = mainSectionLoader.getController();
        SigninController signinController = signinLoader.getController();

        Scene mainScene = new Scene(mainSectionroot);
        Scene loginScene = new Scene(loginRoot);
        Scene signinScene = new Scene(signinRoot);

        mainController.setScene(mainScene);
        mainController.setLoginScene(loginScene);
        mainController.setStage(primaryStage);

        loginController.setMainScene(mainScene);
        loginController.setSigninScene(signinScene);
        loginController.setStage(primaryStage);
        loginController.setLoginScene(loginScene);

        signinController.setLoginScene(loginScene);
        signinController.setMainScene(mainScene);
        signinController.setStage(primaryStage);
        signinController.setSigninScene(signinScene);

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Musicary");
        primaryStage.show();
    }
}
