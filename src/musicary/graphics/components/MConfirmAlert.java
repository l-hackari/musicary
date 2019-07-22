package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class MConfirmAlert extends Alert {

    private DialogPane dialogPane;
    private ButtonType alertButton;
    private int valueSelected;
    public static final int YES_VALUE = 1;
    public static final int NO_VALUE = 0;

    public MConfirmAlert(Alert.AlertType alertType) {
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
        if(alertType == AlertType.CONFIRMATION){
            AnchorPane anchorPane = (AnchorPane) dialogPane.getHeader();
            ImageView imageView = (ImageView) anchorPane.getChildren().get(0);
            imageView.setImage(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                    ".." + File.separator + ".." + File.separator + "res" + File.separator +  "components" + File.separator +
                    "images" + File.separator + "alertlogo.png")));
        }
    }

    private void loadHeaderText(AlertType alertType){
        if(alertType == AlertType.CONFIRMATION){
            VBox vBox = (VBox) dialogPane.getContent();
            Label label = (Label) vBox.getChildren().get(0);
            label.setText("Attenzione");
        }
    }

    public void setText(String s){
        VBox vBox = (VBox) dialogPane.getContent();
        Label label = (Label) vBox.getChildren().get(1);
        label.setText(s);
    }

    private void loadDialogPane(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(".." + File.separator + "scenes" +
                File.separator + "confirmdialog.fxml"));
        try {
            dialogPane = (DialogPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonEvents(){
        VBox vBox = (VBox) dialogPane.getContent();
        HBox hBox = (HBox) vBox.getChildren().get(2);
        MButtonVariant yesButton = (MButtonVariant) hBox.getChildren().get(0);
        MButtonVariant noButton = (MButtonVariant) hBox.getChildren().get(1);
        yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                valueSelected = YES_VALUE;
                close();
            }
        });

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                valueSelected = NO_VALUE;
                close();
            }
        });
    }

    public int getValueSelected(){
        return valueSelected;
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
