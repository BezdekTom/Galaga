package galaga;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class WelcomeControler implements  Controler{
    private  ControlerListener controlerListener;

    @FXML
    private Button playButton;

    @FXML
    private  Button highScoreButton;

    @FXML
    private TextField nameField;

    public WelcomeControler(){}

    @FXML
    private  void play(){
        controlerListener.switchState(GameStates.GAME);
    }

    @FXML
    private  void highScore(){
        controlerListener.switchState(GameStates.SCORE);
    }

    public void setControlerListener(ControlerListener aControlerListener){
        controlerListener = aControlerListener;
    }

    public  String getName(){
        return nameField.getText();
    }
}