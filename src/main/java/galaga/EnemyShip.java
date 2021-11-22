package galaga;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EnemyShip extends Ship{
    private final double height;
    private final  int type;
    private Point2D speed;
    private  Point2D position;

    public  EnemyShip(double aHeight, int aType, Point2D aPosition, Point2D aSpeed){
        height = aHeight;
        type = aType;
        position = aPosition;
        speed = aSpeed;
    }

    @Override
    public  void drawInternal(GraphicsContext gc){
        Image ship = Constants.ENEMY_SHIPS[0];
        gc.drawImage(ship,position.getX(),position.getY(),(height/ ship.getHeight())*ship.getWidth(),height);
    }

    @Override
    public boolean simulate(double timeStep){
        position = position.add(speed.multiply(timeStep));
        return  true;
    }

}
