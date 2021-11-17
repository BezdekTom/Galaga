package galaga;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Canvas canvas;
    private GameController controller;
    @Override
    public void start(Stage primaryStage) {
        try {
            //Construct a main window with a canvas.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("GameView.fxml"));

            BorderPane root = loader.load();

            Scene scene = new Scene(root);


            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("GALAGA - Bezdek");
            primaryStage.show();
            controller = loader.getController();
            controller.startGame();
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void exitProgram(WindowEvent evt) {
        //controller.stopGame();
        System.exit(0);
    }
}
