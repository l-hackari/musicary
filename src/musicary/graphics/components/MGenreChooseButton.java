package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import musicary.controllers.MainController;

public class MGenreChooseButton extends MGenresButton {

    public static final int ARTIST = 1;
    public static final int SONGS = 2;
    private String genre;
    private int choose;
    public MGenreChooseButton(String text, String imagePath, MainController controller, int choose, String genre) {
        super(text, imagePath, controller);
        this.choose = choose;
        this.genre = genre;
    }

    @Override
    protected void setMouseEvents(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.loadGenreChoose(choose, genre);
            }
        });
    }
}
