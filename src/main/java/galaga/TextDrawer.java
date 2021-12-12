package galaga;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TextDrawer {
    private final double gameHeight;
    private final double gameWidth;

    public TextDrawer(double aGameHeight, double aGameWidth){
        gameHeight = aGameHeight;
        gameWidth = aGameWidth;
    }

    public void drawGameOver(GraphicsContext gc, int score){
        gc.save();
        gc.setFill(Color.RED);
        gc.setFont(Font.font ("Verdana", 50));
        gc.fillText("GAME OVER",50, gameHeight/2 - 50);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 30));

        gc.fillText("YOUR SCORE: "+ score,45, gameHeight/2);
        gc.setFont(Font.font ("Verdana", 20));

        gc.fillText("PRESS ESC TO END THE GAME",40, gameHeight/2 + 50);
        gc.restore();
    }

    public void drawGetHit(GraphicsContext gc, int numberOfLives){
        gc.save();
        gc.setFill(Color.GREEN);
        gc.setFont(Font.font ("Verdana", 40));

        gc.fillText("YOU WAS HIT",60, gameHeight/2 - 50);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 30));

        gc.fillText("LIVES REMAINING: "+numberOfLives,40, gameHeight/2);
        gc.restore();
    }

    public void drawNextLevel(GraphicsContext gc, int level){
        gc.save();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 40));
        gc.fillText("NEXT LEVEL",75, gameHeight/2 - 20);
        gc.restore();
    }
}
