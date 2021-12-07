package galaga;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class EnemyShip extends Ship{
    private EnemyShipListener enemyShipListener;

    private final double height;
    private final double width;
    private final  int type;
    private Point2D speed;
    private  Point2D position;
    private  Image image;

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, Point2D aSpeed){
        height = aHeight;
        type = aType;
        position = aPosition;
        speed = aSpeed;
        image = Constants.ENEMY_SHIPS[type];
        width = (height/ image.getHeight())*image.getWidth();
    }

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, Point2D aSpeed, SimulableListener aSimulableListener){
        this(aHeight,aType,aPosition,aSpeed);
        simulableListener = aSimulableListener;
    }

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, Point2D aSpeed, SimulableListener aSimulableListener, ShipListener aShipListener, EnemyShipListener aEnemyShipListener){
        this(aHeight,aType,aPosition,aSpeed, aSimulableListener);
        shipListener = aShipListener;
        enemyShipListener = aEnemyShipListener;
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(image,position.getX(),position.getY(),width,height);
        if(!alive){
            drawPoints(gc);
        }
    }

    @Override
    public void simulate(double timeStep){
        if(alive) {
            position = position.add(speed.multiply(timeStep));
        }
        else if(explosionTimer > 0)
        {
            explosionTimer -= timeStep;
        }
        else
        {
            simulableListener.destruct(this);

        }
    }

    public BoundingBox getBoundingBox(){
        return  new BoundingBox(position.getX(), position.getY(), (height/ Constants.ENEMY_SHIPS[type].getHeight())*Constants.ENEMY_SHIPS[type].getWidth(), height);
    }

    @Override
    public void  hit(DrawableSimulable another){
        if(intersect(another) && (another instanceof MyMissile || another instanceof MyShip) && ((DrawableSimulableEntity) another).alive && alive){
            another.hit(this);
            if(++numberOfHits >= Constants.NUMBER_OF_LIVES[type]){
                enemyShipListener.addPoints(Constants.POINTS[type]);
                alive = false;
                image = Constants.EXPLOSION;
            }
        }
    }

    @Override
    public  void fire(){
        if(alive){
            shipListener.fire(new Point2D(position.getX() + width/2, position.getY() + height/2), type);

        }
    }
    private void drawPoints(GraphicsContext gc){
        gc.save();
        gc.setFill(Color.WHITE);
        gc.fillText(""+Constants.POINTS[type], position.getX(), position.getY()-5);
    }
}
