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

    public void draw(GraphicsContext gc, int level){
        gc.save();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 15));
        gc.fillText(score.toString() + "\t level: "+level,5,Constants.MARGIN*1.5);

        gc.restore();
    }
}
