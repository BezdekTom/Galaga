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

    private GameController gameController;
    private WelcomeControler welcomeController;
    private ScoreControler scoreControler;
    private ControlersControler controlersControler;

    @Override
    public void start(Stage primaryStage){
        startWelcome(primaryStage,"");
    }


    private void startWelcome(Stage primaryStage, String name){
        try {
            gameController = null;
            scoreControler = null;
            controlersControler = null;
            //Construct a main window with a canvas.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("WelcomeView.fxml"));

            BorderPane root = loader.load();

            Scene scene = new Scene(root);


            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("GALAGA - Bezdek");
            primaryStage.show();
            welcomeController = loader.getController();
            welcomeController.setName(name);
            welcomeController.setControlerListener((GameStates state)-> {
                if (state == GameStates.GAME) {
                    String inputName = welcomeController.getName();
                    if(inputName.equals("")){
                        inputName = "Galaga Player";
                    }
                    this.starGame(primaryStage, inputName);
                }
                if(state == GameStates.SCORE){
                    this.showScore(primaryStage, welcomeController.getName());
                }
                if(state == GameStates.CONTROLERS){
                    this.loadInstructions(primaryStage,welcomeController.getName());
                }
            });
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void starGame(Stage primaryStage, String name){
        try {
            welcomeController = null;
            scoreControler = null;

            //Construct a main window with a canvas.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("GameView.fxml"));

            BorderPane root = loader.load();

            Scene scene = new Scene(root);


            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("GALAGA - Bezdek");
            primaryStage.show();
            gameController = loader.getController();
            gameController.setControlerListener((GameStates state)-> {
                if (state == GameStates.WELCOME_PAGE) {
                    this.startWelcome(primaryStage,gameController.getName());
                }
            });
            gameController.startGame(name);
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showScore(Stage primaryStage, String name){
        try {
            gameController = null;
            welcomeController = null;
            //Construct a main window with a canvas.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ScoreView.fxml"));

            BorderPane root = loader.load();

            Scene scene = new Scene(root);


            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("GALAGA - Bezdek");
            primaryStage.show();
            scoreControler = loader.getController();
            scoreControler.setControlerListener((GameStates state)-> {
                if (state == GameStates.WELCOME_PAGE) {
                    this.startWelcome(primaryStage, name);
                }
            });
            scoreControler.showScore();
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadInstructions(Stage primaryStage, String name){
        try {
            gameController = null;
            //Construct a main window with a canvas.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Controls.fxml"));

            BorderPane root = loader.load();

            Scene scene = new Scene(root);


            primaryStage.setScene(scene);
            primaryStage.resizableProperty().set(false);
            primaryStage.setTitle("GALAGA - Bezdek");
            primaryStage.show();
            controlersControler = loader.getController();
            controlersControler.setControlerListener((GameStates state)-> {
                if (state == GameStates.WELCOME_PAGE) {
                    this.startWelcome(primaryStage, name);
                }
            });
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
