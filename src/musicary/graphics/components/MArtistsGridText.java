package musicary.graphics.components;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MArtistsGridText extends Label {

    public MArtistsGridText(String text){
        setAppearance(text);
    }

    private void setAppearance(String text){
        this.setPrefWidth(128);
        this.setMaxHeight(Double.MAX_VALUE);
        this.setWrapText(true);
        this.setTextAlignment(TextAlignment.CENTER);
        this.setAlignment(Pos.CENTER);
        this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, new CornerRadii(0.0), new Insets(
                0.0))));
        this.setTextFill(Color.web("#636363"));
        this.setFont(Font.font("System", FontWeight.BOLD, 13.0));
        this.setText(text);
    }

    public void setNormalText(){
        setTextFill(Color.web("#636363"));
    }

    public void setAlternativeText(){
        setTextFill(Color.web("#ff004e"));
    }
}
