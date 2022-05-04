package galaga;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;


@Log4j2
public class GameController {
	
    private final ScoreServerClient scoreServerClient;

    private  boolean running = true;
    @Setter
    private  GameControllerListener controlerListener;
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
    
    public GameController() {
    	scoreServerClient = JAXRSClientFactory.create("http://localhost:8080", ScoreServerClient.class,
    	    		Collections.singletonList(new JacksonJaxbJsonProvider()));
    }

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
                    controlerListener.switchState(GameStates.WELCOME_PAGE, saveScore());
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

    private  CompletableFuture<Void> saveScore(){
    	Score score = game.getScore();
        try{
        	return saveAsync(score);
        }catch (Exception e) {
			log.error("Exception ocuers: {}",e.toString());
			return null;
		}
    }

    public String getName(){
        return game.getScore().getName();
    }
    
    private CompletableFuture<Void> saveAsync(Score score) throws InterruptedException{
    	CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
    		if(scoreServerClient.updateScore(score) == false) {
    			log.info("New score saved");
            	scoreServerClient.createScore(score);
            }
    	});
    	return completableFuture;
    }
}
