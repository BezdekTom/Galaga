package galaga;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.List;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

//import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import galaga_game.Game;
import galaga_game.Score;
import galaga_game.ScoreComparator;
import galaga_game.ScoreServerClient;

@Log4j2
public class GameController {
	
    private final ScoreServerClient scoreServerClient = JAXRSClientFactory.create("http://localhost:8080", ScoreServerClient.class);

    private  boolean running = true;
    @Setter
    private  ControlerListener controlerListener;
    private  boolean animationRunning = false;
    private  Game game;
    private  AnimationTimer animationTimer;
    private  boolean leftPressed = false;
    private  boolean rightPressed = false;
    private  boolean shooted = false;

    @FXML
    private Canvas canvas;

    @FXML
    private Button rightButton;

    public void startGame(String name) {
        this.game = new Game(canvas.getWidth(), canvas.getHeight(), name);
        //this.game.setGameListener(this::gameOver);
        //Draw scene on a separate thread to avoid blocking UI.
        animationTimer = new AnimationTimer() {
            private Long previous;

            @Override
            public void handle(long now) {
                if (previous == null) {
                    previous = now;
                } else {
                    drawScene((now - previous) / 1e9);
                    previous = now;
                }
            }
        };
        animationTimer.start();
        this.animationRunning = true;
    }

    private void drawScene(double deltaT) {
        if(!running){
            return;
        }
        game.draw(canvas);
        game.simulate(deltaT);
    }

    public  void stop(){

    }

    @FXML
    private  void rightPressed(){
        game.moveRight();
    }

    @FXML
    private void leftPressed(){
        game.moveLeft();
    }

    @FXML
    private  void stopShip(){
        game.stopShip();
    }

    @FXML
    private  void fire(){
        game.fire();
    }


    @FXML
    private void keyHandle(KeyEvent event){
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case LEFT:
                    if(!leftPressed) {
                        game.moveLeft();
                        leftPressed = true;
                    }
                    break;
                case RIGHT:
                    if (!rightPressed){
                        game.moveRight();
                        rightPressed = true;
                    }
                    break;
                case SPACE:
                case ENTER:
                    if(!shooted) {
                        game.fire();
                        shooted = true;
                    }
                    break;
                case ESCAPE:
                    running = false;
                    saveScore();
                    controlerListener.switchState(GameStates.WELCOME_PAGE);
                default:
                    break;
            }
        }
        else if (event.getEventType() == KeyEvent.KEY_RELEASED){
            switch (event.getCode()){
                case LEFT:
                    if(leftPressed) {
                        game.moveRight();
                        leftPressed = false;
                    }
                    break;
                case RIGHT:
                    if(rightPressed) {
                        game.moveLeft();
                        rightPressed = false;
                    }
                    break;
                case SPACE:
                case ENTER:
                    if(shooted) {
                        shooted = false;
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private  void saveScore(){
        //List<Score> previousScore = scoreDAO.loadScore();
        //previousScore.add(game.getScore());
        //Collections.sort(previousScore, new ScoreComparator().reversed());
        //scoreDAO.saveScore(previousScore);
    	Score score = game.getScore();
    	
        //if(scoreServerClient.updateScore(score) == false) {
        	scoreServerClient.createScore(score);
        //}
        log.info("Score saved");
    }

    public String getName(){
        return game.getScore().getName();
    }
}
