package musicary.graphics.components;


import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class MPasswordIconTextField extends BorderPane {

    private MPasswordField passwordField;
    private ImageView imageView;

    public MPasswordIconTextField() {
        passwordField = new MPasswordField();
        imageView = new ImageView(new Image(getClass().getResourceAsStream(".." + File.separator + ".." + File.separator +
                ".." + File.separator + ".." + File.separator + "res" + File.separator + "components" + File.separator +
                "images" + File.separator + "password.png"),
                24.0, 24.0, true, ImageView.SMOOTH_DEFAULT));
        this.setLeft(imageView);
        this.setCenter(passwordField);
        setImageViewProperties();
        setPasswordFieldProperties();
    }

    private void setPasswordFieldProperties() {
        passwordField.setPromptText("Password");
    }

    private void setImageViewProperties() {
        MPasswordIconTextField.setMargin(imageView, new Insets(5.0));
    }

    public String getPasswordFieldValue() {
        return  passwordField.getText();
    }
}
