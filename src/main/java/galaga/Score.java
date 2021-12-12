package galaga;

public class Score {
    private final  String name;
    private  int score;

    public Score(String aName, int aScore){
        name= aName;
        score = aScore;
    }
    @Override
    public String toString(){
        return (name + "\t" + score);
    }

    public int getScore(){
        return  score;
    }
    public  void setScore(int aScore){
        score = aScore;
    }

    public  String getName(){
        return name;
    }
}
