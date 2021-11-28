package galaga;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EnemyShip extends Ship{
    private final double height;
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
    }

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, Point2D aSpeed, SimulableListener aSimulableListener){
        this(aHeight,aType,aPosition,aSpeed);
        simulableListener = aSimulableListener;
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        gc.drawImage(image,position.getX(),position.getY(),(height/ image.getHeight())*image.getWidth(),height);
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
        if(intersect(another) && (another instanceof MyMissile || another instanceof MyShip)){
            if(++numberOfHits >= Constants.NUMBER_OF_LIVES[type]){
                alive = false;
                image = Constants.EXPLOSION;
            }
        }
    }
}
