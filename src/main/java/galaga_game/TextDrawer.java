package galaga_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TextDrawer {
    private final double gameHeight;
    //private final double gameWidth;


    public void drawGameOver(GraphicsContext gc, int score){
        gc.save();
        gc.setFill(Color.RED);
        gc.setFont(Font.font ("Verdana", 50));
        gc.fillText(MyResourceBundle.getBundle().getString("game_over"),50, gameHeight/2 - 50);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 30));

        gc.fillText(MyResourceBundle.getBundle().getString("your_score")+": "+ score,45, gameHeight/2);
        gc.setFont(Font.font ("Verdana", 20));

        gc.fillText(MyResourceBundle.getBundle().getString("press_esc"),40, gameHeight/2 + 50);
        gc.restore();
    }

    public void drawGetHit(GraphicsContext gc, int numberOfLives){
        gc.save();
        gc.setFill(Color.GREEN);
        gc.setFont(Font.font ("Verdana", 40));

        gc.fillText(MyResourceBundle.getBundle().getString("hit"),60, gameHeight/2 - 50);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 30));

        gc.fillText(MyResourceBundle.getBundle().getString("lives") + ": "+numberOfLives,40, gameHeight/2);
        gc.restore();
    }

    public void drawNextLevel(GraphicsContext gc, int level){
        gc.save();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 40));
        gc.fillText(MyResourceBundle.getBundle().getString("next_level"),75, gameHeight/2 - 20);
        gc.restore();
    }
}
