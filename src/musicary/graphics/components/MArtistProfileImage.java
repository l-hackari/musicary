package musicary.graphics.components;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.io.File;

public class MArtistProfileImage extends ImageView {

    public MArtistProfileImage(){
        Circle circle = new Circle(75, 75, 75);
        this.setClip(circle);
    }

    public MArtistProfileImage(Image image){
        this.setImage(image);
        Circle circle = new Circle(75, 75, 75);
        this.setClip(circle);
    }
}
