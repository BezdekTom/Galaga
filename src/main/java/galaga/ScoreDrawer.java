package galaga;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;

class ScoreDrawer {
	@Getter
    private  Score score;

    ScoreDrawer(String name){
        score = new Score(name);
    }

    void addScore(int value){
        score.setScore(score.getScore() + value);
    }

    void draw(GraphicsContext gc, int level){
        gc.save();
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font ("Verdana", 15));
        gc.fillText(score.getName()+"\t"+score.getScore() + "\t" +MyResourceBundle.getBundle().getString("level")+": "+level,5,Constants.MARGIN*1.5);

        gc.restore();
    }
}
