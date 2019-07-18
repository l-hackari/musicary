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

public class ServerArtistController {

    private File coverImage;
    private File profileImage;
    private File selectedArtistCoverImage;
    private File selectedArtistProfileImage;
    private Stage stage;


    @FXML
    private MTextWhiteField artistName;
    @FXML
    private ComboBox artistList;
    @FXML
    private VBox editArtistForm;
    @FXML
    private MTextWhiteField artistNameSelected;

    private File loadFileChooser(String text){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(text);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File Immagine",
                "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(stage);
    }

    @FXML
    private void initialize(){
        artistList.getItems().add(new String("Red Hot Chili Peppers"));
        artistList.getItems().add(new String("Muse"));
        artistList.getItems().add(new String("Arctic Monkeys"));
    }

    @FXML
    private void showArtistInfos(ActionEvent actionEvent) {
        try {
            editArtistForm.setVisible(true);


        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Nessun artista selezionato");
            mAlert.show();
        }
    }

    @FXML
    private void loadProfileImage(MouseEvent mouseEvent) {
        profileImage = loadFileChooser("Carica immagine di profilo");
        try {
            System.out.println(profileImage.getAbsolutePath());
        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Il file selezionato non è valido");
            mAlert.show();
        }
    }

    @FXML
    private void loadCoverImage(MouseEvent mouseEvent) {
        coverImage = loadFileChooser("Carica immagine di copertina");
        try {
            System.out.println(coverImage.getAbsolutePath());
        } catch (NullPointerException e){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Il file selezionato non è valido");
            mAlert.show();
        }
    }

    @FXML
    private void checkArtist(MouseEvent mouseEvent) {
        if(coverImage == null || profileImage == null || artistName.getText().isEmpty()){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Non hai riempito tutti i campi");
            mAlert.show();
        }
    }

    @FXML
    private void loadProfileImageArtistSelected(MouseEvent mouseEvent) {
        selectedArtistProfileImage = loadFileChooser("Carica immagine di profilo");
    }

    @FXML
    private void loadCoverImageArtistSelected(MouseEvent mouseEvent) {
        selectedArtistCoverImage = loadFileChooser("Carica immagine di copertina");
    }

    @FXML
    private void checkNewArtist(MouseEvent mouseEvent) {
        if(artistNameSelected.getText().isEmpty()){
            MAlert mAlert = new MAlert(Alert.AlertType.ERROR);
            mAlert.setText("Non hai riempito tutti i campi");
            mAlert.show();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void deleteArtist(MouseEvent mouseEvent) {
    }
}
