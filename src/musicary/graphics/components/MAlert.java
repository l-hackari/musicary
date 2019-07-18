package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class MAlert extends Alert {

    private DialogPane dialogPane;
    private ButtonType alertButton;

    public MAlert(AlertType alertType) {
        super(alertType);
        loadDialogPane();
        setImage(alertType);
        loadHeaderText(alertType);
        setAppearance();
        setButtonEvents();
        loadButtonType();
        this.setDialogPane(dialogPane);
    }

    private void setImage(AlertType alertType){
        if(alertType == AlertType.ERROR){
            AnchorPane anchorPane = (AnchorPane) dialogPane.getHeader();
            ImageView imageView = (ImageView) anchorPane.getChildren().get(0);
            imageView.setImage(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator +  "components" + File.separator +
                    "images" + File.separator + "alertlogo.png")));
        }
    }

    private void loadHeaderText(AlertType alertType){
        if(alertType == AlertType.ERROR){
            VBox vBox = (VBox) dialogPane.getContent();
            Label label = (Label) vBox.getChildren().get(0);
            label.setText("Errore");
        }
    }

    public void setText(String s){
        VBox vBox = (VBox) dialogPane.getContent();
        Label label = (Label) vBox.getChildren().get(1);
        label.setText(s);
    }

    private void loadDialogPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + File.separator + "scenes" + File.separator + "dialog.fxml"));
        try {
            dialogPane = (DialogPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonEvents(){
        VBox vBox = (VBox) dialogPane.getContent();
        MAlertButton mAlertButton = (MAlertButton) vBox.getChildren().get(2);
        mAlertButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close();
            }
        });
    }

    private void loadButtonType(){
        alertButton = new ButtonType("TestoDiEsempio", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().add(alertButton);
        dialogPane.lookupButton(alertButton).setManaged(false);
    }

    private void setAppearance(){
        this.initStyle(StageStyle.UNDECORATED);
    }
}
