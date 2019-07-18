package musicary.graphics.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MHomeText extends Label {

    public MHomeText(String text){
        setAppearance(text);
    }

    private void setAppearance(String text){
        this.setPrefSize(128, 50);
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.web("#fff"),
                new CornerRadii(0.0), new Insets(0.0))));
        this.setTextFill(Color.web("#ff004e"));
        this.setFont(Font.font("System", FontWeight.THIN, 14.0));
        this.setText(text);
    }
}
