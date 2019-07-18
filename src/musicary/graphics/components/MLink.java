package musicary.graphics.components;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MLink extends Label {

    public MLink(){
        setAppearance();
        setMouseEventProperties();
    }

    private void setMouseEventProperties(){
        this.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUnderline(true);
            }
        });

        this.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUnderline(false);
            }
        });
    }

    private void setAppearance(){
        this.setCursor(Cursor.HAND);
        this.setTextFill(Color.WHITE);
        this.setFont(Font.font("System", FontWeight.BOLD, 15.0));
    }
}
