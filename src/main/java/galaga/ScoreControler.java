package galaga;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class ScoreControler {
    private  ControlerListener controlerListener;
    private final ScoreDAO scoreDAO = new ScoreDAO();
    private final ScoreFile scoreFile = new ScoreFile();

    @FXML
    private ListView<Score> scoreList;
    @FXML
    private TextField searchField;

    public ScoreControler(){}
    public void showScore(){
        //initScoreList(scoreDAO.loadScore());
        initScoreList(scoreFile.loadScore());
    }

    public void setControlerListener(ControlerListener aControlerListener){

        controlerListener = aControlerListener;
    }

    @FXML
    private void returnToMainMenu(){
        controlerListener.switchState(GameStates.WELCOME_PAGE);
    }

    @FXML
    private void search(){
        //List<Score> selectedScore = scoreDAO.loadSelectedScore(searchField.getText());
        List<Score> selectedScore = scoreFile.searchScore(searchField.getText());
        initScoreList(selectedScore);
    }

    @FXML
    private  void deleteScore(){
        //scoreDAO.deleteScore();
        scoreFile.deleteScore();
        showScore();
    }

    private void initScoreList(List<Score> scores) {
        scoreList.setItems(FXCollections.observableList(scores));
    }


}
