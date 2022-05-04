package galaga;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.cxf.jaxrs.client.JAXRSClientFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private GameController gameController;
    private WelcomeControler welcomeController;
    private ScoreControler scoreControler;
    private ControlersControler controlersControler;
    private CompletableFuture<List<Score>> scores = null;
    private ScoreServerClient scoreServerClient = JAXRSClientFactory.create("http://localhost:8080", ScoreServerClient.class,
    		Collections.singletonList(new JacksonJaxbJsonProvider()));

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
            welcomeController.rightTexts();
            welcomeController.setControlerListener((GameStates state)-> {
                if (state == GameStates.GAME) {
                    String inputName = welcomeController.getName();
                    if(inputName.equals("")){
                        inputName = MyResourceBundle.getBundle().getString("player");
                    }
                    starGame(primaryStage, inputName);
                }
                if(state == GameStates.SCORE){
                    showScore(primaryStage, welcomeController.getName());
                }
                if(state == GameStates.CONTROLERS){
                    loadInstructions(primaryStage,welcomeController.getName());
                }
            });
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
        	log.error("Exception ocuers: {}", e.toString());
        }
    }

    private void starGame(Stage primaryStage, String name){
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
            if(scores != null) {
            	scores.get();
            	scores = null;
            }
            gameController = loader.getController();
            //Asynchroni nacitani skore, po dokonceni asynchroniho ulozeni
            gameController.setControlerListener(((state, scoreFuture) -> {
            	scores = scoreFuture.thenApply((v) -> {
            		return scoreServerClient.getScore();
            	});
            	
            	if(state == GameStates.WELCOME_PAGE) {
            		startWelcome(primaryStage, name);
            	}
            }));
            gameController.startGame(name);
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
        	log.error("Exception ocuers: {}", e.toString());
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
            scoreControler.rightTexts();
            if(scores != null) {
            	log.fatal("Score from future");
            	scoreControler.setScores(scores.get());
            }
            scoreControler.setControlerListener((GameStates state)-> {
                if (state == GameStates.WELCOME_PAGE) {
                    startWelcome(primaryStage, name);
                }
            });
            scoreControler.showScore();
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
        	log.error("Exception ocuers: {}", e.toString());
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
            controlersControler.rightTexts();
            controlersControler.setControlerListener((GameStates state)-> {
                if (state == GameStates.WELCOME_PAGE) {
                    startWelcome(primaryStage, name);
                }
            });
            //Exit program when main window is closed
            primaryStage.setOnCloseRequest(this::exitProgram);
        } catch (Exception e) {
        	log.error("Exception ocuers: {}", e.toString());
        }
    }

    private void exitProgram(WindowEvent evt) {
        //controller.stopGame();
        System.exit(0);
    }
}
