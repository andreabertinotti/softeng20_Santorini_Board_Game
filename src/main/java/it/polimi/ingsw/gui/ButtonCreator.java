package it.polimi.ingsw.gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ButtonCreator {

    private Image unpressed;
    private Image pressed;
    private Image disabled;
    private Boolean status = false;
    private Boolean enabled;
    private String utility = null;

    private ImageView iw;



    public Button ButtonCreator(boolean enabled, double width, double height, String imgUnpressed, String imgPressed, String imgDisabled, String textDisplayed, int textSize) {
        this.unpressed = new Image(imgUnpressed);
        this.pressed = new Image(imgPressed);
        this.disabled = new Image(imgDisabled);
        this.enabled = enabled;

        if(!enabled){
            this.iw = new ImageView(disabled);
        }else {
            this.iw = new ImageView(unpressed);
        }
        iw.setFitWidth(width);
        iw.setFitHeight(height);
        Text text = new Text(textDisplayed);
        text.setFont(Font.font("Ryan", FontWeight.BOLD, textSize));
        text.setFill(Color.WHITE);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(iw, text);

        Button b = new Button("", stackPane);
        return b;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setPressed() {
        if(enabled) {
            this.iw.setImage(pressed);
            status = true;
        }
    }

    public void setUnpressed(){
        if(enabled) {
            this.iw.setImage(unpressed);
            status = false;
        }
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
        if(enabled){
            setUnpressed();
        }else{
            this.iw.setImage(disabled);
            this.status = false;
        }
    }

    public String getUtility() {
        return utility;
    }

    public void setUtility(String utility) {
        this.utility = utility;
    }
}