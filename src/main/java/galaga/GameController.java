package galaga;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController {
    private  boolean animationRunning = false;
    private  Game game;
    private  AnimationTimer animationTimer;

    @FXML
    private Canvas canvas;

    @FXML
    private Button rightButton;

    public GameController() {
    }

    public void startGame() {
        this.game = new Game(canvas.getWidth(), canvas.getHeight());
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
        game.draw(canvas);
        game.simulate(deltaT);
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
    private void keyPressed(KeyEvent event){
        switch (event.getCode()){
            case LEFT:
                game.moveLeft();
                break;
            case RIGHT:
                game.moveRight();
                break;
            case SPACE:
            case ENTER:
                game.fire();
                break;
            default:
                break;
        }

    }

    @FXML
    private void keyReleased(KeyEvent event){
        switch (event.getCode()){
            case LEFT:
                game.moveRight();
                break;
            case RIGHT:
                game.moveLeft();
                break;
            default:
                break;
        }

    }



}
