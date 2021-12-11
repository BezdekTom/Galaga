package galaga;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class EnemyShip extends Ship{
    protected EnemyShipListener enemyShipListener;
    protected double height = Constants.DEFAULT_SHIP_HEIGHT;
    protected double width;
    protected final  int type;
    protected Point2D position;
    protected Image image;
    protected double explosionTimer = 1.3;

    public  EnemyShip(double aHeight, int aType, Point2D aPosition){
        height = aHeight;
        type = aType;
        position = aPosition;
        image = Constants.ENEMY_SHIPS[type];
        width = (height/ image.getHeight())*image.getWidth();
    }

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, SimulableListener aSimulableListener){
        this(aHeight,aType,aPosition);
        simulableListener = aSimulableListener;
    }

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, SimulableListener aSimulableListener, EnemyShipListener aEnemyShipListener){
        this(aHeight,aType,aPosition, aSimulableListener);
        enemyShipListener = aEnemyShipListener;
    }

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, SimulableListener aSimulableListener, ShipListener aShipListener, EnemyShipListener aEnemyShipListener){
        this(aHeight,aType,aPosition, aSimulableListener);
        shipListener = aShipListener;
        enemyShipListener = aEnemyShipListener;
    }

    public  EnemyShip(int aType, Point2D aPosition, SimulableListener aSimulableListener, ShipListener aShipListener, EnemyShipListener aEnemyShipListener){
        this(Constants.DEFAULT_SHIP_HEIGHT, aType, aPosition, aSimulableListener, aShipListener, aEnemyShipListener);
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(image,position.getX(),position.getY(),width,height);
        if(!alive){
            drawPoints(gc);
        }
    }

    public BoundingBox getBoundingBox(){
        return  new BoundingBox(position.getX(), position.getY(), (height/ Constants.ENEMY_SHIPS[type].getHeight())*Constants.ENEMY_SHIPS[type].getWidth(), height);
    }

    @Override
    public void  hit(DrawableSimulable another){
        if(intersect(another) && (another instanceof MyMissile || another instanceof MyShip) && ((DrawableSimulableEntity) another).wasAlive && alive){
            another.hit(this);
            if(++numberOfHits >= Constants.NUMBER_OF_LIVES[type]){
                enemyShipListener.addPoints(Constants.POINTS[type]);
                alive = false;
                image = Constants.EXPLOSION;
            }
        }
    }

    private void drawPoints(GraphicsContext gc){
        gc.save();
        gc.setFill(Color.WHITE);
        gc.fillText(""+Constants.POINTS[type], position.getX(), position.getY()-5);
    }
}
