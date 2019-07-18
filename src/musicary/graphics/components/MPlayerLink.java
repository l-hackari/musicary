package musicary.graphics.components;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MPlayerLink extends MLink {

    public MPlayerLink() {
        setAppearance();
    }

    private void setAppearance(){
        this.setTextFill(Color.BLACK);
        this.setFont(Font.font("System", FontWeight.THIN, 14.0));
    }
}
