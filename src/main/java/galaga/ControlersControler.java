package galaga;

import javafx.fxml.FXML;

public class ControlersControler {
    private  ControlerListener controlerListener;

    @FXML
    private void backToMenu(){
        controlerListener.switchState(GameStates.WELCOME_PAGE);
    }

    public void setControlerListener(ControlerListener aControlerListener){
        controlerListener = aControlerListener;
    }
}
