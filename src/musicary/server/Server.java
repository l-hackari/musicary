package musicary.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import musicary.controllers.ServerAlbumController;
import musicary.controllers.ServerArtistController;
import musicary.controllers.ServerGenreController;

import java.io.File;

public class Server extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader serverLoader = new FXMLLoader(getClass().getResource(".." + File.separator +
                "graphics" + File.separator + "scenes" + File.separator + "server.fxml"));

        TabPane root = (TabPane) serverLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Musicary Server");
        primaryStage.show();

    }
}