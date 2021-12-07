package galaga;

import javafx.geometry.Point2D;
import javafx.scene.PointLight;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
    private  final  double width;
    private  final double height;
    private List<DrawableSimulable> drawableSimulables;
    private List<DrawableSimulable> destructed;
    private List<DrawableSimulable> added;
    private ScoreDrawer scoreDrawer;
    private  MyShip myShip;
    private final  double topBottomMargin = 20;
    private final  double defaultShipHeight = 40;
    private  boolean fired = false;

    public  Game(double aWidth, double aHeight, String name){
        width = aWidth;
        height = aHeight;
        scoreDrawer = new ScoreDrawer(name);
        drawableSimulables = new LinkedList<DrawableSimulable>();
        destructed = new LinkedList<DrawableSimulable>();
        added = new LinkedList<DrawableSimulable>();

        myShip = new MyShip(defaultShipHeight, height - defaultShipHeight - topBottomMargin,120,20,width - 20, new MyShipListenerImpl());
        myShip.setSimulableListener(this::removeDrawableSimulable);
        drawableSimulables.add(new EnemyShip(defaultShipHeight,0,new Point2D(100,100), new Point2D(10,-5),this::removeDrawableSimulable, new EnemyShipListenerImpl(),this::addPoints));
        drawableSimulables.add(new EnemyShip(defaultShipHeight,1,new Point2D(250,90), new Point2D(-15,20),this::removeDrawableSimulable,new EnemyShipListenerImpl(),this::addPoints));
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
        Random random = new Random();
        for (DrawableSimulable drawableSimulable:drawableSimulables){
            drawableSimulable.simulate(timeStep);
            if(drawableSimulable instanceof  EnemyShip) {
                if(random.nextInt(150)  == 1){
                    ((EnemyShip) drawableSimulable).fire();
                }
            }
        }

        //Adding of new shots to the game
        for (DrawableSimulable drawableSimulable: added){
            drawableSimulables.add(drawableSimulable);
        }
        added.clear();

        //Control, if some object was hitten by other
        for (DrawableSimulable drawableSimulable1:drawableSimulables){
            for(DrawableSimulable drawableSimulable2:drawableSimulables){
                drawableSimulable1.hit(drawableSimulable2);
            }
        }

        //Removing destructed objects from the game
        for(DrawableSimulable drawableSimulable: destructed){
            drawableSimulables.remove(drawableSimulable);
        }
        destructed.clear();

        fired = false;
    }

    private void drawBackground(GraphicsContext gc){
        gc.save();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,width,height);
        gc.restore();
        scoreDrawer.draw(gc);
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
            myShip.fire();
            fired = true;
        }
    }

    private void removeDrawableSimulable(DrawableSimulable drawableSimulable){
        destructed.add(drawableSimulable);
    }

    private class  MyShipListenerImpl implements ShipListener{
        @Override
        public  void fire(Point2D positionOfCenter, int type){
            Game.this.added.add(new MyMissile(positionOfCenter.getX(), positionOfCenter.getY(), 3*defaultShipHeight/4, Constants.MISSILE_SPEED, 0,Game.this::removeDrawableSimulable));
        }
    }
    private class  EnemyShipListenerImpl implements ShipListener{
        @Override
        public  void fire(Point2D positionOfCenter, int type){
            Game.this.added.add(new EnemyMissile(type,positionOfCenter.getX(), positionOfCenter.getY(), 3*defaultShipHeight/4, Constants.MISSILE_SPEED, Game.this.height,Game.this::removeDrawableSimulable));
        }
    }

    public  void setPlayer(String name){
        scoreDrawer = new ScoreDrawer(name);
    }

    public  void addPoints(int points){
        scoreDrawer.addScore(points);
    }

    public  Score getScore(){
        return  scoreDrawer.getScore();
    }
}
