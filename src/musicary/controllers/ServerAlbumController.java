package musicary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import musicary.graphics.components.MAlert;
import musicary.graphics.components.MTextWhiteField;

import java.io.File;

public class ServerAlbumController {
    @FXML
    private ComboBox artistEditSelectionList;
    @FXML
    private ComboBox albumArtistSelectedList;
    @FXML
    private ComboBox artistList;
    @FXML
    private VBox editAlbumForm;
    @FXML
    private MTextWhiteField editAlbumNameTextField;
    @FXML
    private MTextWhiteField editReleaseDateTextField;
    @FXML
    private MTextWhiteField artistNameTextField;
    @FXML
    private MTextWhiteField albumReleaseDateTextField;

    private File editedAlbumCover;
    private File albumCover;
    private Stage stage;


    private File loadFileChooser(String text){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(text);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File Immagine",
                "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(stage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        artistEditSelectionList.getItems().add(new String("Red Hot Chili Peppers"));
        artistEditSelectionList.getItems().add(new String("Muse"));
        artistEditSelectionList.getItems().add(new String("Arctic Monkeys"));
        artistList.getItems().add(new String("Red Hot Chili Peppers"));
        artistList.getItems().add(new String("Muse"));
        artistList.getItems().add(new String("Arctic Monkeys"));

    }

    @FXML
    private void showAlbumArtistSelectedList(ActionEvent actionEvent) {
        albumArtistSelectedList.getItems().clear();
        albumArtistSelectedList.getItems().add(new String("Californication"));
        albumArtistSelectedList.getItems().add(new String("By The Way"));
        albumArtistSelectedList.getItems().add(new String("Dani California"));
    }

    @FXML
    private void loadCoverEditAlbum(MouseEvent mouseEvent) {
        editedAlbumCover = loadFileChooser("Carica immagine di copertina");
        try {
            System.out.println(editedAlbumCover.getAbsolutePath());
        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Il file selezionato non è valido");
            mAlert.show();
        }
    }

    @FXML
    private void checkEditAlbum(MouseEvent mouseEvent) {
    }

    @FXML
    private void loadCoverAlbum(MouseEvent mouseEvent) {
        albumCover = loadFileChooser("Carica immagine di copertina");
        try {
            System.out.println(editedAlbumCover.getAbsolutePath());
        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Il file selezionato non è valido");
            mAlert.show();
        }
    }

    @FXML
    private void checkAlbum(MouseEvent mouseEvent) {
    }

    @FXML
    private void showEditAlbumInfos(ActionEvent actionEvent) {
        editAlbumForm.setVisible(true);
    }

    @FXML
    private void deleteAlbum(MouseEvent mouseEvent) {
    }
}

    
