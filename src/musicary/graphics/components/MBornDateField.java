package musicary.graphics.components;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;

public class MBornDateField extends BorderPane {

    private MTextField textField;
    private ImageView imageView;

    public MBornDateField() {
        textField = new MTextField();
        imageView = new ImageView(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator +  "components" + File.separator +
                        "images" + File.separator + "borndate.png"), 24.0,
                24.0, true, ImageView.SMOOTH_DEFAULT));
        this.setLeft(imageView);
        this.setCenter(textField);
        setImageViewProperties();
        setTextFieldProperties();
    }

    public String getBornDate(){ return textField.getText(); }

    private void setImageViewProperties(){
        MUsernameField.setMargin(imageView, new Insets(5.0));
    }

    private void setTextFieldProperties(){
        textField.setPromptText("Data di nascita (GG/MM/AAAA)");
    }
}
