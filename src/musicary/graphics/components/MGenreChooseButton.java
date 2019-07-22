package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import musicary.controllers.MainController;

public class MGenreChooseButton extends MGenresButton {

    public static final int ARTIST = 1;
    public static final int SONGS = 2;
    private String genreText;
    private String genreId;
    private int choose;
    public MGenreChooseButton(String text, MainController controller, int choose, String genreText, String
                              genreId) {
        super(text, genreId, controller);
        this.choose = choose;
        this.genreId = genreId;
        this.genreText = genreText;
    }

    @Override
    protected void setMouseEvents(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.loadGenreChoose(choose, genreText, genreId);
            }
        });
    }
}
