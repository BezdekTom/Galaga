package galaga_game;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class ScoreFile {
    public void saveScore(List<Score> highScores){
        try (PrintWriter pw = new PrintWriter(new FileWriter("high_score.csv"))) {
            for (Score score : highScores) {
                pw.printf("%s;%d\n", score.getName(), score.getScore());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  List<Score> loadScore(){
        List<Score> highScores = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("high_score.csv"))){
            String line;
            while (null != (line = bufferedReader.readLine())) {
                String[] tokens = line.split(";");
                highScores.add(new Score(tokens[0], Integer.parseInt(tokens[1]), ZonedDateTime.now()));
            }
            return highScores;
        }catch (Exception e){
            e.printStackTrace();
            return  highScores;
        }
    }

    public  void deleteScore(){
        try (PrintWriter pw = new PrintWriter(new FileWriter("high_score.csv"))) {
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  List<Score> searchScore(String name){
        List<Score> highScores = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("high_score.csv"))){
            String line;
            while (null != (line = bufferedReader.readLine())) {
                String[] tokens = line.split(";");
                if(tokens[0].equals(name)){
                    highScores.add(new Score(tokens[0], Integer.parseInt(tokens[1]), ZonedDateTime.now()));
                }
            }
            return highScores;
        }catch (Exception e){
            e.printStackTrace();
            return  highScores;
        }
    }
}
