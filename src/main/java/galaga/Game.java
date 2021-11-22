package galaga;

import javafx.geometry.Point2D;
import javafx.scene.PointLight;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class Game {
    private  final  double width;
    private  final double height;
    private List<DrawableSimulable> drawableSimulables;
    private  MyShip myShip;
    private final  double bottomMargin = 20;
    private final  double defaultShipHeight = 40;
    private  boolean fired = false;

    public  Game(double aWidth, double aHeight){
        width = aWidth;
        height = aHeight;
        drawableSimulables = new LinkedList<DrawableSimulable>();
        Point2D spe = new Point2D(10,-5);
        Point2D pos = new Point2D(100,100);
        myShip = new MyShip(defaultShipHeight, height - defaultShipHeight - bottomMargin,120,20,width - 20);
        drawableSimulables.add(new EnemyShip(20,0,new Point2D(100,100), new Point2D(10,-5)));
        drawableSimulables.add(myShip);
    }

    public  void  draw(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBackground(gc);
        for (DrawableSimulable drawableSimulable:drawableSimulables){
            drawableSimulable.draw(gc);
        }
    }

    public  void simulate(double timeStep){
        for (DrawableSimulable drawableSimulable:drawableSimulables){
            if(!drawableSimulable.simulate(timeStep)){
                drawableSimulables.remove(drawableSimulable);
            }
        }
        fired = false;
    }

    private void drawBackground(GraphicsContext gc){
        gc.save();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,width,height);
        gc.restore();
    }

    public void moveRight(){
        myShip.moveRight();
    }

    public  void moveLeft(){
        myShip.moveLeft();
    }

    public  void stopShip(){
        myShip.stop();
    }

    public  void fire(){
        if(!fired){
            drawableSimulables.add(new MyMissile(myShip.getHorizontalPosition(), height - bottomMargin - 1.5*defaultShipHeight, 3*defaultShipHeight/4, 200, 0));
        }
    }
}
