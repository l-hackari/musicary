package musicary.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerGenreController {

    private Stage stage;

    @FXML
    private ComboBox genresList;
    @FXML
    private VBox editGenreForm;

    public void setStage(Stage stage) {
        this.stage = stage;
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
    }

    @FXML
    private void checkNewGenre(MouseEvent mouseEvent) {
    }

    @FXML
    private void checkEditGenre(MouseEvent mouseEvent) {
    }
}
