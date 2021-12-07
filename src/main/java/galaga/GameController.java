package galaga;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Collections;
import java.util.List;

public class GameController implements Controler{
    private  ScoreDAO scoreDAO = new ScoreDAO();
    private ScoreFile scoreFile = new ScoreFile();

    private  boolean running = true;
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

    public GameController() {
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
                case B:
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

    public void setControlerListener(ControlerListener aControlerListener){
        controlerListener = aControlerListener;
    }

    private  void saveScore(){
        //List<Score> previousScore = scoreDAO.loadScore();
        //previousScore.add(game.getScore());
        //Collections.sort(previousScore, new ScoreComparator().reversed());
        //scoreDAO.saveScore(previousScore);

        List<Score> previousScore = scoreFile.loadScore();
        previousScore.add(game.getScore());
        Collections.sort(previousScore, new ScoreComparator().reversed());
        scoreFile.saveScore(previousScore);
    }
}
