package musicary.graphics.components;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MPasswordWhiteField extends PasswordField {

    public MPasswordWhiteField(){
        setAppearance();
        setFocusProperties();
    }

    private void setFocusProperties(){
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    setBorder(new Border(new BorderStroke(Color.web("#f5f5f5"), Color.web("#f5f5f5"),
                            Color.web("#ff004e"), Color.web("#f5f5f5"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.THIN,
                            new Insets(0.0))));
                    setStyle("-fx-text-fill: #ff004e;-fx-text-inner-color: #ff004e; -fx-prompt-text-fill: ff004e;" +
                            "-fx-highlight-text-fill: #ff004e");
                } else {
                    setBorder(new Border(new BorderStroke(Color.web("#f5f5f5"), Color.web("#f5f5f5"),
                            Color.web("#cccccc"), Color.web("#f5f5f5"), BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID,
                            BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0), BorderStroke.THIN,
                            new Insets(0.0))));
                    setStyle("-fx-text-fill: #cccccc;-fx-text-inner-color: #cccccc; -fx-prompt-text-fill: #cccccc;");
                }
            }
        });
    }

    private void setAppearance(){
        this.setFont(new Font(15));
        this.setBackground(new Background(new BackgroundFill(Color.web("#f5f5f5"),
                new CornerRadii(0.0), new Insets(0.0))));
        this.setStyle("-fx-text-fill: #cccccc;-fx-text-inner-color: #cccccc; -fx-prompt-text-fill: #cccccc;");
        this.setBorder(new Border(new BorderStroke(Color.web("#f5f5f5"),
                Color.web("#f5f5f5"), Color.web("#cccccc"), Color.web("#f5f5f5"), BorderStrokeStyle.SOLID,
                BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0.0),
                BorderStroke.THIN, new Insets(0.0))));
    }
}
