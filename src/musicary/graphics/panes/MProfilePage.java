package musicary.graphics.panes;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import musicary.controllers.MainController;

import java.io.File;
import java.io.IOException;

public class MProfilePage extends VBox {

    private VBox content;
    private MainController mainController;

    public MProfilePage(MainController mainController){
        this.mainController = mainController;
        loadContent();
    }

    private void loadContent(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + File.separator + "scenes" +
                File.separator + "userpage.fxml"));
        try {
            content = (VBox) loader.load();
            mainController.setProfileController(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.getChildren().add(content);
    }
}
