package galaga;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ScoreDAO {
    public ScoreDAO(){
        Connection connection = getConnection();
        initTable(connection);
        try {
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteScore(){
        try (Connection connection = getConnection()) {
            getConnection().createStatement().execute("DELETE FROM score");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public  void saveScore(List<Score> scoreList){
        try (Connection connection = getConnection()){
            getConnection().createStatement().execute("DELETE FROM score");
            PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO score ( name,score) VALUES (?,?)");
            for(Score score:scoreList){
                preparedStatement.setString(1,score.getName());
                preparedStatement.setInt(2,score.getScore());
                preparedStatement.execute();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public  List<Score> loadScore(){
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name,score FROM score");
            List<Score> scoreList = new LinkedList<Score>();
            while (rs.next()){
                int score = rs.getInt(2);
                String name = rs.getString(1);
                scoreList.add(new Score(name,score));
            }
            connection.close();
            return scoreList;
        }catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }


    public  List<Score> loadSelectedScore(String aName){
        try (Connection connection = getConnection()){
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT name,score FROM score WHERE name = ?");
            preparedStatement.setString(1,aName);
            ResultSet rs = preparedStatement.executeQuery();

            List<Score> scoreList = new LinkedList<Score>();
            while (rs.next()){
                int score = rs.getInt(2);
                String name = rs.getString(1);
                scoreList.add(new Score(name,score));
            }
            connection.close();
            return scoreList;
        }catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }

    private Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby:HighScore; create=true");
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }
    }
    private void initTable(Connection connection){
        try {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE  score("
                    + "Id INT NOT NULL GENERATED ALWAYS AS IDENTITY, " +
                    " name VARCHAR (255), " +
                    " score INT NOT NULL, "+
                    " PRIMARY KEY (Id))");

        }catch (SQLException e){
            if(e.getSQLState().equals("X0Y32")) {
                return;
            }
            e.printStackTrace();
        }
    }
}
