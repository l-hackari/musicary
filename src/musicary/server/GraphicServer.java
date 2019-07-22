package musicary.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import musicary.model.server.Server;

import java.io.File;
import java.io.IOException;

public class GraphicServer extends Application {

    @Override
    public void start(Stage primaryStage) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLLoader serverLoader = new FXMLLoader(getClass().getResource(".." + File.separator +
                "graphics" + File.separator + "scenes" + File.separator + "server.fxml"));

        TabPane root = null;
        try {
            root = (TabPane) serverLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Musicary GraphicServer");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}