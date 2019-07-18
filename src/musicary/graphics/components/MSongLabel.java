package musicary.graphics.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MSongLabel extends Label {

    public MSongLabel(String text){
        super(text);
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0.0), new
                Insets(0.0))));
        this.setTextFill(Color.web("#000"));
        this.setFont(Font.font("System", FontWeight.THIN, 12.0));
    }
}
