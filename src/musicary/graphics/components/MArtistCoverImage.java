package musicary.graphics.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class MArtistCoverImage extends ImageView {

    public MArtistCoverImage() {
        this.setImage(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +
                "images" + File.separator +
                "rhcp.jpg"), 600.0, 600.0, true, ImageView.SMOOTH_DEFAULT));
    }
}
