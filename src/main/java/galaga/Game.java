package galaga;

import javafx.geometry.Point2D;
import javafx.scene.PointLight;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    private TextDrawer textDrawer;
    private HealtsDrawer healtsDrawer;

    private int loadedShips = 0;
    private int addShip = 0;
    private int numberOfShips = 0;
    private int level  = 1;
    private  int numberOfLives = 3;
    private  boolean fired = false;
    private boolean playing = true;
    private double pauseTimer = 0;

    public  Game(double aWidth, double aHeight, String name){
        width = aWidth;
        height = aHeight;
        scoreDrawer = new ScoreDrawer(name);
        drawableSimulables = new LinkedList<DrawableSimulable>();
        destructed = new LinkedList<DrawableSimulable>();
        added = new LinkedList<DrawableSimulable>();
        healtsDrawer = new HealtsDrawer(width);
        textDrawer = new TextDrawer(height, width);

        myShip = new MyShip(Constants.DEFAULT_SHIP_HEIGHT, height - Constants.DEFAULT_SHIP_HEIGHT - Constants.MARGIN,120,width, new MyShipListenerImpl());
        myShip.setSimulableListener(this::getHit);
        drawableSimulables.add(myShip);
    }

    public  void  draw(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBackground(gc);
        for (DrawableSimulable drawableSimulable:drawableSimulables){
            drawableSimulable.draw(gc);
        }
        if(!playing){
            textDrawer.drawGameOver(gc);
        }
        else if(pauseTimer > 0){
            textDrawer.drawGetHit(gc, numberOfLives);
        }
    }

    private void drawBackground(GraphicsContext gc){
        gc.save();
        gc.drawImage(Constants.BACKGROUND, 0, 0);
        gc.restore();
        scoreDrawer.draw(gc);
        healtsDrawer.draw(gc, numberOfLives);
    }


    public  void simulate(double timeStep){
        if(playing){
            if(pauseTimer <= 0) {
                simulateInternal(timeStep);
            }
            else{
                pauseTimer -= timeStep;
            }
        }
    }

    private void simulateInternal(double timeStep){
        levelOne();
        Random random = new Random();
        if(numberOfShips == 0){
            loadedShips = 0;
            addShip = 0;
            level++;
            Constants.SPEED_KOEF++;
        }

        for (DrawableSimulable drawableSimulable:drawableSimulables){
            drawableSimulable.simulate(timeStep);
            if(drawableSimulable instanceof  EnemyShip) {
                if(numberOfShips > 0) {
                    if (random.nextInt((int)(((double)numberOfShips) * 50.0 * (1.0/(double) level))) == 1) {
                        ((EnemyShip) drawableSimulable).fire();
                    }
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

    private void levelOne(){
        if(addShip == 0 && loadedShips < Constants.SHIPS_IN_ROW){
            int nextDirection = 1 - 2 * (loadedShips % 2);
            drawableSimulables.add(new EnemyShipArrive(2,(int)(loadedShips/2), new Point2D(-20,50),width, -1,nextDirection, this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            drawableSimulables.add(new EnemyShipArrive(1,(int)(loadedShips/2), new Point2D(width + 20,150),width, 1,nextDirection, this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            drawableSimulables.add(new EnemyShipArrive(3,(int)(loadedShips/2) ,new Point2D(-20,250),width, -1,nextDirection , this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            drawableSimulables.add(new EnemyShipArrive(0,(int)(loadedShips/2) ,new Point2D(width+20,350),width, 1,nextDirection , this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            numberOfShips += 4;
            addShip = 50;
            loadedShips ++;
        }
        else{
            addShip--;
        }
    }



    private void removeDrawableSimulable(DrawableSimulable drawableSimulable){
        destructed.add(drawableSimulable);
    }

    private class  MyShipListenerImpl implements ShipListener{
        @Override
        public  void fire(Point2D positionOfCenter, int type){
            Game.this.added.add(new MyMissile(positionOfCenter.getX(), positionOfCenter.getY(), 0.75*Constants.DEFAULT_SHIP_HEIGHT, Constants.MISSILE_SPEED, 0,Game.this::removeDrawableSimulable));
        }
    }
    private class  EnemyShipListenerImpl implements ShipListener{
        @Override
        public  void fire(Point2D positionOfCenter, int type){
            Game.this.added.add(new EnemyMissile(type,positionOfCenter.getX(), positionOfCenter.getY(), 0.75*Constants.DEFAULT_SHIP_HEIGHT, Constants.MISSILE_SPEED, Game.this.height,Game.this::removeDrawableSimulable));
        }
    }

    public  void addPoints(int points){
        scoreDrawer.addScore(points);
        numberOfShips --;
    }

    private void addArriveShip(Point2D position, int direction, int previousDirection, int type, int numberOfHits, int numberFromSide, double height){
        MoveSideEnemyShip enemyShipToSide = new MoveSideEnemyShip(height, type, position, numberFromSide, direction,previousDirection, width,this::removeDrawableSimulable, new EnemyShipListenerImpl(),this::addPoints, this::addSideMovingShip);
        added.add(enemyShipToSide);
    }

    private void addSideMovingShip(Point2D position, int direction, int type, int numberOfHits, double height){
        EnemyShipOnPlace enemyShipOnPlace = new EnemyShipOnPlace(height,type,position,direction, this::removeDrawableSimulable, new EnemyShipListenerImpl(),this::addPoints);
        added.add(enemyShipOnPlace);
    }

    private void getHit(DrawableSimulable drawableSimulable){
        if(numberOfLives <= 1){
            this.gameOver();
        }
        hitPause();
        numberOfLives -= 1;
    }

    public  Score getScore(){
        return  scoreDrawer.getScore();
    }

    private void gameOver(){
        playing = false;
    }

    private void hitPause(){
        pauseTimer  = 3;
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
}
