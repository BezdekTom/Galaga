package galaga;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ScoreControler {
	@Setter
    private  ControlerListener controlerListener;
    private final ScoreFile scoreFile = new ScoreFile();

    @FXML
    private ListView<Score> scoreList;
    @FXML
    private TextField searchField;
    @FXML
    private Label headline;
    @FXML 
    private Button searchButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;

    public void rightTexts() {
    	headline.setText(MyResourceBundle.getBundle().getString("high_score"));
    	searchButton.setText(MyResourceBundle.getBundle().getString("search"));
    	deleteButton.setText(MyResourceBundle.getBundle().getString("delete"));
    	backButton.setText(MyResourceBundle.getBundle().getString("back_to_menu"));
    	searchField.setPromptText(MyResourceBundle.getBundle().getString("name"));
    }

    public void showScore(){
        initScoreList(scoreFile.loadScore());
        log.fatal("Score loaded");
    }

    @FXML
    private void returnToMainMenu(){
        controlerListener.switchState(GameStates.WELCOME_PAGE);
    }

    @FXML
    private void search(){
        List<Score> selectedScore = scoreFile.searchScore(searchField.getText());
        initScoreList(selectedScore);
    }

    @FXML
    private  void deleteScore(){
        scoreFile.deleteScore();
        showScore();
        log.info("Score deleted");
    }

    private void initScoreList(List<Score> scores) {
        scoreList.setItems(FXCollections.observableList(scores));
    }


}
