package musicary.graphics.panes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class MMainSection extends BorderPane {

    private Pane container;
    private Integer width;

    public MMainSection (){
    }

    public void loadSection(Pane container){
        this.container = container;
        this.setCenter(container);
    }

    public Integer getUpdatedWidth(){
        System.out.println(width);
        return width;
    }
}
