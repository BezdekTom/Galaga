package galaga_game;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.time.ZonedDateTime;
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
    private MyShip myShip;
    private TextDrawer textDrawer;
    private HealtsDrawer healtsDrawer;

    private double pauseTimer = 0;
    private double nextLevelTimer = 0;

    boolean waveAttacReady = false;
    private double waveAttacTimer = 9999;

    private int loadedShips = 0;
    private int addShip = 0;
    private int numberOfShips = 0;
    private int level  = 1;
    private  int numberOfLives = 3;
    private  boolean fired = false;
    private boolean playing = true;
    private boolean allShipsOnPlace = false;

    public  Game(double aWidth, double aHeight, String name){
        width = aWidth;
        height = aHeight;
        scoreDrawer = new ScoreDrawer(name);
        drawableSimulables = new LinkedList<>();
        destructed = new LinkedList<>();
        added = new LinkedList<>();
        healtsDrawer = new HealtsDrawer(width);
        textDrawer = new TextDrawer(height);// width);

        myShip = new MyShip(Constants.DEFAULT_SHIP_HEIGHT, height - Constants.DEFAULT_SHIP_HEIGHT - Constants.MARGIN,120,width, new MyShipListenerImpl());	
        myShip.setSimulableListener(this::wasHit);
        drawableSimulables.add(myShip);
    }

    public  void  draw(Canvas canvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawBackground(gc);
        drawableSimulables.forEach(d->d.draw(gc));
        if(!playing){
            textDrawer.drawGameOver(gc, scoreDrawer.getScore().getScore());
        }
        else if(pauseTimer > 0){
            textDrawer.drawGetHit(gc, numberOfLives);
        }
        else if(nextLevelTimer > 0){
            textDrawer.drawNextLevel(gc, level);
        }
    }

    private void drawBackground(GraphicsContext gc){
        gc.save();
        gc.drawImage(Constants.BACKGROUND, 0, 0);
        gc.restore();
        scoreDrawer.draw(gc, level);
        healtsDrawer.draw(gc, numberOfLives);

       // textDrawer.drawGameOver(gc, 3);
    }


    public  void simulate(double timeStep){
        if(playing){
            if(pauseTimer <= 0 && nextLevelTimer <= 0) {
                simulateInternal(timeStep);
            }
            else{
                pauseTimer -= timeStep;
                nextLevelTimer -= timeStep;
            }
        }
    }

    private void simulateInternal(double timeStep){
        setLevel();
        waveAttacTimer -= timeStep;

       
        if(numberOfShips == 0){
            nextLevel();
        }

        if(waveAttacReady){
            waveAttacTimer = 5;//20;
            waveAttacReady = false;
        }

        //synchronizace pohybu lodi na radcich
        if(allShipsOnPlace){
        	waveAttacReady = waveAttacTimer > 50 ? true: waveAttacReady;   
            drawableSimulables.stream().filter(d -> d instanceof EnemyShipOnPlace).forEach(d-> ((EnemyShipOnPlace) d).startMoving());
        }

        //nalet lodi s paprskem smrti
        if(waveAttacTimer <= 0){
        	drawableSimulables.stream().filter(d -> d instanceof EnemyShipOnPlace).map(d -> ((EnemyShipOnPlace) d)).filter(e -> e.getType() == 0 && e.isVisible())
        	.findAny().ifPresent(e -> {e.setShipOnPlaceListener(new ShipOnPlaceListenerImpl()); e.waveAttac(); waveAttacReady = false; waveAttacTimer = 30;});
        }
        allShipsOnPlace = true;
        
        //move all elements and shoot from random ships
        drawableSimulables.forEach(d -> moveAndFire(d, timeStep));

        //Adding of new shots to the game
        drawableSimulables.addAll(added);
        added.clear();

        //Control, if some object was hitten by other
        drawableSimulables.forEach(d1 -> drawableSimulables.forEach(d2 -> d1.hit(d2)));

        //Removing destructed objects from the game
        drawableSimulables.removeAll(destructed);
        destructed.clear();

        fired = false;
    }
    
    private void moveAndFire(DrawableSimulable drawableSimulable, double timeStep) {
    	Random random = new Random();
    	drawableSimulable.simulate(timeStep);
        if(drawableSimulable instanceof  EnemyShip) {
            if(!(drawableSimulable instanceof EnemyShipOnPlace)){
                allShipsOnPlace &= false;
            }
            if (random.nextInt((int)(((double)numberOfShips) * 50.0 * (1.0/(double) level)) + 1 ) == 1) {
                ((EnemyShip) drawableSimulable).fire();
            }
        }
    }

    private void setLevel(){
        if(addShip == 0 && loadedShips < Constants.SHIPS_IN_ROW){
            int nextDirection = 1 - 2 * (loadedShips % 2);
            drawableSimulables.add(new EnemyShipArrive(2,loadedShips/2, new Point2D(-20,50),width, -1,nextDirection, this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            drawableSimulables.add(new EnemyShipArrive(1,loadedShips/2, new Point2D(width + 20,150),width, 1,nextDirection, this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            drawableSimulables.add(new EnemyShipArrive(3,loadedShips/2 ,new Point2D(-20,250),width, -1,nextDirection , this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            drawableSimulables.add(new EnemyShipArrive(0,loadedShips/2 ,new Point2D(width+20,350),width, 1,nextDirection , this::removeDrawableSimulable, this::addPoints,this::addArriveShip));
            numberOfShips += 4;
            addShip = 50;
            loadedShips ++;
        }
        else{
            addShip--;
        }
    }

    private void nextLevel(){
        drawableSimulables.clear();
        drawableSimulables.add(myShip);
        if(loadedShips == Constants.SHIPS_IN_ROW) {
            nextLevelTimer = 3;
            loadedShips = 0;
            addShip = 0;
            level++;
            Constants.SPEED_KOEF += 0.2;
            waveAttacTimer = 999;
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

    private class  WaveAttacListenerImpl implements WaveAttacListener{
        @Override
        public  void fire(WaveMissile waveMissile){
            Game.this.added.add(waveMissile);
        }
    }

    private class ShipOnPlaceListenerImpl implements  ShipOnPlaceListener{
        @Override
        public void startWaveAttac(EnemyShipOnPlace aEnemyShipOnPlace, double aHeight, int aType, Point2D aPosition, int aNumberOfHits){
            added.add(new EnemyShipWaveAttac(aEnemyShipOnPlace,aHeight, aType, aPosition, myShip.getPosition().add(new Point2D(0,-100)), aNumberOfHits, Game.this::removeDrawableSimulable, Game.this::addPoints, new WaveAttacListenerImpl()));
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

    private void wasHit(DrawableSimulable drawableSimulable){
        if(numberOfLives <= 1){
            this.gameOver();
        }
        hitPause();
        numberOfLives -= 1;
    }

    public  Score getScore(){
        Score score = scoreDrawer.getScore();
    	score.setTime(ZonedDateTime.now());
        return  score;
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
