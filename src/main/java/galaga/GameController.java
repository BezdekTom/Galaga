package galaga;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class GameController {
    private  boolean animationRunning = false;
    private  Game game;
    private  AnimationTimer animationTimer;

    @FXML
    private Canvas canvas;

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

}
