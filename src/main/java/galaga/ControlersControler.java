package galaga;

import galaga_game.MyResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControlersControler {
    private  ControlerListener controlerListener;
    
    @FXML
    private Label headline;

    @FXML
    private Label left;
    
    @FXML
    private Label right;
    
    @FXML
    private Label shoot;
    
    @FXML
    private Label esc;
    
    @FXML
    private Button back;
    
    public void rightTexts() {
    	headline.setText(MyResourceBundle.getBundle().getString("controls"));
    	left.setText(MyResourceBundle.getBundle().getString("left"));
    	right.setText(MyResourceBundle.getBundle().getString("right"));
    	shoot.setText(MyResourceBundle.getBundle().getString("fire"));
    	esc.setText(MyResourceBundle.getBundle().getString("back_to_menu"));
    	back.setText(MyResourceBundle.getBundle().getString("back_to_menu"));
    }
    
    @FXML
    private void backToMenu(){
        controlerListener.switchState(GameStates.WELCOME_PAGE);
    }

    public void setControlerListener(ControlerListener aControlerListener){
        controlerListener = aControlerListener;
    }
}
