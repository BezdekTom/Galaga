package galaga;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    @Override
    public int compare(Score o1, Score o2) {
        if(o1.getScore() == o2.getScore()){
            return  Integer.compare(o1.getName().length(), o2.getName().length());
        }
        else {
            return Integer.compare(o1.getScore(), o2.getScore());
        }
    }
}
