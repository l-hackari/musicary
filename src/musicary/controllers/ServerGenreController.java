package musicary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import musicary.graphics.components.MAlert;
import musicary.graphics.components.MConfirmAlert;
import musicary.graphics.components.MTextWhiteField;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ServerGenreController {

    private Stage stage;
    private File coverImage;
    private File minImage;
    private File newCoverImage;
    private File newMinImage;

    @FXML
    private ComboBox genresList;
    @FXML
    private VBox editGenreForm;
    @FXML
    private MTextWhiteField genreName;
    @FXML
    private MTextWhiteField genreNameSelected;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private File loadFileChooser(String text){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(text);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File Immagine",
                "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(stage);
    }

    @FXML
    private void initialize(){
        genresList.getItems().add(new String("Rock"));
        genresList.getItems().add(new String("EDM"));
        genresList.getItems().add(new String("Indie"));
    }

    @FXML
    private void showGenreInfo(ActionEvent actionEvent) {
        editGenreForm.setVisible(true);
    }

    @FXML
    private void deleteGenre(MouseEvent mouseEvent) {
        MConfirmAlert mAlert = new MConfirmAlert(Alert.AlertType.CONFIRMATION);
        mAlert.setText("Sei sicuro di voler eliminare il genere?");
        mAlert.showAndWait();
        System.out.println(mAlert.getValueSelected());
    }

    @FXML
    private void checkNewGenre(MouseEvent mouseEvent) {
    }

    @FXML
    private void checkEditGenre(MouseEvent mouseEvent) {
    }

    @FXML
    private void loadGenreImage(MouseEvent mouseEvent) {
        minImage = loadFileChooser("Carica immagine di copertina");
        try {
            System.out.println(minImage.getAbsolutePath());

        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Il file selezionato non è valido");
            mAlert.show();
        }
    }

    @FXML
    private void loadGenreCover(MouseEvent mouseEvent) {
        coverImage = loadFileChooser("Carica immagine di copertina");
        try {
            Image image = new Image(coverImage.toURI().toString());
            if(image.getWidth() != 256 || image.getHeight() != 256){
                MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
                mAlert.setText("Il file selezionato non è valido");
                mAlert.show();
            }
        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Le dimensioni del file non rispettano i requisiti");
            mAlert.show();
        }
    }

    @FXML
    private void loadNewGenreImage(MouseEvent mouseEvent) {
        newMinImage = loadFileChooser("Carica immagine di copertina");
        try {
            Image image = new Image(newMinImage.getAbsolutePath());
        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Il file selezionato non è valido");
            mAlert.show();
        }
    }

    @FXML
    private void loadNewGenreCover(MouseEvent mouseEvent) {
        newCoverImage = loadFileChooser("Carica immagine di copertina");
        try {
            Image image = new Image(newCoverImage.getAbsolutePath());
            System.out.println(image.getWidth());
        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Il file selezionato non è valido");
            mAlert.show();
        }
    }
}
