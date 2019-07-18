package musicary.graphics.panes;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import musicary.graphics.components.MSong;

import java.util.ArrayList;

public class MAlbum extends TitledPane {

    private String albumName;
    private int releaseDate;
    private VBox vBox;

    public MAlbum(String albumName, int releaseDate, ArrayList<MSong> albumSongs){
        vBox = new VBox();
        setTitle(albumName, releaseDate);
        putSongs(albumSongs);
    }

    private void setTitle(String albumName, int releaseDate){
        this.setText(albumName + " - " + releaseDate);
    }

    private void putSongs(ArrayList<MSong> albumSongs){
        vBox.getChildren().add(new MSongListHeader());
        for (int i = 0; i < albumSongs.size(); i++) {
            vBox.getChildren().add(albumSongs.get(i));
        }

        this.setContent(vBox);
    }
}
