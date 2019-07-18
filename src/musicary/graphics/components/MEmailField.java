package musicary.graphics.components;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;

public class MEmailField extends BorderPane {

    private MTextField textField;
    private ImageView imageView;

    public MEmailField() {
        textField = new MTextField();
        imageView = new ImageView(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +  "components" + File.separator +
                "images" + File.separator + "email.png"),
                24.0, 24.0, true, ImageView.SMOOTH_DEFAULT));
        this.setLeft(imageView);
        this.setCenter(textField);
        setImageViewProperties();
        setTextFieldProperties();
    }

    private void setImageViewProperties(){
        MUsernameField.setMargin(imageView, new Insets(5.0));
    }

    private void setTextFieldProperties(){
        textField.setPromptText("Email");
    }
}
