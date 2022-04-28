package galaga;

import com.sun.istack.NotNull;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

@Log4j2
public class ScoreControler {
	private final ScoreServerClient scoreServerClient;
	
	@Setter
    private  ControlerListener controlerListener;

    @FXML
    private ListView<String> scoreList;
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
    
    public ScoreControler() {
    	scoreServerClient = JAXRSClientFactory.create("http://localhost:8080", ScoreServerClient.class,
	    		Collections.singletonList(new JacksonJaxbJsonProvider()));
    }

    public void rightTexts() {
    	headline.setText(MyResourceBundle.getBundle().getString("high_score"));
    	searchButton.setText(MyResourceBundle.getBundle().getString("search"));
    	deleteButton.setText(MyResourceBundle.getBundle().getString("delete"));
    	backButton.setText(MyResourceBundle.getBundle().getString("back_to_menu"));
    	searchField.setPromptText(MyResourceBundle.getBundle().getString("name"));
    }

    public void showScore(){
        initScoreList(scoreServerClient.getScore());
        log.fatal("Score loaded");
    }

    @FXML
    private void returnToMainMenu(){
        controlerListener.switchState(GameStates.WELCOME_PAGE);
    }

    @FXML
    private void search(){
    	String name = searchField.getText();
    	if(name.equals("")) {
    		showScore();
    		return;
    	}
        List<Score> selectedScore = new LinkedList<>();
        Score score = scoreServerClient.getScore(searchField.getText());
        if(score != null) {
        	selectedScore.add(scoreServerClient.getScore(searchField.getText()));
        }
        initScoreList(selectedScore);
    }

    @FXML
    private  void deleteScore(){
        scoreServerClient.deleteScore();
        showScore();
        log.info("Score deleted");
    }

    private void initScoreList(List<Score> scores) {
    	List<String> textScore = scores.stream().map(s->s.formatedText()).toList();
        scoreList.setItems(FXCollections.observableList(textScore));
    }


}
