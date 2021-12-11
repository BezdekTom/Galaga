package galaga;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ScoreDrawer {
    private  Score score;

    public ScoreDrawer(String name){
        score = new Score(name, 0);
    }

    public  Score getScore(){
        return  score;
    }

    public void addScore(int value){
        score.setScore(score.getScore() + value);
    }

    public void draw(GraphicsContext gc){
        gc.save();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 10));
        gc.fillText(score.toString(),5,Constants.MARGIN);

        gc.restore();
    }
}
