package musicary.graphics.panes;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.IOException;

public class MServerGenreTab extends VBox {

    public MServerGenreTab (){
        FXMLLoader serverLoader = new FXMLLoader(getClass().getResource(".." + File.separator +
                "scenes" + File.separator + "servergenre.fxml"));

        try {
            ScrollPane root = (ScrollPane) serverLoader.load();
            this.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
