package musicary.graphics.components;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MTextField extends TextField {

    public MTextField() {
        setAppearance();
        setFocusProperties();
    }

    private void setFocusProperties() {
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    setBorder(new Border(new BorderStroke(Color.web("#ff004e"), Color.web("#ff004e"),
                            Color.web("#fff"), Color.web("#ff004e"), BorderStrokeStyle.SOLID,
                            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                            new CornerRadii(0.0), BorderStroke.THIN, new Insets(0.0))));
                    setStyle("-fx-text-fill: white;-fx-text-inner-color: white; -fx-prompt-text-fill: white;-fx-highlight-text-fill: white");
                } else {
                    setBorder(new Border(new BorderStroke(Color.web("#ff004e"), Color.web("#ff004e"), Color.web("#cccccc"),
                            Color.web("#ff004e"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                            BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.THIN,
                            new Insets(0.0))));
                    setStyle("-fx-text-fill: #cccccc;-fx-text-inner-color: #cccccc; -fx-prompt-text-fill: #cccccc;");
                }
            }
        });
    }

    private void setAppearance(){
        this.setFont(new Font(15));
        this.setBackground(new Background(new BackgroundFill(Color.web("#ff004e"), new CornerRadii(0.0),
                new Insets(0.0))));
        this.setStyle("-fx-text-inner-color: #cccccc; -fx-prompt-text-fill: #cccccc");
        this.setBorder(new Border(new BorderStroke(Color.web("#ff004e"), Color.web("#ff004e"),
                Color.web("#cccccc"), Color.web("#ff004e"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.THIN,
                new Insets(0.0))));
    }
}
