package galaga;

import galaga_game.MyResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class WelcomeControler {
    private  ControlerListener controlerListener;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameText;
    
    @FXML
    private Button playButton;
    
    @FXML
    private Button highScoreButton;
    
    @FXML
    private Button controlsButton;
    
    public void rightTexts() {
    	nameText.setText(MyResourceBundle.getBundle().getString("name")+": ");
    	playButton.setText(MyResourceBundle.getBundle().getString("play"));
    	highScoreButton.setText(MyResourceBundle.getBundle().getString("high_score"));
    	controlsButton.setText(MyResourceBundle.getBundle().getString("controls"));
    }

    @FXML
    private  void play(){
        controlerListener.switchState(GameStates.GAME);
    }

    @FXML
    private  void instructions(){
        controlerListener.switchState(GameStates.CONTROLERS);
    }

    @FXML
    private  void highScore(){
        controlerListener.switchState(GameStates.SCORE);
    }
    
    @FXML
    private void setEnglish() {
    	MyResourceBundle.setLanguage("en");
    	rightTexts();
    }
    
    @FXML
    private void setCzech() {
    	MyResourceBundle.setLanguage("cs");
    	rightTexts();
    }

    public void setControlerListener(ControlerListener aControlerListener){
        controlerListener = aControlerListener;
    }

    public  String getName(){
        return nameField.getText();
    }
    public void setName(String name){
        nameField.setText(name);
    }
}
